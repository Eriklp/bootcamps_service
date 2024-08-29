package com.example.bootcamps.application;

import com.example.bootcamps.domain.model.Bootcamp;
import com.example.bootcamps.domain.repository.BootcampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Flux<Bootcamp> listBootcamps(int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return Flux.fromIterable(bootcampRepository.findAll(pageable));
    }

    // Otros métodos que devuelven Mono o Flux según corresponda
}
