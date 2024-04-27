package com.springtest.account.service;


import com.springtest.account.dto.CreateTransactionDTO;
import com.springtest.account.dto.TransactionDTO;
import com.springtest.account.entity.Account;
import com.springtest.account.entity.Transaction;
import com.springtest.account.exception.InsufficientBalanceException;
import com.springtest.account.exception.NotFoundException;
import com.springtest.account.interfaces.ITransactionService;
import com.springtest.account.repository.AccountRepository;
import com.springtest.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service("ItransactionService")
@Slf4j
public class TransactionService implements ITransactionService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Override
    @Transactional
    public TransactionDTO createTransaction(CreateTransactionDTO createTransactionDTO) {

        Account account = accountRepository.findById(createTransactionDTO.getAccountId()).orElseThrow(
                () -> new NotFoundException("Cuenta con id " + createTransactionDTO.getAccountId() + " no encontrada")
        );

        double balance = account.getBalance();
        double amount = createTransactionDTO.getAmount();

        if(balance + amount < 0){
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .previousBalance(balance)
                .finalBalance(balance + amount)
                .account(account)
                .build();

        transactionRepository.save(transaction);

        account.setBalance(balance + amount);
        accountRepository.save(account);

        return mapTransactionToDTO(transaction);
    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Movimiento con id " + id + " no encontrado")
        );

        return mapTransactionToDTO(transaction);
    }

    private TransactionDTO mapTransactionToDTO (Transaction transaction){
        return TransactionDTO.builder()
                .id(transaction.getId())
                .previousBalance(transaction.getPreviousBalance())
                .finalBalance(transaction.getFinalBalance())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .build();
    }
}
