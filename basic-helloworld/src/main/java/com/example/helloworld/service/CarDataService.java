package com.example.helloworld.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarDataService {

    private static final String DATA_FILE = "./car_data.csv";
    @Value("${api.url}")
    private String apiUrl;

    public String checkCarData(String carName) {
        // Fetch car data from API
        RestTemplate restTemplate = new RestTemplate();
        String jsonData = restTemplate.getForObject(apiUrl, String.class);

        // Hash the JSON data
        String currentHash = hashData(jsonData);

        // Load existing data
        Map<String, String> existingData = loadExistingData();

        // Check for changes and update file
        return checkAndUpdate(carName, currentHash, existingData);
    }

    private String checkAndUpdate(String carName, String currentHash, Map<String, String> existingData) {
        if (!existingData.containsKey(carName)) {
            updateDataFile(carName, currentHash, existingData);
            return "New car added";
        } else if (!existingData.get(carName).equals(currentHash)) {
            updateDataFile(carName, currentHash, existingData);
            return "Car details changed";
        } else {
            return "Car unchanged";
        }
    }

    private Map<String, String> loadExistingData() {
        Map<String, String> dataMap = new HashMap<>();
        try (CSVParser csvParser = new CSVParser(new FileReader(DATA_FILE), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                dataMap.put(record.get("CarName"), record.get("Hash"));
            }
        } catch (IOException e) {
            // Handle exception or log
        }
        return dataMap;
    }

    private void updateDataFile(String carName, String hash, Map<String, String> existingData) {
        existingData.put(carName, hash);
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(DATA_FILE), CSVFormat.DEFAULT.withHeader("CarName", "Hash"))) {
            for (Map.Entry<String, String> entry : existingData.entrySet()) {
                printer.printRecord(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            // Handle exception or log
        }
    }

    private String hashData(String jsonData) {
        return DigestUtils.sha256Hex(jsonData);
    }
}

