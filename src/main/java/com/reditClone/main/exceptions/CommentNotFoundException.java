package com.reditClone.main.exceptions;

public class CommentNotFoundException extends RuntimeException{
	public CommentNotFoundException(String message) {
		super(message);
	}
}
