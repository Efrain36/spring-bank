package com.springtest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtest.client.dto.CreateClientDTO;
import com.springtest.client.entity.Client;
import com.springtest.client.entity.Person;
import com.springtest.client.enums.GenderEnum;
import com.springtest.client.repository.ClientRepository;
import com.springtest.client.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MssPersonsApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private PersonRepository personRepository;


	@BeforeEach
	void setUp() {
		clientRepository.deleteAll();
	}


	@Test
	void shouldCreateClient() throws Exception {
		CreateClientDTO client = getCreateClientDTO();
		String clientString = objectMapper.writeValueAsString(client);
		mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(clientString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, clientRepository.findAll().size());
	}

	@Test
	void shouldGetTheClient() throws Exception {
		Client client = getClient();

		mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + client.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(client.getId()));
		Assertions.assertEquals(1, clientRepository.findAll().size());
	}

	private CreateClientDTO getCreateClientDTO() {
		return CreateClientDTO.builder()
				.name("Test client")
				.age(20)
				.address("42 Wallaby Way, Sydney")
				.phone("+123123123")
				.gender(GenderEnum.MASCULINO)
				.password("password")
				.identification("12313231")
				.status(true)
				.build();
	}

	private Client getClient(){
		Client client = Client.builder()
				.person(Person.builder()
						.name("Test client")
						.age(20)
						.address("42 Wallaby Way, Sydney")
						.phone("+123123123")
						.gender(GenderEnum.MASCULINO)
						.identification("12313231")
						.build())
				.password("Password")
				.status(true)
				.build();

		client = clientRepository.save(client);

		return client;
	}

	@Container
	public static PostgreSQLContainer postgres = new PostgreSQLContainer(
			DockerImageName.parse("postgres:15-alpine")
					.asCompatibleSubstituteFor("postgresql"))
			.withDatabaseName("testdb")
			.withPassword("test1234");

	@DynamicPropertySource
	public static void databaseProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> postgres.getJdbcUrl() + "?useSSL=false");
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

}
