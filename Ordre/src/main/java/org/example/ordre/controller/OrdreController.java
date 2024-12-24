package org.example.ordre.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.ordre.dto.OrdreRequest;
import org.example.ordre.dto.OrdreResponse;
import org.example.ordre.service.OrdreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordres")
public class OrdreController {
    @Autowired
    private OrdreService ordreService;

    @PostMapping
    public ResponseEntity<OrdreResponse> createOrdre(@RequestBody OrdreRequest request) {
        return ResponseEntity.ok(ordreService.createOrdre(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdreResponse> getOrdre(@PathVariable Long id) {
        return ResponseEntity.ok(ordreService.getOrdre(id));
    }
}