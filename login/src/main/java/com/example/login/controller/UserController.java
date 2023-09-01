package com.example.login.controller;

import com.example.login.data.ErrorResponse;
import com.example.login.data.Response;
import com.example.login.data.RequestSignUp;
import com.example.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/app")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
            @RequestParam("email") @NotBlank String email,
            @RequestParam("password") @NotBlank String password,
            @RequestParam("token") @NotBlank String token) {

        Response response = userService.login(email, password, token);
        if (response.getResponse() instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response.getResponse();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUp(@RequestBody RequestSignUp signUpDto) {
        Response response = userService.signUp(signUpDto);

        if (response.getResponse() instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response.getResponse();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    private String extractTokenFromHeader(String tokenHeader) {
        return tokenHeader.replace("Bearer ", "");
    }
}

