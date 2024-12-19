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
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePalmBinary(@RequestBody Map<String, String> request) {
        String palmId = request.get("palmId");
        String palmBinaryBase64 = request.get("palmBinary");

        if (palmId == null || palmBinaryBase64 == null) {
            return ResponseEntity.badRequest().body(false);
        }

        byte[] palmBinary = Base64.getDecoder().decode(palmBinaryBase64);
        boolean isValid = palmService.matchPalmBinary(palmId, palmBinary);
        return ResponseEntity.ok(isValid);
    }

    // Endpoint to store a palm binary
    @PostMapping("/store")
    public ResponseEntity<String> storePalmBinary(@RequestBody Map<String, String> request) {
        String palmId = request.get("palmId");
        String schoolId = request.get("schoolId");
        String palmBinaryBase64 = request.get("palmBinary");

        if (palmId == null || schoolId == null || palmBinaryBase64 == null) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        byte[] palmBinary = Base64.getDecoder().decode(palmBinaryBase64);
        PalmData palmData = new PalmData(palmId, schoolId, palmBinary);

        palmService.savePalmBinary(palmData);
        return ResponseEntity.ok("Palm binary stored successfully");
    }
}

