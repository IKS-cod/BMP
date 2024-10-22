package BMP.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
/**
 * Конфигурация источника данных для работы с базой данных рекомендаций.
 * <p>
 * Этот класс отвечает за создание и настройку пула соединений HikariCP,
 * а также предоставляет экземпляры JdbcTemplate для выполнения SQL-запросов.
 * </p>
 *
 * @author Невзорова Екатерина
 */
@Configuration
public class RecommendationsDataSourceConfiguration {

    /**
     * Создает и настраивает источник данных для базы данных рекомендаций.
     *
     * @param recommendationsUrl URL базы данных, извлекаемый из конфигурации приложения.
     * @return настроенный объект DataSource.
     */
    @Bean(name = "recommendationsDataSource")
    public DataSource recommendationsDataSource(@Value("${application.recommendations-db.url}")
                                                String recommendationsUrl) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    /**
     * Создает JdbcTemplate для работы с базой данных рекомендаций.
     *
     * @param dataSource источник данных, который будет использоваться JdbcTemplate.
     * @return экземпляр JdbcTemplate, настроенный для работы с указанным источником данных.
     */
    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate(
            @Qualifier("recommendationsDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Создает основной источник данных для приложения.
     *
     * @param properties свойства источника данных, используемые для настройки.
     * @return настроенный объект DataSource, который будет использоваться по умолчанию.
     */
    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}
