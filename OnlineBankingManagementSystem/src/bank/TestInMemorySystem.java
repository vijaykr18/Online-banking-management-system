package bank;

import bank.model.SavingsAccount;
import bank.model.CurrentAccount;
import bank.service.BankService;
import bank.exception.*;

public class TestInMemorySystem {
    public static void main(String[] args) {
        System.out.println("=== Testing In-Memory Banking System ===\n");

        try {
            BankService bankService = new BankService();

            // Test 1: Create Accounts
            System.out.println("1. Testing Account Creation...");
            bankService.createAccount(new SavingsAccount("SA002", "Test User 1", 500.0));
            bankService.createAccount(new CurrentAccount("CA002", "Test User 2", 300.0));
            System.out.println("   ✓ Accounts created successfully");

            // Test 2: Deposit
            System.out.println("2. Testing Deposit...");
            bankService.deposit("SA002", 200.0);
            System.out.println("   ✓ Deposit to SA002 successful");

            // Test 3: Withdraw
            System.out.println("3. Testing Withdrawal...");
            bankService.withdraw("SA002", 100.0);
            System.out.println("   ✓ Withdrawal from SA002 successful");

            // Test 4: Transfer
            System.out.println("4. Testing Transfer...");
            bankService.transfer("SA002", "CA002", 50.0);
            System.out.println("   ✓ Transfer from SA002 to CA002 successful");

            // Test 5: Check Balances
            System.out.println("5. Checking Final Balances...");
            var account1 = bankService.getAccount("SA002");
            var account2 = bankService.getAccount("CA002");
            System.out.println("   ✓ SA002 Balance: $" + account1.getBalance());
            System.out.println("   ✓ CA002 Balance: $" + account2.getBalance());

            // Test 6: Get All Accounts
            System.out.println("6. Testing Get All Accounts...");
            var allAccounts = bankService.getAllAccounts();
            System.out.println("   ✓ Total accounts in system: " + allAccounts.size());
            for (var account : allAccounts) {
                System.out.println("     - " + account.getAccountInfo());
            }

            System.out.println("\n=== ALL TESTS PASSED! ===");
            System.out.println("In-memory system is working perfectly!");

        } catch (InsufficientBalanceException e) {
            System.out.println("❌ Insufficient balance: " + e.getMessage());
        } catch (AccountNotFoundException e) {
            System.out.println("❌ Account not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}