package com.example.login.dto;

import lombok.Data;

@Data
public class PhoneDto {
    private Long number;
    private int cityCode;
    private String countryCode;
}
