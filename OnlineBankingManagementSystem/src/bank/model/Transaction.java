package bank.model;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private String accountNumber;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;
    private String relatedAccount;
    private double balanceAfter;
    private String description;

    // Constructor for creating new transactions (without ID and date - they'll be set by database)
    public Transaction(String accountNumber, String transactionType,
                       double amount, String relatedAccount,
                       double balanceAfter, String description) {
        this.transactionId = 0;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = new Timestamp(new Date().getTime()); // Current timestamp
        this.relatedAccount = relatedAccount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    // Constructor for loading from database (with all fields)
    public Transaction(int transactionId, String accountNumber, String transactionType,
                       double amount, Timestamp transactionDate, String relatedAccount,
                       double balanceAfter, String description) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.relatedAccount = relatedAccount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    // Getters
    public int getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public Timestamp getTransactionDate() { return transactionDate; }
    public String getRelatedAccount() { return relatedAccount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("Transaction[ID: %d, Type: %s, Amount: $%.2f, Date: %s]",
                transactionId, transactionType, amount, transactionDate);
    }
}