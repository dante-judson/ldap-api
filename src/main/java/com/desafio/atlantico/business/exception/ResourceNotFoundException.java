package com.desafio.atlantico.business.exception;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 4712295616737339871L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
