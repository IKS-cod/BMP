package BMP.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для выполнения запросов в БД
 * @autor Гуров Дмитрий
 */
@Repository
public class RecommendationsRepository {
    private JdbcTemplate jdbcTemplate;

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
}
