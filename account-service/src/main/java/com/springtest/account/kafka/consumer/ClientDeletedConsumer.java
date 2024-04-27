package com.springtest.account.kafka.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtest.account.interfaces.IAccountService;
import com.springtest.account.kafka.event.ClientDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class ClientDeletedConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    private IAccountService iAccountService;

    public ClientDeletedConsumer(){
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public void run(final String message){
        final ClientDeletedEvent clientDeletedEvent;

        try {
            clientDeletedEvent = this.objectMapper.readValue(message, ClientDeletedEvent.class);
        }catch (Exception e){
            log.error("Ocurrio un error al parsear el mensaje: " + e.getMessage());
            return;
        }
        this.runner(clientDeletedEvent);
    }

    public void runner( final ClientDeletedEvent clientDeletedEvent) {
        iAccountService.deleteAccountsByClientId(clientDeletedEvent.getClientId());
    }
}
