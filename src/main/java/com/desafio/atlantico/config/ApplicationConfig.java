package com.desafio.atlantico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableLdapRepositories(basePackages = "com.desafio.atlantico.repository")
public class ApplicationConfig {

	@Bean
	ContextSource contextSource() {
		
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://127.0.0.1:389");
		ldapContextSource.setBase("dc=techinterview,dc=com");
		ldapContextSource.setUserDn("cn=admin,dc=techinterview,dc=com");
		ldapContextSource.setPassword("123456");
		
		return ldapContextSource;
	}
	
	@Bean
	LdapTemplate ldapTemplate(ContextSource contextSource) {
		return new LdapTemplate(contextSource);
	}
	
}
