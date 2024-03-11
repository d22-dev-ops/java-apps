package com.example.helloworld.controller;

import com.example.helloworld.service.CarDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarDataController {

    @Autowired
    private CarDataService carDataService;

    @GetMapping("/checkCarData")
    public String checkCarData(@RequestParam String carName) {
        return carDataService.checkCarData(carName);
    }
}
