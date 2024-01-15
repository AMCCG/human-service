package com.backend.microservices.humanservice.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HumanEntity {
    private int id;
    private String idCard;
    private String thaiTitle;
    private String thaiFirstName;
    private String thaiLastName;
    private String englishTitle;
    private String englishFirstName;
    private String englishLastName;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phone;
    private int profileImageId;

}
