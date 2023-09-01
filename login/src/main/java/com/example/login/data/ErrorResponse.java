package com.example.login.data;

import lombok.Data;
import java.util.List;

@Data
public class ErrorResponse {
    private List<ErrorInfo> errors;
}
