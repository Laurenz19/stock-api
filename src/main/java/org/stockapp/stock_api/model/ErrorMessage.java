package org.stockapp.stock_api.model;

public class ErrorMessage {
	private int statusCode;
	private String Message;
	
	public ErrorMessage(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		Message = message;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	
}
