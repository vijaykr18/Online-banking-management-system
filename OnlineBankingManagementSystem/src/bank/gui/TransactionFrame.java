package bank.gui;

import bank.service.BankService;
import bank.exception.*;

import javax.swing.*;
import java.awt.*;

public class TransactionFrame extends JFrame {
    private BankService bankService;
    private JTextField accNumberField, amountField, toAccountField;
    private String transactionType;

    public TransactionFrame(BankService bankService, String type) {
        this.bankService = bankService;
        this.transactionType = type;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Banking System - " + transactionType);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Account Number
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Account Number:"), gbc);

        gbc.gridx = 1;
        accNumberField = new JTextField(15);
        panel.add(accNumberField, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        amountField = new JTextField(15);
        panel.add(amountField, gbc);

        // For transfer, show toAccount field
        if ("TRANSFER".equals(transactionType)) {
            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("To Account:"), gbc);

            gbc.gridx = 1;
            toAccountField = new JTextField(15);
            panel.add(toAccountField, gbc);
        }

        // Buttons
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton executeBtn = new JButton("Execute " + transactionType);
        JButton cancelBtn = new JButton("Cancel");

        executeBtn.addActionListener(e -> executeTransaction());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(executeBtn);
        buttonPanel.add(cancelBtn);
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void executeTransaction() {
        try {
            String accNumber = accNumberField.getText();
            double amount = Double.parseDouble(amountField.getText());

            if (accNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter account number");
                return;
            }

            switch (transactionType) {
                case "DEPOSIT":
                    bankService.deposit(accNumber, amount);
                    JOptionPane.showMessageDialog(this, "Deposit successful!");
                    break;

                case "WITHDRAW":
                    bankService.withdraw(accNumber, amount);
                    JOptionPane.showMessageDialog(this, "Withdrawal successful!");
                    break;

                case "TRANSFER":
                    String toAccount = toAccountField.getText();
                    if (toAccount.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter destination account");
                        return;
                    }
                    bankService.transfer(accNumber, toAccount, amount);
                    JOptionPane.showMessageDialog(this, "Transfer successful!");
                    break;
            }

            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount");
        } catch (InsufficientBalanceException e) {
            JOptionPane.showMessageDialog(this, "Transaction failed: " + e.getMessage());
        } catch (AccountNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}