package bank.service;

import bank.dao.InMemoryStorage;
import bank.model.*;
import bank.exception.*;

import java.util.List;

public class BankService {

    public void createAccount(Account account) {
        InMemoryStorage.createAccount(account);
    }

    public void deposit(String accountNumber, double amount) throws AccountNotFoundException {
        Account account = InMemoryStorage.getAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }

        account.deposit(amount);
        InMemoryStorage.updateAccountBalance(accountNumber, account.getBalance());

        // Record transaction
        Transaction transaction = new Transaction(
                accountNumber, "DEPOSIT", amount, null,
                account.getBalance(), "Cash deposit"
        );
        InMemoryStorage.addTransaction(transaction);
    }

    public boolean withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InsufficientBalanceException {

        Account account = InMemoryStorage.getAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }

        boolean success = account.withdraw(amount);
        if (success) {
            InMemoryStorage.updateAccountBalance(accountNumber, account.getBalance());

            // Record transaction
            Transaction transaction = new Transaction(
                    accountNumber, "WITHDRAWAL", amount, null,
                    account.getBalance(), "Cash withdrawal"
            );
            InMemoryStorage.addTransaction(transaction);
        }

        return success;
    }

    public boolean transfer(String fromAccount, String toAccount, double amount)
            throws AccountNotFoundException, InsufficientBalanceException {

        Account fromAcc = InMemoryStorage.getAccount(fromAccount);
        Account toAcc = InMemoryStorage.getAccount(toAccount);

        if (fromAcc == null) {
            throw new AccountNotFoundException("Sender account not found: " + fromAccount);
        }
        if (toAcc == null) {
            throw new AccountNotFoundException("Receiver account not found: " + toAccount);
        }

        // Withdraw from source
        boolean withdrawalSuccess = fromAcc.withdraw(amount);
        if (withdrawalSuccess) {
            // Deposit to target
            toAcc.deposit(amount);

            // Update both accounts
            InMemoryStorage.updateAccountBalance(fromAccount, fromAcc.getBalance());
            InMemoryStorage.updateAccountBalance(toAccount, toAcc.getBalance());

            // Record transactions
            Transaction fromTransaction = new Transaction(
                    fromAccount, "TRANSFER_OUT", amount, toAccount,
                    fromAcc.getBalance(), "Transfer to " + toAccount
            );
            InMemoryStorage.addTransaction(fromTransaction);

            Transaction toTransaction = new Transaction(
                    toAccount, "TRANSFER_IN", amount, fromAccount,
                    toAcc.getBalance(), "Transfer from " + fromAccount
            );
            InMemoryStorage.addTransaction(toTransaction);

            return true;
        }

        return false;
    }

    public Double getBalance(String accountNumber) throws AccountNotFoundException {
        Account account = InMemoryStorage.getAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account.getBalance();
    }

    public List<Account> getAllAccounts() {
        return InMemoryStorage.getAllAccounts();
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        return InMemoryStorage.getTransactionsForAccount(accountNumber);
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = InMemoryStorage.getAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }
}