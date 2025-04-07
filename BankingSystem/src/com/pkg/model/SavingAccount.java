package com.pkg.model;

public class SavingAccount extends Account{



    public void calculateInterest(double interestRate)
    {

        double interest= getAccountBalance()*(interestRate/100);
        setAccountBalance(getAccountBalance() +interest);

    }




}
