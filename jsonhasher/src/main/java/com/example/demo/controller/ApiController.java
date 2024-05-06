package jsonhasher.src.main.java.com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.model.ProjectInfo;
import com.example.demo.model.PersonDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonFactory;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class ApiController {


    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/hash")
    public ResponseEntity<String> getJsonHash(@RequestBody ProjectInfo projectInfo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(projectInfo);
            String hash = DigestUtils.sha256Hex(jsonStr);
            return ResponseEntity.ok(hash);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing JSON");
        }
    }

    @PostMapping("/hashv2")
    public ResponseEntity<String> getJsonHashv2(@RequestBody ProjectInfo projectInfo) {
        try {
            int hash = 0;
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            String jsonString = mapper.writeValueAsString(projectInfo);
            try {
                JsonNode jsonNode = mapper.readTree(factory.createParser(jsonString));
                hash = jsonNode.hashCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(String.valueOf(hash));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing JSON");
        }

    }

    @PostMapping("/hashv3")
    public ResponseEntity<String> getJsonHashv3(@RequestBody ProjectInfo projectInfo) {
        try {
            // Normalize the project information
            projectInfo.normalize();

            // Convert to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String normalizedJsonStr = objectMapper.writeValueAsString(projectInfo);

            // Generate hash
            String hash = DigestUtils.sha256Hex(normalizedJsonStr);
            return ResponseEntity.ok(hash);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing JSON: " + e.getMessage());
        }
    }

    @PostMapping("/hashv4")
    public ResponseEntity<String> getJsonHashv4(@RequestBody ProjectInfo projectInfo) {
        try {
            // Convert to canonical JSON string
            String canonicalJson = objectMapper.writeValueAsString(projectInfo);

            // Generate hash
            String hash = DigestUtils.sha256Hex(canonicalJson);
            return ResponseEntity.ok(hash);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing JSON: " + e.getMessage());
        }
    }

    @PostMapping("/hashv5")
    public ResponseEntity<String> getJsonHashv5(@RequestBody ProjectInfo projectInfo) {
        try {
            int hash = 0;
            ObjectMapper mapper = new ObjectMapper();
    //        JsonFactory factory = mapper.getFactory();
    //        String jsonString = mapper.writeValueAsString(projectInfo);
            try {
                JsonNode jsonNode = mapper.valueToTree(projectInfo);
                hash = jsonNode.hashCode();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(String.valueOf(hash));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing JSON");
        }

    }

}