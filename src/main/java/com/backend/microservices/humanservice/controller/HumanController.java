package com.backend.microservices.humanservice.controller;

import com.backend.microservices.humanservice.model.HumanModel;
import com.backend.microservices.humanservice.service.HumanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/human")
@RequiredArgsConstructor
public class HumanController {

    private final HumanService humanService;

    @GetMapping("")
    public ResponseEntity<List<HumanModel>> getAllHuman(@RequestHeader HttpHeaders headers) throws IOException {
        log.info("Header: {}", headers);
        List<HumanModel> lists = humanService.getAllHuman();
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HumanModel> findById(@RequestHeader HttpHeaders headers, @PathVariable String id) throws IOException {
        log.info("Header: {}", headers);
        HumanModel human = humanService.getHumanById(Integer.parseInt(id));
        return new ResponseEntity<>(human, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HumanModel> insertHuman(@RequestHeader HttpHeaders headers, @RequestParam("model") String modelJson, @RequestParam("file") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Header: {}", headers);
        log.info("Model json: {}", modelJson);
        log.info("File: {}", file);
        var humanModel = objectMapper.readValue(modelJson, HumanModel.class);
        log.info("Model human: {} ", humanModel);
        var humanResponse = humanService.create(humanModel, file);
        return new ResponseEntity<>(humanResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> getImage(@RequestHeader HttpHeaders headers) throws IOException {
        log.info("Header: {}", headers);
        byte[] image = humanService.getImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

}
