package com.springtest.account.app;

import com.springtest.account.dto.ClientResponseDTO;
import com.springtest.account.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceApp {


    private final WebClient.Builder webClientBuilder;
    public ClientResponseDTO findClient(Long clientId) {
        return webClientBuilder.build().get()
                .uri("http://client-service/clientes/{id}", clientId)
                .retrieve()
                .bodyToMono(ClientResponseDTO.class)
                .onErrorResume(e -> {
                    log.error("Error retrieving client", e);
                    throw new NotFoundException("Cliente con id " + clientId + " no encontrado");
                })
                .block();
    }
}
