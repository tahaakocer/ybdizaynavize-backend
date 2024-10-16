package com.tahaakocer.ybdizaynavize.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {
    private long id;
    private UUID userId;
    private AddressUserDto user;
    private String addressTitle;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private String city;
    private String district;
    private String address;
    private String zipCode;

}
