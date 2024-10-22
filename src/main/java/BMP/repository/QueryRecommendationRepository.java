package BMP.repository;

import BMP.model.QueryRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс QueryRecommendationRepository предоставляет методы для работы с сущностью
 * {@link QueryRecommendation} в базе данных.
 * <p>
 * Этот интерфейс наследует методы из {@link JpaRepository}, что позволяет выполнять
 * стандартные операции CRUD (создание, чтение, обновление, удаление) без необходимости
 * реализации их вручную.
 * </p>
 */
@Repository
public interface QueryRecommendationRepository extends JpaRepository<QueryRecommendation, Long> {

}
