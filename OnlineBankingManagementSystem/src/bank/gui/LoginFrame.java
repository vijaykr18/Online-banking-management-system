package bank.gui;

import bank.dao.UserDAO;
import bank.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Banking System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Online Banking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Login Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(e -> login());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        formPanel.add(buttonPanel, gbc);

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Add credentials hint
        JLabel hintLabel = new JLabel("Try: john/password123 or jane/password123", JLabel.CENTER);
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        mainPanel.add(hintLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add Enter key listener for login
        getRootPane().setDefaultButton(loginButton);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.authenticateUser(username, password);

            if (user != null) {
                // Login successful
                JOptionPane.showMessageDialog(this,
                        "Login successful! Welcome " + user.getFullName(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Open dashboard and close login
                new DashboardFrame(user).setVisible(true);
                dispose();
            } else {
                // Login failed
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);

                // Clear password field
                passwordField.setText("");
                usernameField.requestFocus();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "System error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Test the login frame independently
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}