package com.springtest.account.controller;

import com.springtest.account.dto.AccountDTO;
import com.springtest.account.dto.AccountsReportDTO;
import com.springtest.account.entity.Account;
import com.springtest.account.interfaces.IAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/cuentas")
public class AccountsController {

    @Autowired
    IAccountService iAccountService;

    @PostMapping
    public ResponseEntity<Account> createAccount (
            @Valid @RequestBody AccountDTO createAccountDTO
    ) {
        return ResponseEntity.ok(iAccountService.createAccount(createAccountDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount (@PathVariable Long id) {
        return ResponseEntity.ok(iAccountService.getAccount(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount (@PathVariable Long id) {
        return ResponseEntity.ok(iAccountService.deleteAccount(id));
    }

    @GetMapping("/reportes")
    public ResponseEntity<AccountsReportDTO> getAccount (
            @RequestParam("clientId") Long clientId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("startDate") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("endDate") Date endDate

    ) {
        return ResponseEntity.ok(iAccountService.getAccountsReport(clientId, startDate, endDate));
    }
}
