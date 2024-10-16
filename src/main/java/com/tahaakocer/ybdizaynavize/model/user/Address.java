package com.tahaakocer.ybdizaynavize.model.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String addressTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //   @JsonBackReference
    private User user; // User ile ilişki

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private String city;
    private String district;
    private String address;
    private String zipCode;


}