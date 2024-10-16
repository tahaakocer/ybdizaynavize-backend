package com.tahaakocer.ybdizaynavize.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tahaakocer.ybdizaynavize.model.user.Address;
import com.tahaakocer.ybdizaynavize.model.user.Role;
import com.tahaakocer.ybdizaynavize.model.user.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private UUID id;
    private String email;
    private String password;
    private String phoneNumber;
    private List<AddressDto> addressEntities;
    private UserStatus userStatus;
    private Set<Role> authorities;

    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;

}
