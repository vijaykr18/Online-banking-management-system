package bank.model;

import bank.exception.InsufficientBalanceException;
import bank.util.Constants;

public class CurrentAccount extends Account {
    public CurrentAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance, "CURRENT");
    }

    @Override
    public boolean withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (balance - amount >= -Constants.CURRENT_OVERDRAFT_LIMIT) {
            balance -= amount;
            return true;
        } else {
            throw new InsufficientBalanceException(
                    "Cannot withdraw! Overdraft limit exceeded. Maximum overdraft: $" + Constants.CURRENT_OVERDRAFT_LIMIT);
        }
    }
}