 package com.springtest.account.controller;


import com.springtest.account.dto.CreateTransactionDTO;
import com.springtest.account.dto.TransactionDTO;
import com.springtest.account.entity.Transaction;
import com.springtest.account.interfaces.ITransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 @RestController
@RequestMapping("/movimientos")
public class TransactionsController {

  @Autowired
  ITransactionService iTransactionService;


  @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction (
          @Valid @RequestBody CreateTransactionDTO createTransactionDTO
  ) {
   return ResponseEntity.ok(iTransactionService.createTransaction(createTransactionDTO));
  }

     @GetMapping("/{id}")
     public ResponseEntity<TransactionDTO> getTransaction (@PathVariable Long id) {
         return ResponseEntity.ok(iTransactionService.getTransaction(id));
     }
}
