package com.springtest.account.interfaces;

import com.springtest.account.dto.CreateTransactionDTO;
import com.springtest.account.dto.TransactionDTO;
import com.springtest.account.entity.Transaction;

public interface ITransactionService {
    TransactionDTO createTransaction(CreateTransactionDTO createTransactionDTO);

    TransactionDTO getTransaction(Long id);

    ;
}
