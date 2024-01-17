package com.backend.microservices.humanservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Configuration
@ConfigurationProperties(prefix = "s3")
public class ProfileImageConfig {
    private String bucket;
    private String subPath;
    private String landing;

    public Path getStoreagePath() {
        return Paths.get(getFullPath());
    }

    public String getFullPath() {
        return String.format("%s/%s/%s", bucket, subPath, landing);
    }
}
