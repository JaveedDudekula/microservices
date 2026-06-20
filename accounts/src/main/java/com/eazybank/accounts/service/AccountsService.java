package com.eazybank.accounts.service;

import com.eazybank.accounts.dto.CreateAccountsDto;
import com.eazybank.accounts.dto.CustomerDto;

public interface AccountsService {

    void createAccount(CreateAccountsDto createAccountsDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
