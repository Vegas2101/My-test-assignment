import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Calculator {
    private static final Map<String, Integer> romanToArabic = new HashMap<>();
    private static final Map<Integer, String> arabicToRoman = new HashMap<>();

    static {
        romanToArabic.put("0", 0);
        romanToArabic.put("I", 1);
        romanToArabic.put("II", 2);
        romanToArabic.put("III", 3);
        romanToArabic.put("IV", 4);
        romanToArabic.put("V", 5);
        romanToArabic.put("VI", 6);
        romanToArabic.put("VII", 7);
        romanToArabic.put("VIII", 8);
        romanToArabic.put("IX", 9);
        romanToArabic.put("X", 10);

        arabicToRoman.put(0, "0");
        arabicToRoman.put(1, "I");
        arabicToRoman.put(2, "II");
        arabicToRoman.put(3, "III");
        arabicToRoman.put(4, "IV");
        arabicToRoman.put(5, "V");
        arabicToRoman.put(6, "VI");
        arabicToRoman.put(7, "VII");
        arabicToRoman.put(8, "VIII");
        arabicToRoman.put(9, "IX");
        arabicToRoman.put(10, "X");
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный ввод. Формат: 'число1 операция число2'");
        }

        validateOperandType(parts[0], parts[2]);

        int num1 = convertOperand(parts[0]);
        int num2 = convertOperand(parts[2]);

        int result = performOperation(num1, num2, parts[1]);

        if (isRoman(parts[0])) {
            if (result < 1) {
                throw new IllegalArgumentException("Результат для римских чисел должен быть положительным.");
            }
            return arabicToRoman.get(result);
        } else {
            return String.valueOf(result);
        }    }

    private static void validateOperandType(String firstOperand, String secondOperand) {
        boolean isRoman = isRoman(firstOperand) && isRoman(secondOperand);
        boolean isArabic = !isRoman && isArabic(firstOperand) && isArabic(secondOperand);
        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Оба числа должны быть либо арабскими, либо римскими.");
        }
    }
    private static int convertOperand(String operand) {
        if (romanToArabic.containsKey(operand)) {
            return romanToArabic.get(operand);
        }
        try {
            int arabicNumber = Integer.parseInt(operand);
            if (arabicNumber < 1 || arabicNumber > 10) {
                throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10.");
            }
            return arabicNumber;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка ввода числа: " + operand);
        }
    }

    private static int performOperation(int num1, int num2, String operator) {
        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) throw new ArithmeticException("Деление на 0 запрещено.");
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Неверный оператор. Поддерживаемые операторы: +, -, *, /");
        };
    }

    private static boolean isRoman(String number) {
        return romanToArabic.containsKey(number);
    }

    private static boolean isArabic(String number) {
        try {
            int arabicNumber = Integer.parseInt(number);
            return arabicNumber >= 0 && arabicNumber <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, '1 + 2' или 'I + II'):");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}