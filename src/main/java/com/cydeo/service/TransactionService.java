package com.cydeo.service;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.impl.AccountOwnershipException;
import com.cydeo.service.impl.BalanceNotSufficientException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public interface TransactionService {

    Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) throws AccountOwnershipException, BalanceNotSufficientException;
        List<Transaction> findAllTransaction();

}
