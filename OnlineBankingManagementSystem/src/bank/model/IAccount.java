package bank.model;

import bank.exception.InsufficientBalanceException;

public interface IAccount {
    void deposit(double amount);
    boolean withdraw(double amount) throws InsufficientBalanceException;
    double getBalance();
    String getAccountInfo();
}