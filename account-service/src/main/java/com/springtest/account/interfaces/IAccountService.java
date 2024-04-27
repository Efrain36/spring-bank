package com.springtest.account.interfaces;

import com.springtest.account.dto.AccountDTO;
import com.springtest.account.dto.AccountsReportDTO;
import com.springtest.account.entity.Account;

import java.util.Date;

public interface IAccountService {
    Account createAccount(AccountDTO createAccountDTO);

    AccountDTO getAccount(Long id);

    String deleteAccount(Long id);

    AccountsReportDTO getAccountsReport(Long clientId, Date startDate, Date endDate);

    void deleteAccountsByClientId(Long clientId);
}
