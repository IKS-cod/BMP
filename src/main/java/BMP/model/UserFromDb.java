package BMP.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс UserFromDb представляет пользователя, извлеченного из базы данных.
 */
public class UserFromDb {

    /**
     * Уникальный идентификатор пользователя.
     */
    private UUID id;

    /**
     * Имя пользователя.
     */
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    private String lastName;

    /**
     * Конструктор для создания экземпляра UserFromDb.
     *
     * @param id Уникальный идентификатор пользователя.
     * @param firstName Имя пользователя.
     * @param lastName Фамилия пользователя.
     */
    public UserFromDb(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserFromDb() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserFromDb{" +
                "uuid=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFromDb that = (UserFromDb) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

}
