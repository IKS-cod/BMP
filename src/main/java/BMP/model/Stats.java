package BMP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

/**
 * Представляет статистическую запись, связанную с конкретным правилом.
 * Эта сущность отображается на таблицу "stats" в базе данных.
 */
@Entity
@Table(name = "stats")
public class Stats {

    @Id
    private UUID ruleId; // Уникальный идентификатор правила

    private Integer count; // Количество событий, связанных с правилом

    /**
     * Конструктор для создания нового объекта Stats с указанным идентификатором правила и количеством.
     *
     * @param ruleId уникальный идентификатор для правила
     * @param count  начальное значение количества
     */
    public Stats(UUID ruleId, Integer count) {
        this.ruleId = ruleId;
        this.count = count;
    }

    /**
     * Пустой конструктор для JPA.
     */
    public Stats() {
    }

    /**
     * Возвращает уникальный идентификатор для правила.
     *
     * @return идентификатор правила
     */
    public UUID getRuleId() {
        return ruleId;
    }

    /**
     * Устанавливает уникальный идентификатор для правила.
     *
     * @param ruleId уникальный идентификатор для установки
     */
    public void setRuleId(UUID ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * Возвращает текущее количество событий, связанных с правилом.
     *
     * @return текущее количество
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Устанавливает текущее количество событий, связанных с правилом.
     *
     * @param count значение количества для установки
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Сравнивает этот объект Stats с другим объектом на равенство.
     *
     * @param o объект для сравнения
     * @return true, если этот объект равен указанному объекту; false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return Objects.equals(ruleId, stats.ruleId) && Objects.equals(count, stats.count);
    }

    /**
     * Возвращает хеш-код для этого объекта Stats.
     *
     * @return хеш-код для этого объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(ruleId, count);
    }

    /**
     * Возвращает строковое представление этого объекта Stats.
     *
     * @return строковое представление этого объекта
     */
    @Override
    public String toString() {
        return "Stats{" +
                "ruleId=" + ruleId +
                ", count=" + count +
                '}';
    }

}
