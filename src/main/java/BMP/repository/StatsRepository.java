package BMP.repository;

import BMP.model.Stats;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/**
 * Репозиторий для работы с сущностью Stats.
 * Предоставляет методы для выполнения операций вставки и обновления статистических записей.
 */
@Repository
public interface StatsRepository extends JpaRepository<Stats, UUID> {

    /**
     * Вставляет новую запись в таблицу stats с указанным идентификатором правила и начальным значением количества.
     *
     * @param uuid уникальный идентификатор правила
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO stats (rule_id, count) VALUES (:uuid, 1)", nativeQuery = true)
    void insertCount(@Param("uuid") UUID uuid);

    /**
     * Увеличивает счетчик для записи в таблице stats с указанным идентификатором правила.
     *
     * @param uuid уникальный идентификатор правила
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE stats SET count = count + 1 WHERE rule_id = :uuid", nativeQuery = true)
    void incrementCount(@Param("uuid") UUID uuid);
}
