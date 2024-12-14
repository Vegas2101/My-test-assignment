import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

class Calculator {

    private static final Map<String, Integer> romanToArabic = new LinkedHashMap<>();
    private static final Map<Integer, String> arabicToRoman = new LinkedHashMap<>();


    private static final String[] ROMAN_NUMERALS = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final int[] ARABIC_VALUES = {100, 90, 50, 40, 10, 9, 5, 4, 1};


    static {

        for (int i = 0; i <= 100; i++) {
            String roman = convertToRoman(i);
            romanToArabic.put(roman, i);
            arabicToRoman.put(i, roman);
        }
    }

    private static String convertToRoman(int value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ARABIC_VALUES.length; i++) {
            while (value >= ARABIC_VALUES[i]) {
                result.append(ROMAN_NUMERALS[i]);
                value -= ARABIC_VALUES[i];
            }
        }
        return result.toString();
    }

    public static String calc(String input) {

        String[] parts = input.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный ввод. Формат: 'число1 операция число2'.");
        }

        int num1 = parseOperand(parts[0]);
        int num2 = parseOperand(parts[2]);
        String operator = parts[1];


        int result = performOperation(num1, num2, operator);

        if (romanToArabic.containsKey(parts[0].toUpperCase())) {
            if (result > 0) {
                return convertToRoman(result);
            } else {
                return "Ошибка в выражении. Введите корректное выражение с римскими цифрами";
            }
        }

        return String.valueOf(result);
    }

    private static int parseOperand(String operand) {
        if (romanToArabic.containsKey(operand.toUpperCase())) {
            return romanToArabic.get(operand.toUpperCase());
        }
        try {
            int value = Integer.parseInt(operand);
            if (value < 0 || value > 10) {
                throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 10.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа: " + operand);
        }
    }

    private static int performOperation(int num1, int num2, String operator) {

        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на 0 запрещено.");
                }
                yield num1 / num2;
            }

            default -> throw new IllegalArgumentException("Неверный оператор. Используйте: +, -, *, /");
        };
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