package com.example.login.mapper;

import com.example.login.data.RequestSignUp;
import com.example.login.data.ResponseLogin;
import com.example.login.dto.PhoneDto;
import com.example.login.dto.SignUpDto;
import com.example.login.entity.PhoneEntity;
import com.example.login.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-31T22:32:21-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.20 (Amazon.com Inc.)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public SignUpDto toSignUp(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        SignUpDto signUpDto = new SignUpDto();

        signUpDto.setId( userEntity.getId() );
        signUpDto.setCreated( userEntity.getCreated() );
        signUpDto.setLastLogin( userEntity.getLastLogin() );
        signUpDto.setToken( userEntity.getToken() );
        signUpDto.setActive( userEntity.isActive() );

        return signUpDto;
    }

    @Override
    public UserEntity toEntity(RequestSignUp requestSignUpDto) {
        if ( requestSignUpDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName( requestSignUpDto.getName() );
        userEntity.setEmail( requestSignUpDto.getEmail() );
        userEntity.setPassword( requestSignUpDto.getPassword() );
        userEntity.setPhones( phoneDtoListToPhoneEntityList( requestSignUpDto.getPhones() ) );

        return userEntity;
    }

    @Override
    public UserEntity toEntity(ResponseLogin responseLogin) {
        if ( responseLogin == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( responseLogin.getId() );
        userEntity.setCreated( responseLogin.getCreated() );
        userEntity.setLastLogin( responseLogin.getLastLogin() );
        userEntity.setToken( responseLogin.getToken() );
        userEntity.setActive( responseLogin.isActive() );
        userEntity.setName( responseLogin.getName() );
        userEntity.setEmail( responseLogin.getEmail() );
        userEntity.setPassword( responseLogin.getPassword() );
        userEntity.setPhones( phoneDtoListToPhoneEntityList( responseLogin.getPhones() ) );

        return userEntity;
    }

    @Override
    public ResponseLogin toResponseLogin(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        ResponseLogin responseLogin = new ResponseLogin();

        responseLogin.setId( user.getId() );
        responseLogin.setCreated( user.getCreated() );
        responseLogin.setLastLogin( user.getLastLogin() );
        responseLogin.setToken( user.getToken() );
        responseLogin.setActive( user.isActive() );
        responseLogin.setName( user.getName() );
        responseLogin.setEmail( user.getEmail() );
        responseLogin.setPassword( user.getPassword() );
        responseLogin.setPhones( phoneEntityListToPhoneDtoList( user.getPhones() ) );

        return responseLogin;
    }

    protected PhoneEntity phoneDtoToPhoneEntity(PhoneDto phoneDto) {
        if ( phoneDto == null ) {
            return null;
        }

        PhoneEntity phoneEntity = new PhoneEntity();

        phoneEntity.setNumber( phoneDto.getNumber() );
        phoneEntity.setCityCode( phoneDto.getCityCode() );
        phoneEntity.setCountryCode( phoneDto.getCountryCode() );

        return phoneEntity;
    }

    protected List<PhoneEntity> phoneDtoListToPhoneEntityList(List<PhoneDto> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneEntity> list1 = new ArrayList<PhoneEntity>( list.size() );
        for ( PhoneDto phoneDto : list ) {
            list1.add( phoneDtoToPhoneEntity( phoneDto ) );
        }

        return list1;
    }

    protected PhoneDto phoneEntityToPhoneDto(PhoneEntity phoneEntity) {
        if ( phoneEntity == null ) {
            return null;
        }

        PhoneDto phoneDto = new PhoneDto();

        phoneDto.setNumber( phoneEntity.getNumber() );
        phoneDto.setCityCode( phoneEntity.getCityCode() );
        phoneDto.setCountryCode( phoneEntity.getCountryCode() );

        return phoneDto;
    }

    protected List<PhoneDto> phoneEntityListToPhoneDtoList(List<PhoneEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneDto> list1 = new ArrayList<PhoneDto>( list.size() );
        for ( PhoneEntity phoneEntity : list ) {
            list1.add( phoneEntityToPhoneDto( phoneEntity ) );
        }

        return list1;
    }
}
