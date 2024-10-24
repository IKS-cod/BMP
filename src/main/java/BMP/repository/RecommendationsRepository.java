package BMP.repository;

import BMP.model.UserFromDb;
import BMP.service.RecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Репозиторий для выполнения запросов в БД.
 * <p>
 * Этот класс предоставляет методы для проверки различных правил и получения информации о пользователях
 * из базы данных. Он использует {@link JdbcTemplate} для выполнения SQL-запросов.
 * </p>
 *
 * @autor Гуров Дмитрий
 */
@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Map<String, Boolean> cacheUserOfAndActiveUserOf = new HashMap<>();
    private final Map<String, Boolean> cacheTransactionSumCompare = new HashMap<>();
    private final Map<String, Boolean> cacheTransactionSumCompareDepositWithdraw = new HashMap<>();

    /**
     * Конструктор для создания экземпляра RecommendationsRepository.
     *
     * @param jdbcTemplate JdbcTemplate для выполнения SQL-запросов.
     */
    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Проверяет, использовал ли пользователь определенный тип продукта.
     *
     * @param user        UUID пользователя.
     * @param productType Тип продукта для проверки.
     * @return Сумма транзакций по продуктам, отличным от указанного типа.
     */
    public int checkRuleInBaseUserNotUseProductType(UUID user, String productType) {
        return getSumOfTransactions(user, productType, false);
    }

    /**
     * Проверяет сумму транзакций для указанного типа продукта и транзакции.
     *
     * @param user            UUID пользователя.
     * @param productType     Тип продукта для проверки.
     * @param transactionType Тип транзакции для проверки.
     * @return Сумма транзакций для указанного типа продукта и транзакции.
     */
    public int checkRuleInBaseSumTransactionForProductType(UUID user, String productType, String transactionType) {
        return getSumOfTransactions(user, productType, true, transactionType);
    }

    /**
     * Получает сумму транзакций по указанному пользователю и типу продукта.
     *
     * @param user        UUID пользователя.
     * @param productType Тип продукта.
     * @param isEqual     Флаг для определения оператора сравнения (равно или не равно).
     * @param transactionType Дополнительные параметры транзакции (если необходимо).
     * @return Сумма транзакций по указанным критериям.
     */
    private int getSumOfTransactions(UUID user, String productType, boolean isEqual, String... transactionType) {
        String operator = isEqual ? "=" : "!=";
        String sql = "SELECT SUM(AMOUNT) FROM PRODUCTS p " +
                "JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                "WHERE USER_ID = ? AND p.TYPE " + operator + "?";

        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, user, productType);
        return result != null ? result : 0;
    }

    /**
     * Проверяет, является ли сумма или количество транзакций пользователя по определенному типу продукта
     * больше заданного значения.
     *
     * @param user        UUID пользователя.
     * @param productType Тип продукта.
     * @param sumOrCount  Параметр, указывающий на проверку суммы или количества (SUM/COUNT).
     * @return true, если условие выполняется; иначе false.
     */
    public boolean checkUserOfAndActiveUserOf(UUID user, String productType, String sumOrCount) {
        SqlUtils.validateProductType(productType);
        SqlUtils.validateSumOrCount(sumOrCount);

        int index = sumOrCount.equals("COUNT") ? 5 : 0; // Установка индекса в зависимости от типа проверки
        String key = user.toString() + productType + sumOrCount;

        if (cacheUserOfAndActiveUserOf.containsKey(key)) {
            return cacheUserOfAndActiveUserOf.get(key);
        }

        String sql = String.format("SELECT CASE WHEN %s(AMOUNT) > ? THEN TRUE ELSE FALSE END AS is_amount " +
                "FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID WHERE USER_ID = ? AND p.TYPE=?", sumOrCount);

        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, index, user, productType);
        cacheUserOfAndActiveUserOf.put(key, result);

        return result != null && result;
    }

    /**
     * Сравнивает сумму транзакций пользователя с заданным значением по определенному типу продукта и транзакции.
     *
     * @param user UUID пользователя.
     * @param arg  Список аргументов: тип продукта, тип транзакции и оператор сравнения.
     * @return true, если сумма удовлетворяет условию; иначе false.
     */
    public boolean checkTransactionSumCompare(UUID user, List<String> arg) {
        SqlUtils.validateComparisonArgs(arg);

        String operator = arg.get(2); // Получаем оператор
        SqlUtils.validateOperator(operator);

        String key = user.toString() + arg;

        if (cacheTransactionSumCompare.containsKey(key)) {
            return cacheTransactionSumCompare.get(key);
        }

        String sql = String.format(
                "SELECT CASE WHEN SUM(AMOUNT) %s ? THEN TRUE ELSE FALSE END AS is_amount " +
                        "FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                        "WHERE USER_ID = ? AND p.TYPE=? AND t.TYPE=?",
                operator
        );

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                arg.get(3),  // Значение для сравнения
                user,         // USER_ID
                arg.get(0),   // Тип продукта
                arg.get(1)    // Тип транзакции
        );

        cacheTransactionSumCompare.put(key, result);

        return result != null && result;
    }

    /**
     * Сравнивает сумму депозитов и снятий средств пользователя по заданному типу продукта.
     *
     * @param user UUID пользователя.
     * @param arg  Список аргументов: тип продукта и оператор сравнения.
     * @return true, если сумма депозитов больше или меньше суммы снятий в зависимости от оператора; иначе false.
     */
    public boolean checkTransactionSumCompareDepositWithdraw(UUID user, List<String> arg) {
        SqlUtils.validateProductType(arg.get(0));

        String operator = arg.get(1);
        SqlUtils.validateOperator(operator);

        String key = user.toString() + arg;

        if (cacheTransactionSumCompareDepositWithdraw.containsKey(key)) {
            return cacheTransactionSumCompareDepositWithdraw.get(key);
        }

        String sql = String.format(
                "SELECT CASE WHEN (SELECT SUM(t.AMOUNT) FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.TYPE = 'DEPOSIT' AND USER_ID = ? AND p.TYPE = ?) %s " +
                        "(SELECT SUM(t.AMOUNT) FROM PRODUCTS p JOIN TRANSACTIONS t ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.TYPE = 'WITHDRAW' AND USER_ID = ? AND p.TYPE = ?) THEN TRUE ELSE FALSE END AS is_deposit_greater;",
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

        return result != null && result;
    }

    /**
     * Получает информацию о пользователе по имени пользователя.
     *
     * @param userName Имя пользователя для поиска.
     * @return Объект UserFromDb с информацией о пользователе или null, если пользователь не найден.
     */
    public UserFromDb getIdUser(String userName) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT ID, FIRST_NAME, LAST_NAME FROM USERS u WHERE u.USERNAME = ?",
                    new Object[]{userName},
                    new BeanPropertyRowMapper<>(UserFromDb.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // Обработка случая отсутствия пользователя
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка доступа к базе данных", e);
        }
    }

    /**
     * Очищает кэши, используемые в репозитории.
     */
    public void clearCaches() {
        cacheUserOfAndActiveUserOf.clear();
        cacheTransactionSumCompare.clear();
        cacheTransactionSumCompareDepositWithdraw.clear();
    }


}
