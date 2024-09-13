package org.booking.spring.services;

import jakarta.annotation.PostConstruct;
import org.booking.spring.config.MinioConfig;
import org.booking.spring.drivers.storages.DriverEnum;
import org.booking.spring.drivers.storages.StorageDriverInterface;
import org.booking.spring.drivers.storages.StorageDriverMinIo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class StorageService {

    private final MinioConfig minioConfig;
    private StorageDriverInterface driver;
    private Map<DriverEnum, StorageDriverInterface> drivers;

    // Інжектуємо MinioConfig через конструктор
    @Autowired
    public StorageService(MinioConfig minioConfig) {
        this.minioConfig = minioConfig;
        System.out.println("Storage Service Created");
    }

    // Ініціалізація драйверів після створення сервісу
    @PostConstruct
    private void init() {
        // Ініціалізуємо драйвери, використовуючи значення з MinioConfig
        drivers = new HashMap<>();
        drivers.put(DriverEnum.MinIo, new StorageDriverMinIo(
                minioConfig.getMinioUrl(),
                minioConfig.getMinioUsername(),
                minioConfig.getMinioPassword()
        ));

        // Встановлюємо драйвер за замовчуванням
        driver = drivers.get(DriverEnum.MinIo);
    }

    /**
     * Отримати екземпляр драйвера для роботи з ним напряму
     * @param disk тип драйвера
     * @return екземпляр драйвера
     */
    public StorageDriverInterface disk(DriverEnum disk) {
        return drivers.get(disk);
    }

    /**
     * Отримати екземпляр драйвера за замовчуванням
     * @return екземпляр драйвера
     */
    public StorageDriverInterface disk() {
        return driver;
    }

    /**
     * Обгортка для драйвера за замовчуванням
     * @param bucketName ім'я бакету
     * @param fileName ім'я файлу
     * @param file файл для завантаження
     * @return посилання на файл
     */
    public String put(String bucketName, String fileName, MultipartFile file) {
        return driver.put(bucketName, fileName, file);
    }
}