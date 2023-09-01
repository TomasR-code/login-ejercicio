package com.example.login.data;

import com.example.login.dto.PhoneDto;
import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ResponseLogin {
    private UUID id;
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private Timestamp created;
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private Timestamp lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;
}

