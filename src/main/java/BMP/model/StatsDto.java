package BMP.model;

import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) для передачи статистических данных.
 * Содержит список объектов Stats.
 */
public class StatsDto {

    private List<Stats> stats; // Список статистических записей

    /**
     * Конструктор для создания нового объекта StatsDto с указанным списком статистических записей.
     *
     * @param stats список статистических записей
     */
    public StatsDto(List<Stats> stats) {
        this.stats = stats;
    }

    /**
     * Пустой конструктор для создания объекта StatsDto.
     */
    public StatsDto() {
    }

    /**
     * Возвращает список статистических записей.
     *
     * @return список объектов Stats
     */
    public List<Stats> getStats() {
        return stats;
    }

    /**
     * Устанавливает список статистических записей.
     *
     * @param stats список статистических записей для установки
     */
    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsDto statsDto = (StatsDto) o;
        return Objects.equals(stats, statsDto.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stats);
    }

    @Override
    public String toString() {
        return "StatsDto{" +
                "stats=" + stats +
                '}';
    }
}
