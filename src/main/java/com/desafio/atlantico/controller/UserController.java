package com.desafio.atlantico.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.atlantico.business.UserBusiness;
import com.desafio.atlantico.business.exception.ResourceNotFoundException;
import com.desafio.atlantico.entity.User;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@RestController
public class UserController {
	
	@Autowired
	private UserBusiness userBusiness;
	
	@GetMapping(value = "user", produces = "application/json")
	@ApiResponses(value = {			
			@ApiResponse(code = 200, message = "It returns a list that contais all the users.")
	})
	public Iterable<User> findAll(){
		return this.userBusiness.findAll();
	}
	
	
	@PostMapping(value = "user", produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiResponses(value = {			
			@ApiResponse(code = 201, message = "Creates an user."),
			@ApiResponse(code = 400, message = "When the user in the request body does not contains all the required attributes.")
	})
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws NameAlreadyBoundException {
		User savedUser = this.userBusiness.createUser(user);
		return ResponseEntity.created(URI.create(String.format("/user/%s", savedUser.getUid()))).build();
	}
	
	@GetMapping(value = "user/{uid}", produces = "application/json")
	@ApiResponses(value = {			
			@ApiResponse(code = 200, message = "Find the user by the UID given."),
			@ApiResponse(code = 404, message = "When no user was found for the given uid.")
	})
	public ResponseEntity<User> findByUid(@PathVariable(name = "uid") String uid) throws ResourceNotFoundException {
		return ResponseEntity.ok(this.userBusiness.findByUid(uid));
		
	}
	
	@DeleteMapping(value = "user/{uid}", produces = "application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = {			
			@ApiResponse(code = 204, message = "Delete the user by the UID given."),
			@ApiResponse(code = 404, message = "When no user was found for the given uid.")
	})
	public ResponseEntity<User> deleteUser(@PathVariable(name = "uid") String uid) throws ResourceNotFoundException {
		this.userBusiness.deleteUser(uid);
		return ResponseEntity.noContent().build();
	}
	
}
