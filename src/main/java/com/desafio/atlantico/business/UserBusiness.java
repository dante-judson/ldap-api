package com.desafio.atlantico.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.atlantico.business.exception.ResourceNotFoundException;
import com.desafio.atlantico.entity.User;
import com.desafio.atlantico.repository.UserLdapRepository;

@Service
public class UserBusiness {
	

	@Autowired
	private UserLdapRepository userLdapRepository;

	public Iterable<User> findAll() {
		return this.userLdapRepository.findAll();
	}
	
	public User createUser(User user) {
		return this.userLdapRepository.save(user);
	}
	
	public User findByUid(String uid) throws ResourceNotFoundException{
		return this.userLdapRepository.findByUid(uid)
		.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	public void deleteUser(String uid) throws ResourceNotFoundException {
		this.userLdapRepository.delete(this.findByUid(uid));
	}
	
}
