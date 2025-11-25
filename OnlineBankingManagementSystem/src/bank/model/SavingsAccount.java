package bank.model;

import bank.exception.InsufficientBalanceException;
import bank.util.Constants;

public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance, "SAVINGS");
    }

    @Override
    public boolean withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (balance - amount >= Constants.SAVINGS_MIN_BALANCE) {
            balance -= amount;
            return true;
        } else {
            throw new InsufficientBalanceException(
                    "Cannot withdraw! Minimum balance of $" + Constants.SAVINGS_MIN_BALANCE + " must be maintained.");
        }
    }
}