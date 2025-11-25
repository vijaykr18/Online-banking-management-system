package bank.util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9]{4,10}$");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    public static boolean isValidAmount(String amount) {
        if (amount == null) return false;
        try {
            double value = Double.parseDouble(amount);
            return value > 0 && AMOUNT_PATTERN.matcher(amount).matches();
        } catch (NumberFormatException e) {
            return false;
        }
    }
}