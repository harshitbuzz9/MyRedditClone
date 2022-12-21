package com.reditClone.main.exceptions;

public class PostNotFoundException extends RuntimeException{
	public PostNotFoundException(String message) {
		super(message);
	}
}
