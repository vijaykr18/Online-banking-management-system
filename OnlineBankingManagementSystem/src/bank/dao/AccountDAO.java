//package bank.dao;
//
//import bank.model.Account;
//import bank.model.SavingsAccount;
//import bank.model.CurrentAccount;
//import bank.exception.AccountNotFoundException;
//import bank.exception.DatabaseException;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AccountDAO {
//
//    public void createAccount(Account account) throws DatabaseException {
//        String sql = "INSERT INTO accounts (account_number, account_holder, balance, account_type) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, account.getAccountNumber());
//            pstmt.setString(2, account.getAccountHolder());
//            pstmt.setDouble(3, account.getBalance());
//            pstmt.setString(4, account.getAccountType());
//
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new DatabaseException("Error creating account: " + e.getMessage());
//        }
//    }
//
//    public Account getAccount(String accountNumber) throws AccountNotFoundException, DatabaseException {
//        String sql = "SELECT * FROM accounts WHERE account_number = ?";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, accountNumber);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                String accNumber = rs.getString("account_number");
//                String holder = rs.getString("account_holder");
//                double balance = rs.getDouble("balance");
//                String type = rs.getString("account_type");
//
//                if ("SAVINGS".equals(type)) {
//                    return new SavingsAccount(accNumber, holder, balance);
//                } else {
//                    return new CurrentAccount(accNumber, holder, balance);
//                }
//            } else {
//                throw new AccountNotFoundException("Account not found: " + accountNumber);
//            }
//
//        } catch (SQLException e) {
//            throw new DatabaseException("Error retrieving account: " + e.getMessage());
//        }
//    }
//
//    public List<Account> getAllAccounts() throws DatabaseException {
//        List<Account> accounts = new ArrayList<>();
//        String sql = "SELECT * FROM accounts";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                String accNumber = rs.getString("account_number");
//                String holder = rs.getString("account_holder");
//                double balance = rs.getDouble("balance");
//                String type = rs.getString("account_type");
//
//                Account account;
//                if ("SAVINGS".equals(type)) {
//                    account = new SavingsAccount(accNumber, holder, balance);
//                } else {
//                    account = new CurrentAccount(accNumber, holder, balance);
//                }
//                accounts.add(account);
//            }
//
//        } catch (SQLException e) {
//            throw new DatabaseException("Error retrieving accounts: " + e.getMessage());
//        }
//        return accounts;
//    }
//
//    public void updateBalance(String accountNumber, double newBalance) throws DatabaseException {
//        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setDouble(1, newBalance);
//            pstmt.setString(2, accountNumber);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new DatabaseException("Error updating account balance: " + e.getMessage());
//        }
//    }
//}