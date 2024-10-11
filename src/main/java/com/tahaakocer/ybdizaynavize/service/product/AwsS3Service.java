package com.tahaakocer.ybdizaynavize.service.product;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tahaakocer.ybdizaynavize.exception.product.S3UploadImageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class AwsS3Service {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.access.key}")
    private String accessKey;

    @Value("${aws.s3.secret.key}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    public String saveImage(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            // AWS kimlik bilgileri
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(region)
                    .build();

            InputStream inputStream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
            s3Client.putObject(putObjectRequest);
            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        }catch (Exception e) {
            e.printStackTrace();
            throw new S3UploadImageException("Error uploading image");
        }
    }
    // Birden fazla resmi paralel olarak yükler
    public List<String> saveMultipleImages(MultipartFile[] files) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10); // 10 iş parçacığı ile paralel yükleme

        for (MultipartFile file : files) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> saveImage(file), executor);
            futures.add(future);
        }

        // Sonuçları toplar
        List<String> uploadedUrls = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        executor.shutdown();
        return uploadedUrls;
    }

    public void deleteImage(String fileUrl) {
        try {
            String fileName = extractFileNameFromUrl(fileUrl);

            // AWS kimlik bilgileri
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(region)
                    .build();
            s3Client.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            throw new S3DeleteImageException("Error deleting image");
        }
    }
    // Birden fazla dosyayı toplu şekilde siler
    public void deleteMultipleImages(List<String> fileUrls) {
        ExecutorService executor = Executors.newFixedThreadPool(10); // Paralel işlem için 10 iş parçacığı

        List<CompletableFuture<Void>> futures = fileUrls.stream()
                .map(fileUrl -> CompletableFuture.runAsync(() -> deleteImage(fileUrl), executor))
                .toList();

        // Tüm silme işlemleri tamamlanana kadar bekler
        futures.forEach(CompletableFuture::join);

        executor.shutdown(); // İş parçacığı havuzunu kapat
    }

    // URL'den dosya adını ayıklama
    private String extractFileNameFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
}