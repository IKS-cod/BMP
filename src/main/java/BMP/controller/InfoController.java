package BMP.controller;

import BMP.model.ServiceInfo;
import BMP.service.InfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления информацией о сервисе.
 * Обрабатывает HTTP-запросы и предоставляет информацию о сервисе.
 */
@RestController
public class InfoController {
    private final InfoService infoService;

    /**
     * Конструктор контроллера.
     *
     * @param infoService Сервис для получения информации о сервисе.
     */
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    /**
     * Обрабатывает GET-запрос на путь "/management/info".
     *
     * @return объект {@link ServiceInfo}, содержащий информацию о сервисе.
     */
    @GetMapping("/management/info")
    public ServiceInfo getInfo() {
        return infoService.getInfo();
    }
}
