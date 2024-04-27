package com.springtest.account.service;


import com.springtest.account.app.ClientServiceApp;
import com.springtest.account.dto.*;
import com.springtest.account.entity.Account;
import com.springtest.account.entity.Transaction;
import com.springtest.account.exception.NotFoundException;
import com.springtest.account.interfaces.IAccountService;
import com.springtest.account.repository.AccountRepository;
import com.springtest.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service("IaccountService")
@Slf4j
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ClientServiceApp clientServiceApp;

    @Transactional
    @Override
    public Account createAccount(final AccountDTO createAccountDTO) {

        clientServiceApp.findClient(createAccountDTO.getClientId());

        final Account account = Account.builder()
                .clientId(createAccountDTO.getClientId())
                .number(UUID.randomUUID())
                .balance(createAccountDTO.getBalance())
                .type(createAccountDTO.getType())
                .status(createAccountDTO.getStatus())
                .build();

        return accountRepository.save(account);

    }

    @Override
    public AccountDTO getAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Cuenta con id " + id + " no encontrada")
        );

        return AccountDTO.builder()
                .clientId(account.getClientId())
                .type(account.getType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }

    @Override
    @Transactional
    public String deleteAccount(Long id) {
        try {
            accountRepository.deleteById(id);
            return "Cuenta borrada exitosamente";
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Cuenta con id " + id + " no encontrada");
        }
    }

    @Override
    public AccountsReportDTO getAccountsReport(Long clientId, Date startDate, Date endDate) {
        ClientResponseDTO clientResponseDTO = clientServiceApp.findClient(clientId);

        List<Account> accounts = accountRepository.findByClientId(clientId);

        return buildAccountsReportDTO(clientId, clientResponseDTO, accounts, startDate, endDate);
    }

    @Override
    public void deleteAccountsByClientId(Long clientId) {
        List<Long> accounts = accountRepository.findByClientId(clientId)
                .stream().map(Account::getId).collect(Collectors.toList());

        accountRepository.deleteAllById(accounts);
    }



    private AccountsReportDTO buildAccountsReportDTO(Long clientId, ClientResponseDTO clientResponseDTO, List<Account> accounts, Date startDate, Date endDate) {
        AccountsReportDTO accountsReportDTO = new AccountsReportDTO();
        accountsReportDTO.setClientId(clientId);
        accountsReportDTO.setClientIdentification(clientResponseDTO.getIdentification());
        accountsReportDTO.setClientName(clientResponseDTO.getName());

        List<AccountDetailDTO> accountDetailList = accounts.stream()
                .map(account -> buildAccountDetailDTO(account, startDate, endDate))
                .collect(Collectors.toList());

        accountsReportDTO.setAccountsDetail(accountDetailList);

        return accountsReportDTO;
    }

    private AccountDetailDTO buildAccountDetailDTO(Account account, Date startDate, Date endDate) {
        AccountDetailDTO accountDetailDTO = new AccountDetailDTO();
        accountDetailDTO.setType(account.getType());
        accountDetailDTO.setStatus(account.getStatus());
        accountDetailDTO.setNumber(account.getNumber());
        accountDetailDTO.setId(account.getId());
        accountDetailDTO.setBalance(account.getBalance());

        List<TransactionDTO> transactions = transactionRepository.findTransactionsByAccountAndDateRange(account.getId(), startDate, endDate)
                .stream()
                .map(this::buildTransactionDTO)
                .collect(Collectors.toList());

        accountDetailDTO.setTransactions(transactions);

        return accountDetailDTO;
    }

    private TransactionDTO buildTransactionDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .previousBalance(transaction.getPreviousBalance())
                .finalBalance(transaction.getFinalBalance())
                .date(transaction.getDate())
                .build();
    }

}
