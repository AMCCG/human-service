package com.backend.microservices.humanservice.service;

import com.backend.microservices.humanservice.model.HumanModel;
import com.backend.microservices.humanservice.model.entity.HumanEntity;
import com.backend.microservices.humanservice.model.entity.ProfileImageEntity;
import com.backend.microservices.humanservice.repository.HumanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Slf4j
@Service
@AllArgsConstructor
public class HumanService {
    private final HumanRepository humanRepository;
    private final HumanFileStorageService humanFileStorageService;

    public List<HumanModel> getAllHuman() throws IOException {
        List<HumanModel> humanModels = new ArrayList<>();
        List<HumanEntity> humanEntities = humanRepository.getAllHuman();
        log.info("Get all human size: {}", humanEntities.size());
        for (HumanEntity humanEntity : humanEntities) {
            HumanModel humanModel = new HumanModel();
            mapperToHumanModel(humanModel, humanEntity);
            humanModels.add(humanModel);
        }
        return humanModels;
    }

    public HumanModel getHumanById(int id) throws IOException {
        var humanEntity = humanRepository.getHumanById(id);
        log.info("Get human by id: {}", humanEntity);
        HumanModel humanModel = new HumanModel();
        mapperToHumanModel(humanModel, humanEntity);
        return humanModel;
    }

    public HumanModel create(HumanModel humanModel) {
        log.info("Create human: {}", humanModel);
        HumanEntity humanEntity = new HumanEntity();
        mapperToHumanEntity(humanEntity, humanModel);
        var id = humanRepository.create(humanEntity);
        humanModel.setId(id);
        return humanModel;
    }

    private void mapperToHumanModel(HumanModel humanModel, HumanEntity humanEntity) throws IOException {
        humanModel.setId(humanEntity.getId());
        humanModel.setIdCard(humanEntity.getIdCard());
        humanModel.setThaiTitle(humanEntity.getThaiTitle());
        humanModel.setThaiFirstName(humanEntity.getThaiFirstName());
        humanModel.setThaiLastName(humanEntity.getThaiLastName());
        humanModel.setEnglishTitle(humanEntity.getEnglishTitle());
        humanModel.setEnglishFirstName(humanEntity.getEnglishFirstName());
        humanModel.setEnglishLastName(humanEntity.getEnglishLastName());
        humanModel.setAddress(humanEntity.getAddress());
        humanModel.setEmail(humanEntity.getEmail());
        humanModel.setPhone(humanEntity.getPhone());
        humanModel.setDateOfBirth(humanEntity.getDateOfBirth());
        humanModel.setProfileImageId(humanEntity.getProfileImageId());
        humanModel.setImage(getImage());
    }


    private void mapperToHumanEntity(HumanEntity humanEntity, HumanModel humanModel) {
        humanEntity.setIdCard(humanModel.getIdCard());
        humanEntity.setThaiTitle(humanModel.getThaiTitle());
        humanEntity.setThaiFirstName(humanModel.getThaiFirstName());
        humanEntity.setThaiLastName(humanModel.getThaiLastName());
        humanEntity.setEnglishTitle(humanModel.getEnglishTitle());
        humanEntity.setEnglishFirstName(humanModel.getEnglishFirstName());
        humanEntity.setEnglishLastName(humanModel.getEnglishLastName());
        humanEntity.setAddress(humanModel.getAddress());
        humanEntity.setEmail(humanModel.getEmail());
        humanEntity.setPhone(humanModel.getPhone());
        humanEntity.setDateOfBirth(humanModel.getDateOfBirth());
        humanEntity.setProfileImageId(humanModel.getProfileImageId());
    }

    private String getIdCard() {
        var random = getRandom();
        String first = String.valueOf(random.nextInt(1, 9));
        String second = String.valueOf(random.nextInt(10, 99));
        String third = String.valueOf(random.nextInt(10, 99));
        String fourth = String.valueOf(random.nextInt(10000, 99999));
        String five = String.valueOf(random.nextInt(10, 99));
        var idCard = first + second + third + fourth + five;
        int length = 13;
        int total = 0;
        for (int i = 0; i < idCard.length(); i++) {
            total += (i * length);
            length--;
        }
        int total2 = total % 11;
        total2 = 11 - total2;
        return idCard + String.valueOf(total2).substring(String.valueOf(total2).length() - 1);
    }

    private Random getRandom() {
        return new Random();
    }

    public HumanModel create(HumanModel humanModel, MultipartFile file) throws IOException {
        log.info("Create human: {}", humanModel);
        ProfileImageEntity profileImageEntity = humanFileStorageService.storeFile(file);
        log.info("Profile image: {}", profileImageEntity);
        HumanEntity humanEntity = new HumanEntity();
        mapperToHumanEntity(humanEntity, humanModel);
        var id = humanRepository.create(humanEntity);
        humanModel.setId(id);
        humanModel.setProfileImageId(profileImageEntity.getId());
        humanModel.setImage(getImage());
        return humanModel;
    }

    public byte[] getImage() throws IOException {
        Resource imResource = humanFileStorageService.loadFile();
        return StreamUtils.copyToByteArray(imResource.getInputStream());
    }
}
