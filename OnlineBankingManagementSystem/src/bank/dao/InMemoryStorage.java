package bank.dao;

import bank.model.Account;
import bank.model.Transaction;
import bank.model.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorage {
    // Thread-safe collections for storage
    private static Map<String, Account> accounts = new ConcurrentHashMap<>();
    private static Map<String, User> users = new ConcurrentHashMap<>();
    private static List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());
    private static int transactionIdCounter = 1;

    static {
        initializeSampleData();
    }

    private static void initializeSampleData() {
        // Create sample users
        users.put("john", new User(1, "john", "password123", "John Doe", "john@email.com"));
        users.put("jane", new User(2, "jane", "password123", "Jane Smith", "jane@email.com"));

        // Create sample accounts
        accounts.put("SA001", new bank.model.SavingsAccount("SA001", "John Doe", 1000.0));
        accounts.put("CA001", new bank.model.CurrentAccount("CA001", "Jane Smith", 500.0));

        System.out.println("In-memory storage initialized with sample data!");
    }

    // Account methods
    public static void createAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public static Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public static List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public static void updateAccountBalance(String accountNumber, double newBalance) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            // Create a new account instance with updated balance
            Account updatedAccount;
            if (account instanceof bank.model.SavingsAccount) {
                updatedAccount = new bank.model.SavingsAccount(accountNumber, account.getAccountHolder(), newBalance);
            } else {
                updatedAccount = new bank.model.CurrentAccount(accountNumber, account.getAccountHolder(), newBalance);
            }
            accounts.put(accountNumber, updatedAccount);
        }
    }

    // User methods
    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    // Transaction methods
    public static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public static List<Transaction> getTransactionsForAccount(String accountNumber) {
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAccountNumber().equals(accountNumber)) {
                accountTransactions.add(t);
            }
        }
        // Sort by date (most recent first)
        accountTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));
        return accountTransactions;
    }

    public static int getNextTransactionId() {
        return transactionIdCounter++;
    }
}