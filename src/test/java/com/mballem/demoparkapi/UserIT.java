package com.mballem.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mballem.demoparkapi.web.dto.UserCreateDto;
import com.mballem.demoparkapi.web.dto.UserResponseDto;
import com.mballem.demoparkapi.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase =  Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase =  Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {
	
	@Autowired
	WebTestClient testClient;
	
	@Test
	public void createUser_WithValidUsernameAndPassword_ReturnUserCreatedWithStatus201() {
		UserResponseDto responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("rcaza@email.com", "123456"))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(UserResponseDto.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("rcaza@email.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
	}
	
	@Test
	public void createUser_WithInvalidUsername_ReturnErrorMessageStatus422() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("", "123456"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("rcaza@", "123456"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("rcaza@email", "123456"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

	}
	
	@Test
	public void createUser_WithInvalidPassword_ReturnErrorMessageStatus422() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("rcaza@email.com", ""))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("rcaza@email.com", "123"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("rcaza@email.com", "123456789"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

	}

}