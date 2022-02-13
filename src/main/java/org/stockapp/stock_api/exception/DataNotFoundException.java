package org.stockapp.stock_api.exception;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6133911008707896986L;
	
	public DataNotFoundException(String message) {
		super(message);
	}

}
