package com.springtest.client.controller;


import com.springtest.client.dto.ClientDTO;
import com.springtest.client.dto.CreateClientDTO;
import com.springtest.client.interfaces.IClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService iClientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientDTO> createClient (
            @Valid @RequestBody CreateClientDTO createClientDTO
    ) {
        return new ResponseEntity<>(iClientService.createClient(createClientDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(iClientService.getAllClients());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(iClientService.getClientById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientData) {
        return ResponseEntity.ok(iClientService.updateClient(id, clientData));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteClient(@PathVariable Long id) {
        return iClientService.deleteClient(id);

    }

}
