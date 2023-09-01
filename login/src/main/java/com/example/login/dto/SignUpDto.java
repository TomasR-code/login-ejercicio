package com.example.login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SignUpDto {

    private UUID id;
    private Timestamp created;
    private Timestamp lastLogin;
    private String token;
    private boolean isActive;
}
