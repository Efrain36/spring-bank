package com.springtest.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsReportDTO {

    private Long clientId;
    private String clientName;
    private String clientIdentification;
    private List<AccountDetailDTO> accountsDetail;

}
