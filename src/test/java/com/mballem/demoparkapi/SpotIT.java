package com.mballem.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mballem.demoparkapi.web.dto.SpotCreateDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/spots/spots-insert.sql", executionPhase =  Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/spots/spots-delete.sql", executionPhase =  Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SpotIT {
	
	@Autowired
	WebTestClient testClient;
	
	@Test
	public void createSpot_WithValidData_ReturnLocationWithStatus201() {
		testClient
				.post()
				.uri("/api/v1/spots")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.bodyValue(new SpotCreateDto("A-05", "FREE"))
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().exists(HttpHeaders.LOCATION);
	}
	
	@Test
	public void createSpot_WithExistingCode_ReturnErrorMessageWithStatus409() {
		testClient
				.post()
				.uri("/api/v1/spots")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.bodyValue(new SpotCreateDto("A-01", "FREE"))
				.exchange()
				.expectStatus().isEqualTo(409)
				.expectBody()
				.jsonPath("status").isEqualTo(409)
				.jsonPath("method").isEqualTo("POST")
				.jsonPath("path").isEqualTo("/api/v1/spots");
	}
	
	@Test
	public void createSpot_WithInvalidData_ReturnErrorMessageWithStatus422() {
		testClient
				.post()
				.uri("/api/v1/spots")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.bodyValue(new SpotCreateDto("", ""))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody()
				.jsonPath("status").isEqualTo(422)
				.jsonPath("method").isEqualTo("POST")
				.jsonPath("path").isEqualTo("/api/v1/spots");
		
		testClient
		.post()
		.uri("/api/v1/spots")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
		.bodyValue(new SpotCreateDto("A-501", "RESERVED"))
		.exchange()
		.expectStatus().isEqualTo(422)
		.expectBody()
		.jsonPath("status").isEqualTo(422)
		.jsonPath("method").isEqualTo("POST")
		.jsonPath("path").isEqualTo("/api/v1/spots");
	}

}
