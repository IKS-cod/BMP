package BMP.service;

import BMP.model.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

/**
 * Сервис для получения информации о сервисе.
 * <p>
 * Этот класс предоставляет методы для извлечения информации о текущем сервисе, включая его имя и версию,
 * используя свойства сборки, предоставляемые {@link BuildProperties}.
 * </p>
 */
@Component
public class InfoService {

    @Autowired
    private BuildProperties buildProperties;

    /**
     * Получает информацию о сервисе.
     *
     * @return Объект {@link ServiceInfo}, содержащий имя и версию сервиса.
     */
    public ServiceInfo getInfo() {
        return new ServiceInfo(buildProperties.getName(), buildProperties.getVersion());
    }
}
