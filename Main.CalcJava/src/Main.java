import java.util.Scanner;

class Main {

    // Arrays for mapping Roman numerals to integers and vice versa
    private static final String[] ROMAN_NUMERALS = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
    private static final int[] ROMAN_VALUES = {1, 4, 5, 9, 10, 40, 50, 90, 100};

    // Method to perform calculation based on input string
    public static String calc(String input) {
        input = input.trim();

        // Split input into parts: operand1, operator, operand2
        String[] parts = input.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input format: expected 'operand operator operand'");
        }

        String operand1 = parts[0].trim();
        String operator = parts[1].trim();
        String operand2 = parts[2].trim();

        int num1, num2;

        if (isNumeric(operand1) && isNumeric(operand2)) {
            // Both operands are Arabic numerals
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Numbers should be between 1 and 10 (inclusive)");
            }
        } else if (isRoman(operand1) && isRoman(operand2)) {
            // Both operands are Roman numerals
            num1 = romanToInteger(operand1);
            num2 = romanToInteger(operand2);
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Numbers should be between 1 and 10 (inclusive)");
            }
        } else {
            throw new IllegalArgumentException("Mixed numeral systems (Arabic and Roman numerals) are not allowed");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: only +, -, *, / are supported");
        }

        // Return result as string based on input numeral type
        if (isNumeric(operand1) && isNumeric(operand2)) {
            return Integer.toString(result);
        } else {
            return integerToRoman(result);
        }
    }

    // Check if a string is a numeric value
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }

    // Check if a string is a valid Roman numeral
    private static boolean isRoman(String str) {
        return str.matches("^[IVXLCDM]+$");
    }

    // Function to convert Roman numeral to integer
    private static int romanToInteger(String roman) {
        int result = 0;
        int i = 0;

        while (i < roman.length()) {
            if (i + 1 < roman.length() && isRomanNumeral(roman.substring(i, i + 2))) {
                result += getValue(roman.substring(i, i + 2));
                i += 2;
            } else {
                result += getValue(roman.substring(i, i + 1));
                i++;
            }
        }

        return result;
    }

    // Check if a string is a valid pair of Roman numeral characters
    private static boolean isRomanNumeral(String s) {
        for (String numeral : ROMAN_NUMERALS) {
            if (numeral.equals(s)) {
                return true;
            }
        }
        return false;
    }

    // Get integer value of a Roman numeral
    private static int getValue(String roman) {
        for (int i = 0; i < ROMAN_NUMERALS.length; i++) {
            if (ROMAN_NUMERALS[i].equals(roman)) {
                return ROMAN_VALUES[i];
            }
        }
        throw new IllegalArgumentException("Invalid Roman numeral: " + roman);
    }

    // Convert integer to Roman numeral
    private static String integerToRoman(int number) {
        if (number < 1 || number > 100) {
            throw new IllegalArgumentException("Number must be between 1 and 100");
        }

        StringBuilder sb = new StringBuilder();
        int i = ROMAN_VALUES.length - 1; // start with the largest numeral
        while (number > 0) {
            if (number >= ROMAN_VALUES[i]) {
                sb.append(ROMAN_NUMERALS[i]);
                number -= ROMAN_VALUES[i];
            } else {
                i--; // move to the next smaller numeral
            }
        }

        return sb.toString();
    }

    // Main method to run the calculator
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter expression ('5 + 5' or 'IV + V'): ");
        String input = scanner.nextLine();
        scanner.close();

        try {
            String result = calc(input);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
