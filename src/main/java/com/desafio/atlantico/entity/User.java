package com.desafio.atlantico.entity;

import java.io.Serializable;

import javax.naming.Name;
import javax.validation.constraints.NotNull;

import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entry(objectClasses =  {"inetOrgPerson", "organizationalPerson", "person", "top" }, base = "ou=Users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2147562739760713408L;

	@Id
	@JsonIgnore
	private Name dn;
	
	@NotNull(message = "Uid is a required field")
	@ApiModelProperty(required = true)
	private String uid;
	
	@DnAttribute(value = "cn", index = 1)
	@NotNull(message = "cn is a required field")
	@ApiModelProperty(required = true)
	private String cn;

	@NotNull(message = "sn is a required field")
	@ApiModelProperty(required = true)
	private String sn;

	
	public User() {
//		this.dn = LdapUtils.emptyLdapName();
	}
	
	public Name getDn() {
		return dn;
	}

	public void setDn(Name dn) {
		this.dn = dn;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	
}
