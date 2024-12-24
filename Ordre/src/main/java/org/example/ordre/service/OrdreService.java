package org.example.ordre.service;


import org.example.ordre.dto.OrdreRequest;
import org.example.ordre.dto.OrdreResponse;
import org.example.ordre.model.Ordre;
import org.example.ordre.repository.OrdreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class OrdreService {
    @Autowired
    private OrdreRepository ordreRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public OrdreResponse createOrdre(OrdreRequest request) {
        Ordre ordre = new Ordre();
        ordre.setDescription(request.getDescription());
        ordre.setReference(request.getReference());
        ordre.setStatus("CREATED");
        ordre.setDateCreation(LocalDateTime.now());

        ordre = ordreRepository.save(ordre);
        kafkaTemplate.send("ordre-topic", ordre.getReference());

        return convertToResponse(ordre);
    }

    private OrdreResponse convertToResponse(Ordre ordre) {
        OrdreResponse response = new OrdreResponse();
        response.setId(ordre.getId());
        response.setReference(ordre.getReference());
        response.setStatus(ordre.getStatus());
        response.setDateCreation(ordre.getDateCreation());
        return response;
    }

    public OrdreResponse getOrdre(Long id) {
        Ordre ordre = ordreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordre not found"));
        return convertToResponse(ordre);
    }
}

