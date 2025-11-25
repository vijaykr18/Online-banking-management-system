package bank.service;

import bank.model.Transaction;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TransactionProcessor implements Runnable {
    private BlockingQueue<Transaction> transactionQueue;
    private BankService bankService;
    private volatile boolean running;

    public TransactionProcessor(BankService bankService) {
        this.transactionQueue = new LinkedBlockingQueue<>();
        this.bankService = bankService;
        this.running = true;
    }

    public void addTransaction(Transaction transaction) {
        transactionQueue.offer(transaction);
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running || !transactionQueue.isEmpty()) {
            try {
                Transaction transaction = transactionQueue.take();
                processTransaction(transaction);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error processing transaction: " + e.getMessage());
            }
        }
    }

    private void processTransaction(Transaction transaction) {
        // Simulate transaction processing
        System.out.println("Processing transaction: " + transaction);
        try {
            Thread.sleep(100); // Simulate processing time
            System.out.println("Transaction processed: " + transaction.getTransactionId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}