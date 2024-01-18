package com.backend.microservices.humanservice.service;

import com.backend.microservices.humanservice.config.ProfileImageConfig;
import com.backend.microservices.humanservice.exception.StorageException;
import com.backend.microservices.humanservice.exception.StorageFileNotFoundException;
import com.backend.microservices.humanservice.model.entity.ProfileImageEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class HumanFileStorageService {

    private final ProfileImageConfig profileImageConfig;

    public ProfileImageEntity storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        Image image;
        log.info("File: {}", file.getOriginalFilename());
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new StorageException("The file could not be opened , it is not an image");
        }
        if (image == null) {
            throw new StorageException("The file could not be opened , it is not an image");
        }
        String[] fileName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        var newFileName = UUID.randomUUID() + "." + fileName[fileName.length - 1];
        log.info("New file name: {}", newFileName);
        checkPathDirectory(this.profileImageConfig.getStoreagePath());
        Path destinationFile = this.profileImageConfig.getStoreagePath().resolve(Paths.get(newFileName)).normalize().toAbsolutePath();
        log.info("Destination: {}", destinationFile);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
        ProfileImageEntity profileImageEntity = new ProfileImageEntity();
        profileImageEntity.setId(1);
        profileImageEntity.setPath(destinationFile.toString());
        return profileImageEntity;
    }

    private void checkPathDirectory(Path storeagePath) {
        try {
            log.info("Create directory: {}", storeagePath);
            Files.createDirectories(storeagePath);
        } catch (IOException e) {
            throw new StorageException("Can not create directory.", e);
        }
    }

    public Resource loadFile() {
        var filename = "f43a6c14-034f-491f-ae9a-85c3b4e71f7d.png";
        try {
            Path file = this.profileImageConfig.getStoreagePath().resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
}
