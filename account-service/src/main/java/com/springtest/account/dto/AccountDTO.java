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
public class AccountDTO {

    @NotNull(message = "clientId is required")
    private Long clientId;

    @NotNull(message = "type is required")
    private AccountTypeEnum type;

    @NotNull(message = "balance is required")
    @Min(value = 0, message = "balance must be greater than or equal to 0")
    private Double balance;

    @NotNull(message = "status is required")
    private Boolean status;
}
