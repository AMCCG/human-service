package com.backend.microservices.humanservice.controller;

import com.backend.microservices.humanservice.model.HumanModel;
import com.backend.microservices.humanservice.service.HumanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/human")
@RequiredArgsConstructor
public class HumanController {

    private final HumanService humanService;

    @GetMapping("")
    public ResponseEntity<List<HumanModel>> getAllHuman(@RequestHeader HttpHeaders headers) {
        List<HumanModel> lists = humanService.getAllHuman();
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HumanModel> findById(@RequestHeader HttpHeaders headers, @PathVariable String id) {
        HumanModel human = humanService.getHumanById(Integer.parseInt(id));
        return new ResponseEntity<>(human, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HumanModel> insertHuman(@RequestHeader HttpHeaders headers, @RequestBody HumanModel humanModel) {
        var humanResponse = humanService.create(humanModel);
        return new ResponseEntity<>(humanResponse, HttpStatus.OK);
    }

}
