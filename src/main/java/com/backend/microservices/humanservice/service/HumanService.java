package com.backend.microservices.humanservice.service;

import com.backend.microservices.humanservice.model.HumanModel;
import com.backend.microservices.humanservice.model.entity.HumanEntity;
import com.backend.microservices.humanservice.repository.HumanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


@Slf4j
@Service
@AllArgsConstructor
public class HumanService {
    private final HumanRepository humanRepository;

    public HumanModel create(HumanModel humanModel) {
        log.info("Create human: {}", humanModel);
        HumanEntity humanEntity = getHumanEntity();
        var id = humanRepository.create(humanEntity);
        humanModel.setId(id);
        return humanModel;
    }

    private HumanEntity getHumanEntity() {
        var random = getRandom();
        HumanEntity human = new HumanEntity();
        human.setIdCard(getIdCard());
        human.setThaiTitle("setThaiTitle");
        human.setThaiFirstName("thaiFirstName");
        human.setThaiLastName("ThaiLastName");
        human.setEnglishTitle("setEnglishTitle");
        human.setEnglishFirstName("setEnglishFirstName");
        human.setEnglishLastName("setEnglishLastName");
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        c.set(Calendar.YEAR, random.nextInt(1900, 2024));
        human.setDateOfBirth(c.getTime());
        human.setAddress("9 Ratchadapisek Rd., Jatujak Bangkok 10900 Thailand");
        human.setEmail(human.getEnglishFirstName() + "@microservices.com");
        human.setPhone("0900000000");
        return human;
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
}
