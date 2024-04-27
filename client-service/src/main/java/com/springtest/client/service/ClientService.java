package com.springtest.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtest.client.dto.ClientDTO;
import com.springtest.client.dto.CreateClientDTO;
import com.springtest.client.entity.Client;
import com.springtest.client.entity.Person;
import com.springtest.client.kafka.event.ClientDeletedEvent;
import com.springtest.client.exception.NotFoundException;
import com.springtest.client.exception.PersonIdentificationAlreadyExistsException;
import com.springtest.client.interfaces.IClientService;
import com.springtest.client.repository.ClientRepository;
import com.springtest.client.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("iClientService")
@RequiredArgsConstructor
@Slf4j
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;


    @Transactional
    @Override
    public ClientDTO createClient(CreateClientDTO createClientDTO) {
        personRepository.findByIdentification(createClientDTO.getIdentification())
                .ifPresent(person -> {
                    throw new PersonIdentificationAlreadyExistsException("La persona con identificacion " + createClientDTO.getIdentification() + " ya existe");
                });

        Person person = new Person();
        person.setName(createClientDTO.getName());
        person.setAge(createClientDTO.getAge());
        person.setAddress(createClientDTO.getAddress());
        person.setGender(createClientDTO.getGender());
        person.setPhone(createClientDTO.getPhone());
        person.setIdentification(createClientDTO.getIdentification());
        person = personRepository.save(person);

        Client client = new Client();
        client.setPassword(passwordEncoder.encode(createClientDTO.getPassword()));
        client.setStatus(createClientDTO.getStatus());
        client.setPerson(person);
        client = clientRepository.save(client);

        return mapClientToDTO(client);
    }

    @Override
    public List<ClientDTO> getAllClients(){

        return clientRepository.findAll()
                .stream()
                .map(this::mapClientToDTO)
                .toList();
    }

    @Override
    public ClientDTO getClientById(Long clientId){
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new NotFoundException("Cliente con id " + clientId + " no encontrado")
        );

        return mapClientToDTO(client);
    }

    @Override
    @Transactional
    public ClientDTO updateClient(Long clientId, ClientDTO clientData) {
        Client client = clientRepository.findById(clientId)
                .map(cli -> {
                    updateClientData(cli, clientData);
                    Person personData = cli.getPerson();
                    updatePersonData(personData, clientData);
                    personRepository.save(personData);
                    cli.setPerson(personData);
                    return clientRepository.save(cli);
                })
                .orElseThrow(() -> new NotFoundException("Cliente con id " + clientId + " no encontrado"));

        return mapClientToDTO(client);
    }

    private void updateClientData(Client client, ClientDTO clientData) {
        Optional.ofNullable(clientData.getStatus()).ifPresent(client::setStatus);
    }

    private void updatePersonData(Person personData, ClientDTO clientData) {
        Optional.ofNullable(clientData.getIdentification()).ifPresent(personData::setIdentification);
        Optional.ofNullable(clientData.getGender()).ifPresent(personData::setGender);
        Optional.of(clientData.getAge()).ifPresent(personData::setAge);
        Optional.ofNullable(clientData.getName()).ifPresent(personData::setName);
        Optional.ofNullable(clientData.getAddress()).ifPresent(personData::setAddress);
        Optional.ofNullable(clientData.getPhone()).ifPresent(personData::setPhone);
    }

    @Override
    @Transactional
    public String deleteClient(Long id)  {
        if (!clientRepository.existsById(id)) {
            throw new NotFoundException("Cliente con id " + id + " no encontrado");
        }
        clientRepository.deleteById(id);
        try {
            kafkaTemplate.send("client.deleted",  objectMapper.writeValueAsString(new ClientDeletedEvent(id)));
        } catch (JsonProcessingException e) {
            log.error("Error al enviar mensaje de client.delete de kafka");
        }
        return  "Cliente con id " + id + " borrado exitosamente";
    }

    private ClientDTO mapClientToDTO (Client client){
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getPerson().getName())
                .identification(client.getPerson().getIdentification())
                .age(client.getPerson().getAge())
                .phone(client.getPerson().getPhone())
                .status(client.getStatus())
                .gender(client.getPerson().getGender())
                .address(client.getPerson().getAddress())
                .build();
    }
}
