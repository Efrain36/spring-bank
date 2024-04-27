package com.springtest.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionDTO {

    @NotNull(message = "amount is required")
    private Double amount;
    @NotNull(message = "accountId is required")
    private Long accountId;
}
