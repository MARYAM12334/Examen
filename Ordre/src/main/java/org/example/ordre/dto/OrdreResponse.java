package org.example.ordre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdreResponse {
    private Long id;
    private String reference;
    private String status;
    private LocalDateTime dateCreation;

}