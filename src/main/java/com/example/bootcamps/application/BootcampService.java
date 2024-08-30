package com.example.bootcamps.application;

import com.example.bootcamps.domain.model.Bootcamp;
import com.example.bootcamps.domain.repository.BootcampRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;

@Service
public class BootcampService {

    private final BootcampRepository bootcampRepository;

    @Autowired
    public BootcampService(BootcampRepository bootcampRepository) {
        this.bootcampRepository = bootcampRepository;
    }

    public Mono<Bootcamp> createBootcamp(Bootcamp bootcamp) {
        return Mono.just(bootcampRepository.save(bootcamp));
    }

    public Mono<Bootcamp> getBootcampById(Long id) {
        return Mono.justOrEmpty(bootcampRepository.findById(id));
    }

    public Mono<Void> deleteBootcamp(Long id) {
        Mono.fromRunnable(() -> bootcampRepository.deleteById(id));
        return null;
    }

    @Transactional()
    public Flux<BootcampDTO> listBootcamps(int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<BootcampDTO> bootcampPage = bootcampRepository.findAll(pageRequest).map(this::initializeAndConvertToDTO);
        System.out.println(bootcampPage);
        return Flux.fromIterable(bootcampPage.getContent());
    }

    private BootcampDTO initializeAndConvertToDTO(Bootcamp bootcamp) {
        Hibernate.initialize(bootcamp.getCapacities());
        return convertToDTO(bootcamp);
    }

    private BootcampDTO convertToDTO(Bootcamp bootcamp) {
        // Convierte a DTO
        return new BootcampDTO(bootcamp.getId(), bootcamp.getName(), bootcamp.getDescription(), new HashSet<>(bootcamp.getCapacities()));
    }
    // Otros métodos que devuelven Mono o Flux según corresponda
}
