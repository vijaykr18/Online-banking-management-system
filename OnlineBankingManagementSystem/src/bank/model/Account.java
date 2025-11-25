package bank.model;

import bank.exception.InsufficientBalanceException;

public abstract class Account implements IAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected String accountType;

    public Account(String accountNumber, String accountHolder, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.accountType = accountType;
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    @Override
    public abstract boolean withdraw(double amount) throws InsufficientBalanceException;

    @Override
    public String getAccountInfo() {
        return String.format("Account[%s, Holder: %s, Balance: $%.2f, Type: %s]",
                accountNumber, accountHolder, balance, accountType);
    }

    @Override
    public String toString() {
        return getAccountInfo();
    }
}