package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.BadRequestException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
private  final TransactionRepository transactionRepository;
    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) throws AccountOwnershipException, BalanceNotSufficientException {
/*
* -if sender or receiver is null?
* -if sender and receiver is same account?
* -if sender has enough balance to make transfer?
* -if both account are checking if not one of then saving it needs to be same userId
* */


        validateAccount(sender,receiver);
        checkAccountOwnership(sender,receiver);
        executebalanceAndUpdateIfRequired(amount,sender,receiver);
        Transaction transaction=Transaction.builder().amount(amount).sender(sender.getId()).
                receiver(receiver.getId()).creatData(creationDate).message(message).build();
        //makeTransfer
        //save in to db n return it
return transactionRepository.save(transaction);

    }

    private void executebalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) throws BalanceNotSufficientException {

        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify sender has enough balance to send
       return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)>=0;
    }

    private void checkAccountOwnership(Account sender, Account receiver) throws AccountOwnershipException {
        //write an if statement that checkes if one of the account is saving
        //and user of sender or receiver is not them same throw AccountOwnershipException
        if(sender.getAccountType().equals(AccountType.SAVING)||receiver.getAccountType()
                .equals(AccountType.SAVING)&&! sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnershipException("if one of the account is saving user must be the same for sender and reciever");

        }
    }

    public void validateAccount(Account sender, Account receiver){
        /*
        * -if any of the account is null
        * if account ids are the same(same account)
        * -if the account exist in the database(repository
        *
        * */
        if(sender==null||receiver==null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        //if acxcount a same throw badrequestExeception with saying account need to be difrent
        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account need to be different than receiver");
        }
       findAccountById(sender.getId());
        findAccountById(receiver.getId());



    }

    private void findAccountById(UUID id) {
        accountRepository.findById(id);


    }


    @Override
    public List<Transaction> findAllTransaction() {
        return null;









    }
}
