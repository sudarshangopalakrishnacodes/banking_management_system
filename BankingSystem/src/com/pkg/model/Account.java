package com.pkg.model;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.logging.Level;
public class Account {

    private static final Logger LOGGER = Logger.getLogger(Account.class.getName());
    private int accountNumber;
    private double accountBalance;
    private int clientId;
    private String accountType;

    public String getAccountType() {
        return accountType;
    }

    public Account(int accountNumber, double accountBalance,String accountType, int clientId){
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.clientId = clientId;
        this.accountType = accountType;
    }

    public Account()
    {

    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountNumber()
    {
        return accountNumber;
    }

    public double getAccountBalance()
    {
        return accountBalance;
    }

    public int getClientId()
    {
        return clientId;
    }

    public void setAccountNumber(int accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public void setAccountBalance(double accountBalance)
    {
        this.accountBalance = accountBalance;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public void deposit(double amount)
    {
        if(amount > 0)
        {
            this.accountBalance += amount;
            LOGGER.info("Amount deposited successfully");
        }
    }

    public boolean withdraw(double amount)
    {
        boolean success = false;
       if(amount <= 0)
       {
           LOGGER.info("Withdrawal Failed: Amount must be positive");
       }
       else if(this.accountBalance < amount)
       {
           LOGGER.info("Withdrawal failed: Insufficient Funds");
       }
       else {
           this.accountBalance -= amount;
           success =true;
       }
       return success;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountBalance=" + accountBalance +
                ", clientId=" + clientId +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber == account.accountNumber && Double.compare(accountBalance, account.accountBalance) == 0 && clientId == account.clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountBalance, clientId);
    }
}
