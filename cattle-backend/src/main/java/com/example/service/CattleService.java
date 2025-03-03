package com.example.service;

import com.example.dto.ValidationRequest;
import com.example.dto.ValidationResponse;
import com.example.model.Cattle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import java.util.Optional;

@ApplicationScoped
public class CattleService {

    private static final Logger LOG = Logger.getLogger(CattleService.class);

    @Inject
    @Channel("validation-requests")
    Emitter<ValidationRequest> validationRequestEmitter;

    @Transactional
    public Cattle saveCattle(Cattle cattle) {
        cattle.persist();
        return cattle;
    }

    public void saveCattleAndSendValidation(Cattle cattle) {
        Cattle savedCattle = saveCattle(cattle);
        sendValidationRequestAsync(savedCattle);
    }

    private void sendValidationRequestAsync(Cattle cattle) {
        LOG.infof("Sending validation request for Cattle ID: %s", cattle.id);
        ValidationRequest request = new ValidationRequest(cattle.id, cattle.name, cattle.description);
        validationRequestEmitter.send(request)
            .toCompletableFuture().join();
        
        LOG.info("Kafka message sent successfully: " + request);
    }
    

    @Incoming("validation-responses")
    @Transactional
    public void processValidationResponse(ValidationResponse response) {
        LOG.infof("Received validation response: ID=%s, Valid=%s", response.id(), response.valid());

        Optional<Cattle> cattleOptional = Cattle.findByIdOptional(response.id());
        if (cattleOptional.isEmpty()) {
            LOG.warn("Cattle not found");
            return;
        }

        Cattle cattle = cattleOptional.get();
        cattle.validated = response.valid();
        cattle.persist();

        LOG.infof("Updated cattle validation status: %s -> %s", cattle.id, response.valid());
    }
}
