package com.example.bootcamps.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class CapacityServiceClient {

    private final WebClient webClient;

    public CapacityServiceClient(WebClient.Builder webClientBuilder) {
        // Use the Docker service name and the internal port
        this.webClient = webClientBuilder.baseUrl("http://capacities-service:8081").build();
    }

    public Mono<Boolean> validateExistence(List<String> capacities) {
        return webClient
                .post()
                .uri("/validate")
                .bodyValue(new ArrayList<>(capacities))
                .retrieve()
                .bodyToMono(Boolean.class) // Espera un Boolean directamente desde el cuerpo de la respuesta
                .flatMap(allExist -> {
                    if (allExist) {
                        return Mono.just(true); // O proceder con la operación deseada si todas las tecnologías existen
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
