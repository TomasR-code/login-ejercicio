package com.example.login.mapper;

import com.example.login.data.RequestSignUp;
import com.example.login.data.ResponseLogin;
import com.example.login.dto.SignUpDto;
import com.example.login.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    SignUpDto toSignUp(UserEntity userEntity);

    UserEntity toEntity(RequestSignUp requestSignUpDto);

    UserEntity toEntity(ResponseLogin responseLogin);

    ResponseLogin toResponseLogin(UserEntity user);
}


