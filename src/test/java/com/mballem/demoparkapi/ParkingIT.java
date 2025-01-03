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
		
		testClient.post().uri("/api/v1/parkings/check-in")
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
	
	@Test
	public void createCheckin_WithClientRole_ReturnErrorStatus403() {
		ParkingCreateDto createDto = ParkingCreateDto.builder()
				.licensePlate("WER-1111")
				.brand("FIAT")
				.model("PALIO 1.0")
				.color("BLUE")
				.clientCpf("19913027039")
				.build();
		
		testClient.post().uri("/api/v1/parkings/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "nreis@email.com", "123456"))
		.bodyValue(createDto)
		.exchange()
		.expectStatus().isForbidden()
		.expectBody()
		.jsonPath("status").isEqualTo("403")
		.jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	@Test
	public void createCheckin_WithInvalidData_ReturnErrorStatus422() {
		ParkingCreateDto createDto = ParkingCreateDto.builder()
				.licensePlate("")
				.brand("")
				.model("")
				.color("")
				.clientCpf("")
				.build();
		
		testClient.post().uri("/api/v1/parkings/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "nreis@email.com", "123456"))
		.bodyValue(createDto)
		.exchange()
		.expectStatus().isEqualTo(422)
		.expectBody()
		.jsonPath("status").isEqualTo("422")
		.jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	@Test
	public void createCheckin_WithNonExistingCpf_ReturnErrorStatus404() {
		ParkingCreateDto createDto = ParkingCreateDto.builder()
				.licensePlate("WER-1111")
				.brand("FIAT")
				.model("PALIO 1.0")
				.color("BLUE")
				.clientCpf("55541407001")
				.build();
		
		testClient.post().uri("/api/v1/parkings/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
		.bodyValue(createDto)
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("status").isEqualTo("404")
		.jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	@Sql(scripts = "/sql/parkings/parkings-insert-occupied-spots.sql", executionPhase =  Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/parkings/parkings-delete-occupied-spots.sql", executionPhase =  Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Test
	public void createCheckin_WithOccupiedSpots_ReturnErrorStatus404() {
		ParkingCreateDto createDto = ParkingCreateDto.builder()
				.licensePlate("WER-1111")
				.brand("FIAT")
				.model("PALIO 1.0")
				.color("BLUE")
				.clientCpf("19913027039")
				.build();
		
		testClient.post().uri("/api/v1/parkings/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
		.bodyValue(createDto)
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("status").isEqualTo("404")
		.jsonPath("path").isEqualTo("/api/v1/parkings/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	@Test
	public void findCheckin_WithAdminRole_ReturnDataStatus200() {

		testClient.get()
		.uri("/api/v1/parkings/check-in/{receipt}", "20230313-101300")
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("licensePlate").isEqualTo("FIT-1010")
		.jsonPath("brand").isEqualTo("FIAT")
		.jsonPath("model").isEqualTo("PALIO")
		.jsonPath("color").isEqualTo("GREEN")
		.jsonPath("clientCpf").isEqualTo("57522693004")
		.jsonPath("receipt").isEqualTo("20230313-101300")
		.jsonPath("entryDate").isEqualTo("2023-03-13 10:15:00")
		.jsonPath("spotCode").isEqualTo("A-01");
	}

	@Test
	public void findCheckin_WithClientRole_ReturnDataStatus200() {

		testClient.get()
		.uri("/api/v1/parkings/check-in/{receipt}", "20230313-101300")
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "dviana@email.com", "123456"))
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("licensePlate").isEqualTo("FIT-1010")
		.jsonPath("brand").isEqualTo("FIAT")
		.jsonPath("model").isEqualTo("PALIO")
		.jsonPath("color").isEqualTo("GREEN")
		.jsonPath("clientCpf").isEqualTo("57522693004")
		.jsonPath("receipt").isEqualTo("20230313-101300")
		.jsonPath("entryDate").isEqualTo("2023-03-13 10:15:00")
		.jsonPath("spotCode").isEqualTo("A-01");
	}

}
