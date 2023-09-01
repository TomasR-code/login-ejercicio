package com.example.login.common;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class ErrorDetails {
    private Timestamp timestamp;
    private int code;
    private String detail;
}
