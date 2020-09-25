package banking;

import java.awt.*;

public class Luhn_Algorithm {

    public static int validator(String number) {
        int digitsSum = 0;
        int digits [] = parseNumber(number);
        digits = luhnAlgorithm(digits);
        for (int x : digits) {
            digitsSum += x;
        }
        return (digitsSum % 10 != 0 ? 10 - (digitsSum % 10) : 0);
    }

    public static int [] parseNumber(String number) {
        int digits [] = new int [number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = Character.getNumericValue(number.charAt(i));
        }
        return digits;
    }

    public static int [] luhnAlgorithm (int [] digits) {
        int appliedDigits [] = digits;
        for (int i = 0; i < digits.length; i++) {
            if ((i + 1) % 2 != 0) {
                appliedDigits[i] = appliedDigits[i] * 2;
            }
        }

        for (int i = 0; i < digits.length; i++) {
            if (appliedDigits[i] > 9) {
                appliedDigits[i] -= 9;
            }
        }
        return appliedDigits;
    }

    public static boolean verifyNumber(String number) {
        int digits [] = new int [number.length()];
        digits = parseNumber(number);
        digits = luhnAlgorithm(digits);
        int sum = 0;
        for (int i : digits) {
            sum += i;
        }
        if (sum % 10 == 0) {
            return true;
        }
        return false;
    }
}
