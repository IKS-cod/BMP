package BMP.repository;

import BMP.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Product}.
 * <p>
 * Этот интерфейс предоставляет методы для выполнения стандартных операций CRUD (создание, чтение,
 * обновление и удаление) над продуктами в базе данных. Он наследует функциональность от
 * {@link JpaRepository}, что позволяет использовать встроенные методы без необходимости их
 * ручной реализации.
 * </p>
 */
@Repository
public interface RulesRecommendationsRepository extends JpaRepository<Product, Long> {

}
