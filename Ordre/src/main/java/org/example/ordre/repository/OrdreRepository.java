package org.example.ordre.repository;


import org.example.ordre.model.Ordre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdreRepository extends JpaRepository<Ordre, Long> {
    List<Ordre> findByStatus(String status);
    Optional<Ordre> findByReference(String reference);
}