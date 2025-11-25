//package bank.dao;
//
//import bank.model.Transaction;
//import bank.exception.DatabaseException;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TransactionDAO {
//
//    public void recordTransaction(Transaction transaction) throws DatabaseException {
//        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, related_account, balance_after, description) VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, transaction.getAccountNumber());
//            pstmt.setString(2, transaction.getTransactionType());
//            pstmt.setDouble(3, transaction.getAmount());
//            pstmt.setString(4, transaction.getRelatedAccount());
//            pstmt.setDouble(5, transaction.getBalanceAfter());
//            pstmt.setString(6, transaction.getDescription());
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new DatabaseException("Error recording transaction: " + e.getMessage());
//        }
//    }
//
//    public List<Transaction> getTransactionHistory(String accountNumber) throws DatabaseException {
//        List<Transaction> transactions = new ArrayList<>();
//        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, accountNumber);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                // Use the full constructor with all fields from database
//                Transaction transaction = new Transaction(
//                        rs.getInt("transaction_id"),
//                        rs.getString("account_number"),
//                        rs.getString("transaction_type"),
//                        rs.getDouble("amount"),
//                        rs.getTimestamp("transaction_date"),
//                        rs.getString("related_account"),
//                        rs.getDouble("balance_after"),
//                        rs.getString("description")
//                );
//                transactions.add(transaction);
//            }
//
//        } catch (SQLException e) {
//            throw new DatabaseException("Error retrieving transaction history: " + e.getMessage());
//        }
//        return transactions;
//    }
//}