package com.springtest.account.dto;

import com.springtest.account.enums.AccountTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponseDTO {

    private Long id;
    private String identification;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private Boolean status;
}
