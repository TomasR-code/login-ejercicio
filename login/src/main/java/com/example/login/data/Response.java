package com.example.login.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Response {
    @JsonIgnore
    private boolean success;
    private Object response;
}
