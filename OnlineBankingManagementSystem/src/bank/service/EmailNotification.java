package bank.service;

public class EmailNotification {
    public void sendTransactionNotification(String email, String message) {
        // Simulate email sending
        new Thread(() -> {
            try {
                Thread.sleep(500); // Simulate network delay
                System.out.println("Email sent to " + email + ": " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}