package bank.gui;

import bank.service.BankService;
import bank.model.SavingsAccount;
import bank.model.CurrentAccount;
import bank.exception.AccountNotFoundException;
import bank.exception.InsufficientBalanceException;

import javax.swing.*;
import java.awt.*;

public class AccountFrame extends JFrame {
    private BankService bankService;
    private JTextField accNumberField, holderField, balanceField;
    private JComboBox<String> typeComboBox;

    public AccountFrame(BankService bankService) {
        this.bankService = bankService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Create New Account");
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

        // Account Holder
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Account Holder:"), gbc);

        gbc.gridx = 1;
        holderField = new JTextField(15);
        panel.add(holderField, gbc);

        // Initial Balance
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Initial Balance:"), gbc);

        gbc.gridx = 1;
        balanceField = new JTextField(15);
        panel.add(balanceField, gbc);

        // Account Type
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Account Type:"), gbc);

        gbc.gridx = 1;
        typeComboBox = new JComboBox<>(new String[]{"Savings", "Current"});
        panel.add(typeComboBox, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton createBtn = new JButton("Create Account");
        JButton cancelBtn = new JButton("Cancel");

        createBtn.addActionListener(e -> createAccount());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void createAccount() {
        try {
            String accNumber = accNumberField.getText();
            String holder = holderField.getText();
            double balance = Double.parseDouble(balanceField.getText());
            String type = (String) typeComboBox.getSelectedItem();

            if (accNumber.isEmpty() || holder.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            if ("Savings".equals(type)) {
                bankService.createAccount(new SavingsAccount(accNumber, holder, balance));
            } else {
                bankService.createAccount(new CurrentAccount(accNumber, holder, balance));
            }

            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid balance");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating account: " + e.getMessage());
        }
    }
}