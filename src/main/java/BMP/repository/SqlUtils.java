package BMP.repository;

import java.util.List;

/**
 * Утилитный класс для валидации входных параметров в различных методах.
 * <p>
 * Этот класс содержит статические методы для проверки корректности типов продуктов,
 * аргументов суммы или количества, операторов сравнения и аргументов для сравнения транзакций.
 * </p>
 */
public class SqlUtils {

    /**
     * Проверяет, является ли указанный тип продукта допустимым.
     *
     * @param productType Тип продукта для проверки.
     * @throws IllegalArgumentException если тип продукта недопустим.
     */
    public static void validateProductType(String productType) {
        if (!productType.matches("DEBIT|CREDIT|INVEST|SAVING")) {
            throw new IllegalArgumentException("Неправильное название типа продукта");
        }
    }

    /**
     * Проверяет, является ли указанный аргумент допустимым (SUM или COUNT).
     *
     * @param sumOrCount Аргумент для проверки.
     * @throws IllegalArgumentException если аргумент недопустим.
     */
    public static void validateSumOrCount(String sumOrCount) {
        if (!sumOrCount.matches("SUM|COUNT")) {
            throw new IllegalArgumentException("Неправильный аргумент для суммы или количества");
        }
    }

    /**
     * Проверяет, является ли указанный оператор допустимым.
     *
     * @param operator Оператор для проверки.
     * @throws IllegalArgumentException если оператор недопустим.
     */
    public static void validateOperator(String operator) {
        if (!operator.matches(">=|<=|>|<|=")) {
            throw new IllegalArgumentException("Неправильный оператор сравнения: " + operator);
        }
    }

    /**
     * Проверяет корректность аргументов для сравнения транзакций.
     *
     * @param args Список аргументов: тип продукта, тип транзакции и значение для сравнения.
     * @throws IllegalArgumentException если аргументы некорректны.
     */
    public static void validateComparisonArgs(List<String> args) {
        if (args.size() < 4) {
            throw new IllegalArgumentException("Недостаточно аргументов для сравнения");
        }

        validateProductType(args.get(0));

        if (!args.get(1).matches("WITHDRAW|DEPOSIT")) {
            throw new IllegalArgumentException("Неправильное название типа транзакции");
        }

        try {
            Integer.parseInt(args.get(3));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа: " + args.get(3), e);
        }
    }
}

