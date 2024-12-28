package com.mballem.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mballem.demoparkapi.web.dto.ParkingCreateDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parkings/parkings-insert.sql", executionPhase =  Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parkings/parkings-delete.sql", executionPhase =  Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {
	
	@Autowired
	WebTestClient testClient;
	
	@Test
	public void createCheckin_WithValidData_ReturnCreatedAndLocation() {
		ParkingCreateDto createDto = ParkingCreateDto.builder()
				.licensePlate("WER-1111")
				.brand("FIAT")
				.model("PALIO 1.0")
				.color("BLUE")
				.clientCpf("19913027039")
				.build();
		
		testClient.post().uri("api/v1/parkings/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
		.bodyValue(createDto)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().exists(HttpHeaders.LOCATION)
		.expectBody()
		.jsonPath("licensePlate").isEqualTo("WER-1111")
		.jsonPath("brand").isEqualTo("FIAT")
		.jsonPath("model").isEqualTo("PALIO 1.0")
		.jsonPath("color").isEqualTo("BLUE")
		.jsonPath("clientCpf").isEqualTo("19913027039")
		.jsonPath("receipt").exists()
		.jsonPath("entryDate").exists()
		.jsonPath("spotCode").exists();
	}
	

}
