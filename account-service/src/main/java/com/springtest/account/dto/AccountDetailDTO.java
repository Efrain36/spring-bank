package com.springtest.account.dto;

import com.springtest.account.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetailDTO {

    private Long id;
    private UUID number;
    private AccountTypeEnum type;
    private Double balance;
    private Boolean status;
    private List<TransactionDTO> transactions;

}
