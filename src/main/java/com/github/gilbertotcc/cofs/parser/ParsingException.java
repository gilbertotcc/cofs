package com.github.gilbertotcc.cofs.parser;

public class ParsingException extends RuntimeException {
	
	private static final long serialVersionUID = -6700257149290510006L;

	public enum ErrorEnums {
		MALFORMED_ADD_USER(1, "Malformed add user expression"),
		MALFORMED_ADD_TRANSACTION(2, "Malformed add transaction expression"),
		MALFORMED_CREDIT(3, "Malformed credit expression");
		
		private int errorCode;
		private String errorMessage;
		
		private ErrorEnums(int code, String message) {
			this.errorCode = code;
			this.errorMessage = message;
		}
		
		public int code() {
			return errorCode;
		}
		
		public String message() {
			return errorMessage;
		}
	}
	
	private final ErrorEnums error;
	
	public ParsingException(ErrorEnums error, Throwable cause) {
		super(cause);
		this.error = error;
	}
	
	public int getErrorCode() {
		return error.errorCode;
	}
	
	public String getErrorMessage() {
		return error.errorMessage;
	}
}
