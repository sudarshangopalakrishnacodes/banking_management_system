package com.pkg.service;

import com.pkg.model.Account;
import com.pkg.model.SavingAccount;
import com.pkg.repository.AccountRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AccountService {

    private static final Logger LOGGER = Logger.getLogger(AccountService.class.getName());
    AccountRepository accountRepository = new AccountRepository();

    public boolean createAccount(Account account) throws SQLException {

        String account_type = account.getAccountType();
        int account_number = account.getAccountNumber();
        double balance = account.getAccountBalance();
        int client_id = account.getClientId();
        boolean success = false;
        if(account_type == null  || client_id <=0 )
        {
            LOGGER.warning("Account type or account number is invalid");
            return false;
        }
        else {
          success =  accountRepository.createAccount(account);

          if(success){
              LOGGER.info("Account successfully created");

          }
          else {
              LOGGER.warning("Account creation failed");
          }

        }
        return success;

    }
    public Account getAccount(int account_id) throws SQLException {


        if(account_id <= 0)
        {
            LOGGER.warning("Account id is invalid");
            return null;
        }

            Account  account = accountRepository.getAccountByNumber(account_id);
            if(account == null)
            {
                LOGGER.warning("Account not found");
            }
            else {
                LOGGER.info("Account found");
            }

        return account;
    }
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        accounts =   accountRepository.getAllAccounts();
        if(accounts.isEmpty())
        {
            LOGGER.warning("No accounts found");
        }
        else {
            LOGGER.info(accounts.size() + " accounts found");
        }
        return accounts;
    }

    public boolean deleteAccount(int account_id) throws SQLException {
        boolean success =false;
        if(account_id <= 0)
        {
            LOGGER.warning("Account id is invalid");
            return false;
        }
        else {
          success =   accountRepository.deleteAccount(account_id);
          if(success)
          {
              LOGGER.info("Account successfully deleted");

          }
          else {
                LOGGER.warning("Account deletion failed");
          }
        }
        return success;
    }

    public boolean deposit(int accountNumber, double amount){
        boolean success =false;
        if(accountNumber <= 0 || amount <=0)
        {
            LOGGER.warning("Account number or amount is invalid");
            return success;
        }
        Account account = accountRepository.getAccountByNumber(accountNumber);
        if(account == null)
        {
            LOGGER.warning("Account not found");
            return success;
        }
        double newBalance = account.getAccountBalance() + amount;
        account.setAccountBalance(newBalance);
        success = accountRepository.updateAccount(account);
        if(success)
        {
            LOGGER.info("Deposit successful. New balance is" +newBalance);
        }
        else {
            LOGGER.warning("Account update failed");
        }
        return success;
    }

    public boolean withdraw(int accountNumber, double amount){
        boolean success = false;
        if(accountNumber <= 0 || amount <=0  )
        {
            LOGGER.warning("Account number or amount is invalid");
            return success;
        }

        Account account = accountRepository.getAccountByNumber(accountNumber);
        if(account == null)
        {
            LOGGER.warning("Account not found");
            return success;
        }
        if(account.getAccountBalance() <amount)
        {
            LOGGER.warning("INSUFFICIENT FUNDS");
            return success;
        }
        double newBalance = account.getAccountBalance() -amount;
        account.setAccountBalance(newBalance);
        success =accountRepository.updateAccount(account);
        if(success)
        {
            LOGGER.info("Withdraw successful. New balance is " +newBalance);

        }
        else {
            LOGGER.warning("Amount withdrawal failed");

        }
        return success;
    }

    public boolean applyInterest(int accountNumber, double rate) {
        Account account = accountRepository.getAccountByNumber(accountNumber);
        boolean success = false;

        if (account == null) {
            LOGGER.warning("Account not found with number: " + accountNumber);
            return false;
        }

        if ("savings".equalsIgnoreCase(account.getAccountType())) {
            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setAccountNumber(account.getAccountNumber());
            savingAccount.setAccountBalance(account.getAccountBalance());
            savingAccount.setClientId(account.getClientId());
            savingAccount.setAccountType(account.getAccountType());

            savingAccount.calculateInterest(rate);
            success = accountRepository.updateAccount(savingAccount);

            if (success) {
                LOGGER.info("Interest applied. New balance: " + savingAccount.getAccountBalance());
            } else {
                LOGGER.warning("Failed to update account after applying interest.");
            }
        } else {
            LOGGER.warning("Account is not a savings account. Interest not applicable.");
        }

        return success;
    }

}
