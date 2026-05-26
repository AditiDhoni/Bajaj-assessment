package com.bfhl.api.service;

import com.bfhl.api.dto.BfhlRequest;
import com.bfhl.api.dto.BfhlResponse;
import com.bfhl.api.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BfhlServiceImpl implements BfhlService {

    // Credentials required in response
    private static final String USER_ID = "aditi_dhoni_24032005";
    private static final String EMAIL = "aditidhoni231188@acropolis.in";
    private static final String ROLL_NUMBER = "0827IT231008";

    @Override
    public BfhlResponse processRequest(BfhlRequest request) {
        // 1. Validation
        if (request == null || request.getData() == null) {
            throw new InvalidRequestException("Request body or 'data' field is null");
        }

        List<String> evenNumbers = new ArrayList<>();
        List<String> oddNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();

        int sumValue = 0;
        StringBuilder concatSource = new StringBuilder();

        // 2. Categorization logic
        for (String item : request.getData()) {
            if (item == null) {
                continue;
            }
            String trimmed = item.trim();
            if (trimmed.isEmpty()) {
                specialCharacters.add(item);
            } else if (trimmed.matches("^[0-9]+$")) {
                // Number
                try {
                    int val = Integer.parseInt(trimmed);
                    sumValue += val;
                    if (val % 2 == 0) {
                        evenNumbers.add(trimmed);
                    } else {
                        oddNumbers.add(trimmed);
                    }
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse number: {}", trimmed);
                    specialCharacters.add(item);
                }
            } else if (trimmed.matches("^[a-zA-Z]+$")) {
                // Alphabet
                alphabets.add(trimmed.toUpperCase());
                concatSource.append(trimmed);
            } else {
                // Special character
                specialCharacters.add(item);
            }
        }

        // 3. Alternating Caps Reverse Concatenated string
        String concatString = generateAlternatingCapsReverse(concatSource.toString());

        // 4. Build and return response
        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .evenNumbers(evenNumbers)
                .oddNumbers(oddNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(String.valueOf(sumValue))
                .concatString(concatString)
                .build();
    }

    private String generateAlternatingCapsReverse(String source) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        // Reverse the string
        String reversed = new StringBuilder(source).reverse().toString();
        // Alternating caps: index 0 (even) upper, index 1 (odd) lower
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
