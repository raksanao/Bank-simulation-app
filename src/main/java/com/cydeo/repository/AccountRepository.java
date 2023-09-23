package com.cydeo.repository;

import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component

public class AccountRepository {
    public static List<Account> accountList = new ArrayList<>();


    public Account save(Account account) {
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return  accountList;
    }

    public Account findById(UUID id) {

        return accountList.stream().filter(account -> account.getId().equals(id)).
                findAny().orElseThrow(()->new RecordnotFoundException("Account does not exist in the database"));
    }

        //task
        //write a method that find the account inside the list, if not;
        //through RecordNotFoundException

        }












