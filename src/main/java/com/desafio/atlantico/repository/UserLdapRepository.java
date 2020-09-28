package com.desafio.atlantico.repository;

import java.util.Optional;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import com.desafio.atlantico.entity.User;

@Repository
public interface UserLdapRepository extends LdapRepository<User>{
	
	Optional<User> findByUid(String uid);
	

}
