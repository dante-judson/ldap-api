package com.desafio.atlantico;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.atlantico.business.UserBusiness;
import com.desafio.atlantico.business.exception.ResourceNotFoundException;
import com.desafio.atlantico.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserBusiness mockedUserBusiness;
	
	
	@Test
	void getAllUsersTest() throws Exception{
		Iterable<User> returnedUsers = new ArrayList<User>();
		when(mockedUserBusiness.findAll())
		.thenReturn(returnedUsers);
		
		mockMvc.perform(get("/user"))
		.andDo(print())
		.andExpect(status().isOk());

	}
	
	@Test
	void createNullUserTest() throws Exception {
		User user = new User();
		when(mockedUserBusiness.createUser(user))
		.thenReturn(user);
		
		mockMvc.perform(post("/user").content(asJsonString(user)).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void createUserTest() throws Exception {
		User user = new User();
		
		user.setCn("anystring");
		user.setUid("anystring");
		user.setSn("anystring");
		
		when(mockedUserBusiness.createUser(any()))
		.thenReturn(user);
		
		mockMvc.perform(post("/user").content(asJsonString(user)).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	@Test
	void createUserAlreadyExistsTest() throws Exception {
		User user = new User();
		
		user.setCn("anystring");
		user.setUid("anystring");
		user.setSn("anystring");
		
		when(mockedUserBusiness.createUser(any()))
		.thenThrow(new NameAlreadyBoundException(
				new javax.naming.NameAlreadyBoundException("Name already Exists")));
		
		mockMvc.perform(post("/user").content(asJsonString(user)).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void getUserByUid() throws Exception {
		when(mockedUserBusiness.findByUid(anyString()))
		.thenReturn(new User());
		
		mockMvc.perform(get("/user/anyuid"))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	void getUserByUidNotFound() throws Exception {
		when(mockedUserBusiness.findByUid(anyString()))
		.thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/user/anyuid"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	void deleteUserByUid() throws Exception {		

		doNothing().when(mockedUserBusiness).deleteUser(anyString());
		mockMvc.perform(delete("/user/anyuid"))
		.andDo(print())
		.andExpect(status().isNoContent());
	}
	
	@Test
	void deleteUserByUidNotFound() throws Exception {
		doThrow(ResourceNotFoundException.class).when(mockedUserBusiness).deleteUser(anyString());
		mockMvc.perform(delete("/user/anyuid"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	
	private static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);	
	}

}
