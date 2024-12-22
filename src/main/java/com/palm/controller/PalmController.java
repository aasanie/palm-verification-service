package com.palm.controller;

import com.palm.model.PalmData;
import com.palm.service.PalmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/palm")
public class PalmController {

    @Autowired
    private PalmService palmService;

    // Endpoint to validate a palm binary
    @PostMapping(value = "/validate", consumes = "application/octet-stream")
    public ResponseEntity<Boolean> validatePalmBinary(@RequestParam String palm_id, @RequestBody byte[] palm_binary) {

        if (palm_id == null || palm_binary == null) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean isValid = palmService.matchPalmBinary(palm_id, palm_binary);
        return ResponseEntity.ok(isValid);
    }

    // Endpoint to store a palm binary
    @PostMapping(value = "/store", consumes = "application/octet-stream")
    public ResponseEntity<String> storePalmBinary(@RequestParam String schoolId, @RequestParam String palmId, @RequestBody byte[] palmBinary) {

        if (palmId == null || schoolId == null || palmBinary == null) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        PalmData palmData = new PalmData(palmId, schoolId, palmBinary);

        palmService.savePalmBinary(palmData);
        return ResponseEntity.ok("Palm binary stored successfully");
    }
}

