package com.example.login.common;

import lombok.Data;
import java.util.List;

@Data
public class ErrorResponse {
    private List<ErrorDetails> error;
}

