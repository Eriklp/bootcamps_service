package com.example.bootcamps.domain.repository;

import com.example.bootcamps.domain.model.Bootcamp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {
    // Métodos adicionales si son necesarios
}
