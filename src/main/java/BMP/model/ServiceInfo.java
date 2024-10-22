package BMP.model;

/**
 * Класс ServiceInfo представляет информацию о сервисе, включая его имя и версию.
 */
public class ServiceInfo {

    /**
     * Имя сервиса.
     */
    private String name;

    /**
     * Версия сервиса.
     */
    private String version;

    /**
     * Конструктор для создания экземпляра ServiceInfo.
     *
     * @param name    Имя сервиса.
     * @param version Версия сервиса.
     */
    public ServiceInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
