package com.example.younghanapi1.exception;

public class NotEnoughStockException extends RuntimeException {

	public NotEnoughStockException(String message) {
		super(message);
	}
}
