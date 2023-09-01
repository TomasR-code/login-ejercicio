package com.example.login.data;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
