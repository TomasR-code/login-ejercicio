package com.example.login.service;

import com.example.login.data.ErrorInfo;
import com.example.login.data.ErrorResponse;
import com.example.login.data.RequestSignUp;
import com.example.login.data.Response;
import com.example.login.data.ResponseLogin;
import com.example.login.entity.UserEntity;
import com.example.login.mapper.UserEntityMapper;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserEntityMapper userMapper;

    public Response signUp(RequestSignUp signUpDto) {
        Response response = new Response();
        response.setSuccess(false);
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            if (!isValidEmailFormat(signUpDto.getEmail())) {
                return errorMsgResponse(response, now,"El correo electrónico no tiene el formato correcto.");
            } else if (!isValidPassword(signUpDto.getPassword())) {
                return errorMsgResponse(response, now, "La contraseña no cumple los requisitos.");
            } else {
                signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
                if (isUserExist(signUpDto)) {
                    return errorMsgResponse(response, now, "El usuario ya existe.");
                }
                UserEntity user = userMapper.toEntity(signUpDto);
                user.setCreated(now);
                user.setLastLogin(now);
                user.setToken(jwtTokenService.generateToken(user.getEmail()));
                user.setActive(true);

                userRepository.save(user);

                response.setSuccess(true);
                response.setResponse(userMapper.toSignUp(user));
            }
        } catch (Exception e) {
            response.setResponse(e.getMessage());
        }
        return response;
    }

    public Response login(String email, String password, @NotBlank String token) {
        Response response = new Response();
        response.setSuccess(false);
        try {
            UserEntity user = userRepository.findByEmail(email);
            Timestamp now = new Timestamp(new Date().getTime());
            if (user == null) {
                return errorMsgResponse(response, now, "email incorrecto.");
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return errorMsgResponse(response, now, "contraseña incorrecta.");
            }
            if (!token.equals(user.getToken())) {
                return errorMsgResponse(response, now, "token incorrecto.");
            }
            ResponseLogin responseLogin = userMapper.toResponseLogin(user);
            responseLogin.setLastLogin(now);
            responseLogin.setToken(jwtTokenService.generateToken(user.getEmail()));
            user = userMapper.toEntity(responseLogin);
            userRepository.save(user);
            response.setResponse(responseLogin);
            response.setSuccess(true);

        } catch (Exception e) {
            response.setResponse(e.getMessage());
        }
        return response;
    }

    public boolean IsSaveNewToken(String email, String token) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null){
            return false;
        }
        user.setToken(token);
        userRepository.save(user);
        return true;
    }

    private Response errorMsgResponse(Response response, Timestamp now, String message) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setTimestamp(now);
        errorInfo.setCodigo(400);
        errorInfo.setDetail(message);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(Collections.singletonList(errorInfo));
        response.setResponse(errorResponse);

        return response;
    }

    private boolean isUserExist(RequestSignUp signUpDto) {
        UserEntity user = userRepository.findByEmail(signUpDto.getEmail());
        return user != null;
    }


    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d.*\\d)(?=.*[a-z]).{8,12}$";
        return password.matches(regex);
    }
}
