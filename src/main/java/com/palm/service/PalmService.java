package com.palm.service;

import com.palm.model.PalmData;
import com.palm.repository.PalmDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tg.vein.SDPVD310API;

import java.util.Optional;

@Service
public class PalmService {

    @Autowired
    private PalmDataRepository palmDataRepository;

    /**
     * Validate a given palm binary against the stored template.
     *
     * @param palmId      The ID of the palm to validate.
     * @param palmBinary  The binary data of the palm to validate.
     * @return true if validation is successful, false otherwise.
     */
    public boolean matchPalmBinary(String palmId, byte[] palmBinary) {
        Optional<PalmData> palmDataOptional = palmDataRepository.findById(palmId);

        if (palmDataOptional.isEmpty()) {
            System.err.println("PalmData not found for ID: " + palmId);
            return false;
        }

        PalmData palmData = palmDataOptional.get();
        byte[] storedTemplate = palmData.getPalmTemplate();

        int groupId = 1; // Group ID for matching; customize as needed
        byte[] userId = new byte[32]; // For the matched user ID
        int[] palmIdOutput = new int[1]; // Matched palm ID
        byte[] updatedTemplate = new byte[getTemplateSize()]; // Buffer for updated template

        // Call the matching API
        int result = SDPVD310API.instanceDll.SD_API_Match1VNEx(
                palmBinary,
                groupId,
                userId,
                palmIdOutput,
                updatedTemplate
        );

        // Handle the result
        if (result == 0) {
            System.out.println("Match successful!");
            System.out.println("Matched User ID: " + new String(userId));
            System.out.println("Palm ID: " + palmIdOutput[0]);

            // Optionally update the stored template
            palmData.setPalmTemplate(updatedTemplate);
            palmDataRepository.save(palmData);
            return true;
        } else {
            System.err.println("Match failed. Error code: " + result);
            return false;
        }
    }

    /**
     * Save a palm binary template into the database.
     *
     * @param palmData The palm data object to store.
     */
    public void savePalmBinary(PalmData palmData) {
        Optional<PalmData> existingPalmData = palmDataRepository.findById(palmData.getPalmId());

        if (existingPalmData.isPresent()) {
            // Update existing data
            PalmData existingData = existingPalmData.get();
            existingData.setSchoolId(palmData.getSchoolId());
            existingData.setPalmTemplate(palmData.getPalmTemplate());
            palmDataRepository.save(existingData);
            System.out.println("Updated existing palm data for ID: " + palmData.getPalmId());
        } else {
            // Save new data
            palmDataRepository.save(palmData);
            System.out.println("Stored new palm data for ID: " + palmData.getPalmId());
        }
    }

    /**
     * Utility method to fetch the template size from the SDK.
     *
     * @return The size of the palm template.
     */
    private int getTemplateSize() {
        int[] tmplSize = new int[1];
        SDPVD310API.instanceDll.SD_API_GetBufferSize(new int[1], tmplSize, new int[1], new int[1]);
        return tmplSize[0];
    }
}

