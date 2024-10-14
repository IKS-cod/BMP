package BMP.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Репозиторий для выполнения запросов в БД
 * @autor Гуров Дмитрий
 */
@Repository
public class RecommendationsRepository {
    private JdbcTemplate jdbcTemplate;
    private Map<String, Boolean> cacheUserOfAndActiveUserOf = new HashMap<>();
    private Map<String, Boolean> cacheTransactionSumCompare = new HashMap<>();
    private Map<String, Boolean> cacheTransactionSumCompareDepositWithdraw = new HashMap<>();

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int checkRuleInBaseUserNotUseProductType(UUID user, String productType) {

        Integer result = jdbcTemplate.queryForObject(
                "SELECT \n" +
                        "    SUM(AMOUNT)\n" +
                        "FROM \n" +
                        "    PRODUCTS p\n" +
                        "JOIN \n" +
                        "    TRANSACTIONS t ON t.PRODUCT_ID = p.ID\n" +
                        "WHERE \n" +
                        "    USER_ID = ?\n" +
                        "    AND p.\"TYPE\" != ?",
                Integer.class, user, productType);
        return result != null ? result : 0;
    }

    public int checkRuleInBaseSumTransactionForProductType(UUID user,String productType, String transactionType) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT \n" +
                        "    SUM(AMOUNT)\n" +
                        "FROM \n" +
                        "    PRODUCTS p\n" +
                        "JOIN \n" +
                        "    TRANSACTIONS t ON t.PRODUCT_ID = p.ID\n" +
                        "WHERE \n" +
                        "    USER_ID = ?\n" +
                        "    AND p.\"TYPE\" = ?\n" +
                        "    AND t.\"TYPE\" = ?",
                Integer.class, user, productType, transactionType);
        return result != null ? result : 0;
    }

    public boolean checkUserOfAndActiveUserOf(UUID user, String productType, String sumOrCount) {

        // Проверка типа продукта
        if (!productType.matches("DEBIT|CREDIT|INVEST|SAVING")) {
            throw new IllegalArgumentException("Неправильное название типа продукта");
        }

        // Проверка аргумента sumOrCount
        if (!sumOrCount.matches("SUM|COUNT")) {
            throw new IllegalArgumentException("Неправильный аргумент для суммы или количества");
        }
        int index = 0;
        if (sumOrCount.equals("COUNT")) {
            index = 5;
        }
        String key = user.toString()+productType+sumOrCount;

        if (cacheUserOfAndActiveUserOf.containsKey(key)) {
                return cacheUserOfAndActiveUserOf.get(key);
        }

        String sql = String.format("SELECT CASE WHEN %s(AMOUNT) > ? " +
                "THEN TRUE ELSE FALSE END AS is_amount\n" +
                "FROM PRODUCTS p\n" +
                "JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID\n" +
                "WHERE USER_ID = ? AND p.TYPE=?",sumOrCount);

        // Выполнение запроса и получение результата
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, index, user, productType);

        cacheUserOfAndActiveUserOf.put(key, result);

        return result;
    }


    public boolean checkTransactionSumCompare(UUID user, List<String> arg) {
        // arg.get(3) - это значение для суммы,
        // arg.get(2) - это оператор (например, ">", "<", "=" и т.д.)

        String operator = arg.get(2); // Получаем оператор
        if (!operator.matches(">=|<=|>|<|=")) {
            throw new IllegalArgumentException("Неправильный оператор сравнения: " + operator);
        }

        if (!arg.get(0).matches("DEBIT|CREDIT|INVEST|SAVING")) {
            throw new IllegalArgumentException("Неправильное название типа продукта");
        }

        if (!arg.get(1).matches("WITHDRAW|DEPOSIT")) {
            throw new IllegalArgumentException("Неправильное название типа транзакции");
        }

        try {
            int number = Integer.parseInt(arg.get(3));
        } catch (NumberFormatException e) {
            // Обработка ошибки
            System.out.println("Ошибка: некорректный формат числа: " + arg.get(3));

        }

        String key = user.toString()+arg;

        if (cacheTransactionSumCompare.containsKey(key)) {
            return cacheTransactionSumCompare.get(key);
        }

        String sql = String.format(
                "SELECT CASE WHEN SUM(AMOUNT) %s ? THEN TRUE ELSE FALSE END AS is_amount " +
                        "FROM PRODUCTS p " +
                        "JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                        "WHERE USER_ID = ? AND p.\"TYPE\"=? AND t.\"TYPE\"=?",
                operator
        );

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                arg.get(3),  // Значение для сравнения
                user,        // USER_ID
                arg.get(0),  // Тип продукта
                arg.get(1)   // Тип транзакции
        );

        cacheTransactionSumCompare.put(key, result);

        return result != null ? result : false;
    }

    public boolean checkTransactionSumCompareDepositWithdraw(UUID user, List<String> arg) {

        String operator = arg.get(1);
        if (!operator.matches(">=|<=|>|<|=")) {
            throw new IllegalArgumentException("Неправильный оператор сравнения: " + operator);
        }

        if (!arg.get(0).matches("DEBIT|CREDIT|INVEST|SAVING")) {
            throw new IllegalArgumentException("Неправильное название типа продукта");
        }

        String key = user.toString()+arg;

        if (cacheTransactionSumCompareDepositWithdraw.containsKey(key)) {
            return cacheTransactionSumCompareDepositWithdraw.get(key);
        }

        String sql = String.format(
                "SELECT \n" +
                        "    CASE \n" +
                        "        WHEN (SELECT SUM(t.AMOUNT) \n" +
                        "              FROM PRODUCTS p \n" +
                        "              JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID \n" +
                        "              WHERE t.TYPE = 'DEPOSIT' \n" +
                        "                AND USER_ID = ? \n" +
                        "                AND p.\"TYPE\" = ?) %s \n" +
                        "             (SELECT SUM(t.AMOUNT) \n" +
                        "              FROM PRODUCTS p \n" +
                        "              JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID \n" +
                        "              WHERE t.TYPE = 'WITHDRAW' \n" +
                        "                AND USER_ID = ? \n" +
                        "                AND p.\"TYPE\" = ?) \n" +
                        "        THEN TRUE \n" +
                        "        ELSE FALSE \n" +
                        "    END AS is_deposit_greater;",
                operator
        );

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                user,
                arg.get(0),
                user,
                arg.get(0)
        );

        cacheTransactionSumCompareDepositWithdraw.put(key, result);

        return result != null ? result : false;
    }




}
