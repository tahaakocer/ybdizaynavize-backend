package com.tahaakocer.ybdizaynavize.exception.product;

public class S3UploadImageException extends RuntimeException {
    public S3UploadImageException(String errorUploadingImage) {
        super(errorUploadingImage);
    }
}
