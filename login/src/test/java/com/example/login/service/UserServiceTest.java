package com.example.login.service;

import com.example.login.data.ErrorInfo;
import com.example.login.data.ErrorResponse;
import com.example.login.data.RequestSignUp;
import com.example.login.data.Response;
import com.example.login.data.ResponseLogin;
import com.example.login.dto.SignUpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.login.entity.UserEntity;
import com.example.login.mapper.UserEntityMapper;
import com.example.login.repository.UserRepository;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEntityMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final String EMAIL = "test@example.com";
    private final String PASSWORD = "Test1234";
    private final String TOKEN = "testToken";

    @Test
    void testSignUpWithValidInput() {
        RequestSignUp signUpDto = new RequestSignUp();
        signUpDto.setEmail(EMAIL);
        signUpDto.setPassword(PASSWORD);

        when(passwordEncoder.encode(signUpDto.getPassword())).thenReturn("hashedPassword");
        when(jwtTokenService.generateToken(signUpDto.getEmail())).thenReturn(TOKEN);
        when(userMapper.toEntity(signUpDto)).thenReturn(new UserEntity());
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
        when(userMapper.toSignUp(any(UserEntity.class))).thenReturn(new SignUpDto());

        Response response = userService.signUp(signUpDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertTrue(response.getResponse() instanceof SignUpDto);
    }

    @Test
    void testSignUpWithInvalidEmailFormat() {
        RequestSignUp signUpDto = new RequestSignUp();
        signUpDto.setEmail("test@example.comom");

        Response response = userService.signUp(signUpDto);

        ErrorResponse expectedErrorResponse = new ErrorResponse();
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCodigo(400);
        errorInfo.setDetail("El correo electrónico no tiene el formato correcto.");
        expectedErrorResponse.setErrors(Collections.singletonList(errorInfo));

        ErrorResponse actualErrorResponse = (ErrorResponse) response.getResponse();

        for (int i = 0; i < expectedErrorResponse.getErrors().size(); i++) {
            ErrorInfo expectedErrorInfo = expectedErrorResponse.getErrors().get(i);
            ErrorInfo actualErrorInfo = actualErrorResponse.getErrors().get(i);

            Assertions.assertEquals(expectedErrorInfo.getCodigo(), actualErrorInfo.getCodigo());
            Assertions.assertEquals(expectedErrorInfo.getDetail(), actualErrorInfo.getDetail());
        }
    }

    @Test
    void testSignUpWithInvalidPassword() {
        RequestSignUp signUpDto = new RequestSignUp();
        signUpDto.setEmail(EMAIL);
        signUpDto.setPassword("11111111");

        Response response = userService.signUp(signUpDto);

        ErrorResponse expectedErrorResponse = new ErrorResponse();
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCodigo(400);
        errorInfo.setDetail("La contraseña no cumple los requisitos.");
        expectedErrorResponse.setErrors(Collections.singletonList(errorInfo));

        ErrorResponse actualErrorResponse = (ErrorResponse) response.getResponse();

        for (int i = 0; i < expectedErrorResponse.getErrors().size(); i++) {
            ErrorInfo expectedErrorInfo = expectedErrorResponse.getErrors().get(i);
            ErrorInfo actualErrorInfo = actualErrorResponse.getErrors().get(i);

            Assertions.assertEquals(expectedErrorInfo.getCodigo(), actualErrorInfo.getCodigo());
            Assertions.assertEquals(expectedErrorInfo.getDetail(), actualErrorInfo.getDetail());
        }
    }

    @Test
    void testSignUpWithExistingUser() {
        RequestSignUp signUpDto = new RequestSignUp();
        signUpDto.setEmail(EMAIL);
        signUpDto.setPassword(PASSWORD);

        when(userRepository.findByEmail(EMAIL)).thenReturn(new UserEntity());

        Response response = userService.signUp(signUpDto);

        ErrorResponse expectedErrorResponse = new ErrorResponse();
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCodigo(400);
        errorInfo.setDetail("El usuario ya existe.");
        expectedErrorResponse.setErrors(Collections.singletonList(errorInfo));

        ErrorResponse actualErrorResponse = (ErrorResponse) response.getResponse();

        for (int i = 0; i < expectedErrorResponse.getErrors().size(); i++) {
            ErrorInfo expectedErrorInfo = expectedErrorResponse.getErrors().get(i);
            ErrorInfo actualErrorInfo = actualErrorResponse.getErrors().get(i);

            Assertions.assertEquals(expectedErrorInfo.getCodigo(), actualErrorInfo.getCodigo());
            Assertions.assertEquals(expectedErrorInfo.getDetail(), actualErrorInfo.getDetail());
        }
    }

    @Test
    void testSignUpWithDatabaseErrorDuringSave() {
        RequestSignUp signUpDto = new RequestSignUp();
        signUpDto.setEmail(EMAIL);
        signUpDto.setPassword(PASSWORD);

        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(jwtTokenService.generateToken(any())).thenReturn(TOKEN);
        when(userMapper.toEntity((RequestSignUp) any())).thenReturn(new UserEntity());
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        Response response = userService.signUp(signUpDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Database error", response.getResponse());
    }

    @Test
    void testLoginWithCorrectCredentials() {
        String email = EMAIL;
        String password = PASSWORD;
        String token = TOKEN;

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setToken(token);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(userMapper.toResponseLogin(user)).thenReturn(new ResponseLogin());
        when(jwtTokenService.generateToken(user.getEmail())).thenReturn("newToken");

        Response response = userService.login(email, password, token);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertTrue(response.getResponse() instanceof ResponseLogin);
    }

    @Test
    void testLoginWithIncorrectEmail() {
        String email = EMAIL;

        when(userRepository.findByEmail(email)).thenReturn(null);

        Response response = userService.login(email, PASSWORD, TOKEN);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getResponse() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getResponse();
        Assertions.assertEquals(1, errorResponse.getErrors().size());
        Assertions.assertEquals("email incorrecto.", errorResponse.getErrors().get(0).getDetail());
    }

    @Test
    void testLoginWithIncorrectPassword() {
        String email = EMAIL;
        String password = PASSWORD;
        String token = TOKEN;

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("DifferentPassword"));
        user.setToken(token);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        Response response = userService.login(email, password, token);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getResponse() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getResponse();
        Assertions.assertEquals(1, errorResponse.getErrors().size());
        Assertions.assertEquals("contraseña incorrecta.", errorResponse.getErrors().get(0).getDetail());
    }

    @Test
    void testLoginWithIncorrectToken() {
        String email = EMAIL;
        String password = PASSWORD;

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setToken("DifferentToken");

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        Response response = userService.login(email, password, TOKEN);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertTrue(response.getResponse() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getResponse();
        Assertions.assertEquals(1, errorResponse.getErrors().size());
        Assertions.assertEquals("token incorrecto.", errorResponse.getErrors().get(0).getDetail());
    }

    @Test
    void testLoginWithDatabaseErrorDuringSave() {
        String email = EMAIL;
        String password = PASSWORD;
        String token = TOKEN;

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setToken(token);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(userMapper.toResponseLogin(user)).thenReturn(new ResponseLogin());
        when(jwtTokenService.generateToken(user.getEmail())).thenReturn("newToken");
        when(userMapper.toEntity((ResponseLogin) any())).thenReturn(new UserEntity());
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        Response response = userService.login(email, password, token);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Database error", response.getResponse());
    }
}

