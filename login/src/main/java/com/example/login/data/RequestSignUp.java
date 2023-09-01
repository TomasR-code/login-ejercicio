package com.example.login.data;

import com.example.login.dto.PhoneDto;
import lombok.Data;
import java.util.List;


import javax.validation.constraints.*;
import java.util.List;

@Data
public class RequestSignUp {

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email no puede estar en blanco")
    private String email;

    @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String password;

    @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
    private List<PhoneDto> phones;
}



