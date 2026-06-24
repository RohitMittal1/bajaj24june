package com.example.bfhl.service;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.name:rohit}")
    private String fullName;

    @Value("${bfhl.user.dob:24062026}")
    private String dob;

    @Value("${bfhl.user.email:rohit0870.be23@chitkata.edu.in}")
    private String email;

    @Value("${bfhl.user.roll-number:2310990870}")
    private String rollNumber;

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        if (request == null || request.getData() == null) {
            return BfhlResponse.builder()
                    .is_success(false)
                    .user_id(fullName.toLowerCase() + "_" + dob)
                    .email(email)
                    .roll_number(rollNumber)
                    .odd_numbers(Collections.emptyList())
                    .even_numbers(Collections.emptyList())
                    .alphabets(Collections.emptyList())
                    .special_characters(Collections.emptyList())
                    .sepcial_characters(Collections.emptyList())
                    .sum("0")
                    .concat_string("")
                    .build();
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        int sum = 0;

        List<Character> allAlphabetChars = new ArrayList<>();

        for (String item : request.getData()) {
            if (item == null || item.isEmpty()) continue;

            // Check if it is an integer number
            if (item.matches("^-?\\d+$")) {
                try {
                    int val = Integer.parseInt(item);
                    sum += val;
                    if (val % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                } catch (NumberFormatException e) {
                    specialCharacters.add(item);
                }
            } else if (item.matches("^[a-zA-Z]+$")) {
                alphabets.add(item.toUpperCase());
                for (char c : item.toCharArray()) {
                    allAlphabetChars.add(c);
                }
            } else {
                specialCharacters.add(item);
                for (char c : item.toCharArray()) {
                    if (Character.isLetter(c)) {
                        allAlphabetChars.add(c);
                    }
                }
            }
        }

        // Concatenate alphabetical characters in reverse order with alternating caps
        Collections.reverse(allAlphabetChars);
        StringBuilder concatBuilder = new StringBuilder();
        for (int i = 0; i < allAlphabetChars.size(); i++) {
            char ch = allAlphabetChars.get(i);
            if (i % 2 == 0) {
                concatBuilder.append(Character.toUpperCase(ch));
            } else {
                concatBuilder.append(Character.toLowerCase(ch));
            }
        }

        return BfhlResponse.builder()
                .is_success(true)
                .user_id(fullName.toLowerCase() + "_" + dob)
                .email(email)
                .roll_number(rollNumber)
                .odd_numbers(oddNumbers)
                .even_numbers(evenNumbers)
                .alphabets(alphabets)
                .special_characters(specialCharacters)
                .sepcial_characters(specialCharacters)
                .sum(String.valueOf(sum))
                .concat_string(concatBuilder.toString())
                .build();
    }
}
