package BMP.repository;

import BMP.exceptions.*;

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
     * @throws IllegalNameTypeProductException если тип продукта недопустим.
     */
    public static void validateProductType(String productType) {
        if (!productType.matches("DEBIT|CREDIT|INVEST|SAVING")) {
            throw new IllegalNameTypeProductException("Неправильное название типа продукта");
        }
    }

    /**
     * Проверяет, является ли указанный аргумент допустимым (SUM или COUNT).
     *
     * @param sumOrCount Аргумент для проверки.
     * @throws IllegalArgumentForSumAndCountException если аргумент недопустим.
     */
    public static void validateSumOrCount(String sumOrCount) {
        if (!sumOrCount.matches("SUM|COUNT")) {
            throw new IllegalArgumentForSumAndCountException("Неправильный аргумент для суммы или количества");
        }
    }

    /**
     * Проверяет, является ли указанный оператор допустимым.
     *
     * @param operator Оператор для проверки.
     * @throws IncorrectComparisonOperatorException если оператор недопустим.
     */
    public static void validateOperator(String operator) {
        if (!operator.matches(">=|<=|>|<|=")) {
            throw new IncorrectComparisonOperatorException("Неправильный оператор сравнения: " + operator);
        }
    }

    /**
     * Проверяет корректность аргументов для сравнения транзакций.
     *
     * @param args Список аргументов: тип продукта, тип транзакции и значение для сравнения.
     * @throws IllegalNameTypeTransactionException если аргументы некорректны.
     * @throws IllegalNumberFormatException если аргументы некорректны.
     * @throws NotEnoughArgumentsForComparisonException если аргументы некорректны.
     */
    public static void validateComparisonArgs(List<String> args) {
        if (args.size() < 4) {
            throw new NotEnoughArgumentsForComparisonException("Недостаточно аргументов для сравнения");
        }

        validateProductType(args.get(0));

        if (!args.get(1).matches("WITHDRAW|DEPOSIT")) {
            throw new IllegalNameTypeTransactionException("Неправильное название типа транзакции");
        }

        try {
            Integer.parseInt(args.get(3));
        } catch (NumberFormatException e) {
            throw new IllegalNumberFormatException("Некорректный формат числа: " + args.get(3), e);
        }
    }
}

