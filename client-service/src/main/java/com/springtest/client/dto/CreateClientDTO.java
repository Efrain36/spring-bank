package com.springtest.client.dto;

import com.springtest.client.enums.GenderEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClientDTO {

    @NotBlank(message = "El numero de identificacion es requerido")
    private String identification;

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotNull(message = "El genero es requerido")
    private GenderEnum gender;

    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    private int age;

    @Size(max = 100, message = "La direccion no debe exceder los 100 caracteres")
    private String address;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "El telefono no es valido")
    private String phone;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "El estado es requerido")
    private Boolean status;
}
