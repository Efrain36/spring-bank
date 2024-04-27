package com.springtest.client.dto;

import com.springtest.client.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    private  Long id;
    private String identification;
    private String name;
    private GenderEnum gender;
    private int age;
    private String address;
    private String phone;
    private Boolean status;
}
