package com.example.bootcamps.infrastructure;

import com.example.bootcamps.application.BootcampDTO;
import com.example.bootcamps.application.BootcampService;
import com.example.bootcamps.domain.exception.CustomException;
import com.example.bootcamps.domain.model.Bootcamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class BootcampController {

    private final BootcampService bootcampService;

    @Autowired
    public BootcampController(BootcampService bootcampService) {
        this.bootcampService = bootcampService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Bootcamp> createBootcamp(@RequestBody Bootcamp bootcamp) {
        return bootcampService.createBootcamp(bootcamp);
    }

    @GetMapping("/{id}")
    public Mono<Bootcamp> getBootcampById(@PathVariable Long id) {
        return bootcampService.getBootcampById(id);
    }

    @GetMapping
    public Flux<BootcampDTO> listBootcamps(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "name") String sortField,
                                           @RequestParam(defaultValue = "asc") String sortOrder) {
        return bootcampService.listBootcamps(page, size, sortField, sortOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBootcamp(@PathVariable Long id) {
        return bootcampService.deleteBootcamp(id);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
