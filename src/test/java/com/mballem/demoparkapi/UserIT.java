package com.mballem.demoparkapi;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mballem.demoparkapi.web.dto.UserCreateDto;
import com.mballem.demoparkapi.web.dto.UserPasswordDto;
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
	public void createUser_WithInvalidUsername_ReturnErrorMessageWithStatus422() {
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
	public void createUser_WithInvalidPassword_ReturnErrorMessageWithStatus422() {
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

	@Test
	public void createUser_WithRepeatedUsername_ReturnUserCreatedWithStatus409() {
		ErrorMessage responseBody = testClient
				.post()
				.uri("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("celler@email.com", "123456"))
				.exchange()
				.expectStatus().isEqualTo(409)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
	}
	
	@Test
	public void findUser_WithExistingId_ReturnUserWithStatus200() {
		UserResponseDto responseBody = testClient
				.get()
				.uri("/api/v1/users/100")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserResponseDto.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("celler@email.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
	
		responseBody = testClient
				.get()
				.uri("/api/v1/users/101")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserResponseDto.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("nreis@email.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");

		responseBody = testClient
				.get()
				.uri("/api/v1/users/101")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "nreis@email.com", "123456"))
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserResponseDto.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("nreis@email.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
	
	}

	@Test
	public void findUser_WithNonExistingId_ReturnErrorMessageWithStatus404() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/users/0")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.exchange()
				.expectStatus().isNotFound()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
	}
	
	@Test
	public void findUser_WithClientUserLookingForAnotherClient_ReturnErrorMessageWithStatus403() {
		ErrorMessage responseBody = testClient
				.get()
				.uri("/api/v1/users/102")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "nreis@email.com", "123456"))
				.exchange()
				.expectStatus().isForbidden()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
	}

	@Test
	public void editPassword_WithValidData_ReturnStatus204() {
		testClient
				.patch()
				.uri("/api/v1/users/100")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "123456", "123456"))
				.exchange()
				.expectStatus().isNoContent();
		
		testClient
		.patch()
		.uri("/api/v1/users/101")
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "nreis@email.com", "123456"))
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(new UserPasswordDto("123456", "123456", "123456"))
		.exchange()
		.expectStatus().isNoContent();
	
	}

	@Test
	public void editPassword_WithDifferentUsers_ReturnErrorMessageWithStatus403() {
		ErrorMessage responseBody = testClient
				.patch()
				.uri("/api/v1/users/0")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "celler@email.com", "123456"))
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "123456", "123456"))
				.exchange()
				.expectStatus().isForbidden()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
		
		responseBody = testClient
				.patch()
				.uri("/api/v1/users/0")
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "nreis@email.com", "123456"))
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "123456", "123456"))
				.exchange()
				.expectStatus().isForbidden()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
		
	}
	
	@Test
	public void editPassword_WithInvalidFields_ReturnErrorMessageWithStatus422() {

		ErrorMessage responseBody = testClient
				.patch()
				.uri("/api/v1/users/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("", "", ""))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		responseBody = testClient
				.patch()
				.uri("/api/v1/users/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("12345", "12345", "12345"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = testClient
				.patch()
				.uri("/api/v1/users/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("12345678", "12345678", "12345678"))
				.exchange()
				.expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

	}
	
	@Test
	public void editPassword_WithInvalidPasswords_ReturnErrorMessageWithStatus400() {
		ErrorMessage responseBody = testClient
				.patch()
				.uri("/api/v1/users/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("123456", "123456", "000000"))
				.exchange()
				.expectStatus().isEqualTo(400)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
		
		responseBody = testClient
				.patch()
				.uri("/api/v1/users/100")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserPasswordDto("000000", "123456", "123456"))
				.exchange()
				.expectStatus().isEqualTo(400)
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

	}

	@Test
	public void findUsers_WithoutAnyParams_ReturnListOfUsersWithStatus200() {
		List<UserResponseDto> responseBody = testClient
				.get()
				.uri("/api/v1/users")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(UserResponseDto.class)
				.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
	}
	
}
