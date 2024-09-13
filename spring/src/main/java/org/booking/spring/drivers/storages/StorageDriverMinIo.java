package org.booking.spring.drivers.storages;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class StorageDriverMinIo implements StorageDriverInterface {

    private final MinioClient minioClient;
    private final String minioUrl; // Додаємо поле для збереження URL MinIO

    public StorageDriverMinIo(String minioUrl, String minioUsername, String minioPassword) {
        this.minioUrl = minioUrl; // Зберігаємо URL MinIO
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername, minioPassword)
                .build();

        System.out.println("Storage MinioClient Created");
    }

    private void bucketExists(String bucketName) throws Exception {
        boolean found = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            setPublicPolicy(bucketName); // Встановлюємо публічну політику після створення ведра
        } else {
            setPublicPolicy(bucketName); // Перевіряємо та встановлюємо політику, якщо вже існує
        }
    }

    /**
     * Встановлення публічної політики для відра
     */
    private void setPublicPolicy(String bucketName) throws Exception {
        String policy = "{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": \"*\",\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetObject\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(policy)
                        .build());
    }

    /**
     * Генерація прямого URL до об'єкта
     */
    public String getDirectUrl(String bucketName, String fileName) {
        // Формуємо прямий URL без підписаних параметрів
        return minioUrl + "/" + bucketName + "/" + fileName;
    }

    @Override
    public String put(String bucketName, String fileName, MultipartFile file) {
        try {
            bucketExists(bucketName);
            ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
            String contentType = file.getContentType();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(bais, bais.available(), -1)
                            .contentType(contentType)
                            .build());
            bais.close();

            // Повертаємо простий URL до файлу
            return getDirectUrl(bucketName, fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null; // Помилка завантаження файлу
        }
    }
}

