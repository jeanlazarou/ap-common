package com.ap.parser;

/**
 * @author Jean Lazarou
 */
public class SyntaxError extends RuntimeException {
	
	public SyntaxError(String message, int line, String code, int position) {
		super(createErrorMessage(message, line, code, position));
		
		this.code = code;
		this.line = line;
		this.position = position;
	}
	
	public SyntaxError(String message, int line, String code, int position, Throwable cause) {
		super(createErrorMessage(message, line, code, position), cause);
		
		this.code = code;
		this.line = line;
		this.position = position;
	}
	
	public String getCode() {
		return code;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getPosition() {
		return position;
	}
	
	private static String createErrorMessage(String message, int line, String code, int column) {
	
		StringBuffer sb = new StringBuffer(message.length() + code.length());
		
		sb.append(message);
		sb.append(" at ");
		sb.append(line);
		sb.append("\n        ");
		sb.append(code);
		sb.append("\n        ");
		
		for (int i = 0; i < column; i++) {
			sb.append(' ');
		}

		sb.append('^');
		
		return sb.toString();
		
	}
	
	int line;
	int position;
	String code;

}
