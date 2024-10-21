package com.mballem.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mballem.demoparkapi.web.dto.ClientCreateDto;
import com.mballem.demoparkapi.web.dto.ClientResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clients/clients-insert.sql", executionPhase =  Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clients/clients-delete.sql", executionPhase =  Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class ClientIT {
	
	@Autowired
	WebTestClient testClient;
	
	@Test
	public void createClient_WithValidData_ReturnCreatedClientWithStatus201() {
		ClientResponseDto responseBody = testClient
				.post()
				.uri("/api/v1/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "cveloso@email.com", "123456"))
				.bodyValue(new ClientCreateDto("Caetano Veloso", "24205734042"))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(ClientResponseDto.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Caetano Veloso");
		org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("24205734042");
		
	}
		

}
