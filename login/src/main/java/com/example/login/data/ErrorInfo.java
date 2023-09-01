package com.example.login.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ErrorInfo {
    private Timestamp timestamp;
    private int codigo;
    private String detail;
}
