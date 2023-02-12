package com.hoaxify.hoaxify;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import shared.GenericResponse;
import user.User;
import user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class UserControllerTest {

	private static final String API_1_0_USERS = "/api/1.0/users";
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	@Before
	public void cleanup() {
		userRepository.deleteAll();
	}
	
	@Test
	public void postUser_whenUserIsValid_recieveOk() {
		User user = createValidMethod();
		ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	
	@Test
	public void postUser_whenUserIsValid_userSavedToDatabase() {
		User user = createValidMethod();
		testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
		assertThat(userRepository.count()).isEqualTo(1);
	}
	
	
	@Test
	public void postUser_whenUserIsValid_recieveMessage() {
		User user = createValidMethod();
		ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
		assertThat(response.getBody().getMessage()).isNotNull();
	}
	
	private User createValidMethod() {
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("P4ssword");
		return user;
	}
}
