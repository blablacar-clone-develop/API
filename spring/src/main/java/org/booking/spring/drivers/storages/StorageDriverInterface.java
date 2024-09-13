package org.booking.spring.drivers.storages;

import org.springframework.web.multipart.MultipartFile;

public interface StorageDriverInterface {
    public String put (String bucketName, String fileName, MultipartFile file);
}
