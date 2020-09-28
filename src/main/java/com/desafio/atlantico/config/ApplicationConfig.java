package com.desafio.atlantico.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableLdapRepositories(basePackages = "com.desafio.atlantico.repository")
public class ApplicationConfig {

	@Autowired
	private Environment env;
	
	@Bean
	ContextSource contextSource() {
		
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl(env.getProperty("app.ldap.url","ldap://127.0.0.1:389"));
		ldapContextSource.setBase(env.getProperty("app.ldap.base","dc=techinterview,dc=com"));
		ldapContextSource.setUserDn(env.getProperty("app.ldap.admindn","cn=admin,dc=techinterview,dc=com"));
		ldapContextSource.setPassword(env.getProperty("app.ldap.adminpassword","123456"));
		
		
		return ldapContextSource;
	}
	
	@Bean
	LdapTemplate ldapTemplate(ContextSource contextSource) {
		return new LdapTemplate(contextSource);
	}
	
}
