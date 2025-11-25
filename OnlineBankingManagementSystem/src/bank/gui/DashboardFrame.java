package bank.gui;

import bank.model.User;
import bank.service.BankService;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private User user;
    private BankService bankService;

    public DashboardFrame(User user) {
        this.user = user;
        this.bankService = new BankService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Banking System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Header
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullName(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createAccountBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton transferBtn = new JButton("Transfer");
        JButton viewAccountsBtn = new JButton("View All Accounts");
        JButton transactionHistoryBtn = new JButton("Transaction History");

        createAccountBtn.addActionListener(e -> new AccountFrame(bankService).setVisible(true));
        depositBtn.addActionListener(e -> new TransactionFrame(bankService, "DEPOSIT").setVisible(true));
        withdrawBtn.addActionListener(e -> new TransactionFrame(bankService, "WITHDRAW").setVisible(true));
        transferBtn.addActionListener(e -> new TransactionFrame(bankService, "TRANSFER").setVisible(true));
        viewAccountsBtn.addActionListener(e -> viewAllAccounts());
        transactionHistoryBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Transaction History feature"));

        buttonsPanel.add(createAccountBtn);
        buttonsPanel.add(depositBtn);
        buttonsPanel.add(withdrawBtn);
        buttonsPanel.add(transferBtn);
        buttonsPanel.add(viewAccountsBtn);
        buttonsPanel.add(transactionHistoryBtn);

        panel.add(buttonsPanel, BorderLayout.CENTER);
        add(panel);
    }

    private void viewAllAccounts() {
        try {
            var accounts = bankService.getAllAccounts();
            if (accounts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No accounts found.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("All Accounts:\n\n");
            for (var account : accounts) {
                sb.append(account.getAccountInfo()).append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}