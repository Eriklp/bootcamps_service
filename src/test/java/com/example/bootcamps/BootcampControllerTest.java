package com.example.bootcamps;

import com.example.bootcamps.application.BootcampDTO;
import com.example.bootcamps.application.BootcampService;
import com.example.bootcamps.domain.model.Bootcamp;
import com.example.bootcamps.infrastructure.BootcampController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebFluxTest(BootcampController.class)
public class BootcampControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BootcampService bootcampService;

    private Bootcamp bootcamp;
    private BootcampDTO bootcampDTO;

    @BeforeEach
    void setUp() {
        Set<String> capacities = new HashSet<>(Set.of("Microservices", "Cloud"));
        bootcamp = new Bootcamp(1L, "Spring Boot WebFlux", "Advanced Spring Boot for Microservices", capacities);
        bootcampDTO = new BootcampDTO(1L, "Spring Boot WebFlux", "Advanced Spring Boot for Microservices", capacities);
    }

    @Test
    void createBootcamp() {
        when(bootcampService.createBootcamp(any(Bootcamp.class))).thenReturn(Mono.just(bootcamp));

        webTestClient.post().uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bootcamp)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(bootcamp.getId())
                .jsonPath("$.name").isEqualTo(bootcamp.getName())
                .jsonPath("$.description").isEqualTo(bootcamp.getDescription());
    }

    @Test
    void getBootcampById() {
        when(bootcampService.getBootcampById(1L)).thenReturn(Mono.just(bootcamp));

        webTestClient.get().uri("/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(bootcamp.getId())
                .jsonPath("$.name").isEqualTo(bootcamp.getName())
                .jsonPath("$.description").isEqualTo(bootcamp.getDescription());
    }

    @Test
    void listBootcamps() {
        when(bootcampService.listBootcamps(0, 10, "name", "asc")).thenReturn(Flux.just(bootcampDTO));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/")
                                .queryParam("page", 0)
                                .queryParam("size", 10)
                                .queryParam("sortField", "name")
                                .queryParam("sortOrder", "asc")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BootcampDTO.class)
                .hasSize(1)
                .consumeWith(response -> {
                    List<BootcampDTO> bootcamps = response.getResponseBody();
                    BootcampDTO received = bootcamps.get(0);
                    Assertions.assertEquals(bootcampDTO.getId(), received.getId());
                    Assertions.assertEquals(bootcampDTO.getName(), received.getName());
                    Assertions.assertEquals(bootcampDTO.getDescription(), received.getDescription());
                });
    }

    @Test
    void deleteBootcamp() {
        given(bootcampService.deleteBootcamp(1L)).willReturn(Mono.empty());

        webTestClient.delete().uri("/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }
}
