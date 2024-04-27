package com.springtest.client.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springtest.client.dto.ClientDTO;
import com.springtest.client.dto.CreateClientDTO;

import java.util.List;

public interface IClientService {
    ClientDTO createClient(CreateClientDTO createClientDTO);

    List<ClientDTO> getAllClients();

    ClientDTO getClientById(Long clientId);

    ClientDTO updateClient(Long clientId, ClientDTO clientData);

    String deleteClient(Long clientId);

}
