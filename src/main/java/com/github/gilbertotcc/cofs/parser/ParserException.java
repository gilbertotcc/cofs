package com.github.gilbertotcc.cofs.parser;

public class ParserException extends RuntimeException {
	
	private static final long serialVersionUID = -6700257149290510006L;
	
	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ParserException(String message) {
		super(message);
	}
}
