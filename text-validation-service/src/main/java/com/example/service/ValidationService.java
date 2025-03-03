package com.example.service;

import com.example.dto.ValidationRequest;
import com.example.dto.ValidationResponse;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.common.annotation.Blocking;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
public class ValidationService {

    private static final Logger LOG = Logger.getLogger(ValidationService.class);

    private static final List<String> FORBIDDEN_NAMES = List.of(
        "schlecht", "idiot", "mistvieh", "dumm", "böse", "alex"
    );

    private static final Pattern NUMBERS_PATTERN = Pattern.compile(".*\\d.*"); 


    @Incoming("validation-requests")
    @Outgoing("validation-responses-out")
    @Blocking 
    public ValidationResponse validate(ValidationRequest message) {
        LOG.info("Validating request: " + message);

        boolean isValidName = validateName(message.name());
        boolean isValidDescription = validateDescription(message.description());

        boolean isValid = isValidName && isValidDescription; 
    
        LOG.infof("Validation result for name '%s': %s", message.name(), isValid);
    
        return new ValidationResponse(message.id(), isValid);
    }


    private boolean validateName(String name) {
    
        if (name == null || name.trim().isEmpty()) {
            LOG.warn("Validation failed: Name is empty.");
            return false;
        }
    
        for (String word : FORBIDDEN_NAMES) {
            if (name.equalsIgnoreCase(word)) {
                LOG.warnf("Validation failed: Forbidden name '%s' found in name.", word);
                return false;
            }
        }
        return true;
    }

    private boolean validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            LOG.warn("Validation failed: Description is empty.");
            return false;
        }

        if (NUMBERS_PATTERN.matcher(description).matches()) { 
            LOG.warn("Validation failed: Description contains numbers.");
            return false;
        }

        return true;
    }
}
