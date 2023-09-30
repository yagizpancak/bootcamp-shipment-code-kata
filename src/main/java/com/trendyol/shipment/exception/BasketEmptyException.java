package com.trendyol.shipment.exception;

public class BasketEmptyException extends RuntimeException{
	private static final String MESSAGE = "Basket is empty!";

	public BasketEmptyException(){
		super(MESSAGE);
	}
}
