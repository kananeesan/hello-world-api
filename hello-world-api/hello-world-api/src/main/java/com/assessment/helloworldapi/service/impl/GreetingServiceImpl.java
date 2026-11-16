package com.assessment.helloworldapi.service.impl;

import com.assessment.helloworldapi.exception.InvalidNameException;
import com.assessment.helloworldapi.service.GreetingService;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String greet(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidNameException();
        }

        String normalizedName = name.strip();
        char firstLetter = normalizedName.charAt(0);

        boolean startsWithAllowedLetter =
                (firstLetter >= 'A' && firstLetter <= 'M')
                        || (firstLetter >= 'a' && firstLetter <= 'm');

        if (!startsWithAllowedLetter) {
            throw new InvalidNameException();
        }

        String formattedName =
                Character.toUpperCase(firstLetter)
                        + normalizedName.substring(1);

        return "Hello " + formattedName;
    }
}
