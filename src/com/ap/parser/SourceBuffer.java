package com.ap.parser;

import java.io.PrintStream;
import java.util.Stack;

public class SourceBuffer {
	
	public SourceBuffer() {
		source = "";
	}
	
	public SourceBuffer(String source) {
		
		this.source = source;
		
		buffer = source.toCharArray();
		
	}

	public void add(String text) {
		source = source + text;
		buffer = source.toCharArray();
	}

	public void addLine() {
		add("\n");
	}

	public void addLine(String line) {
		add(line + "\n");
	}
	
	public char next() {
		
		if (position >= buffer.length) return '\0';
		
		char c = buffer[position++];
		
		if (c == '\r') {
			
			c = '\n';

			if (position < buffer.length) {
				
				if (buffer[position] == '\n') {
					position++;
				}
				
			}

		}

		if (debug != null) {
			
			debug.print(this.getClass().getName() + ".get() -> 0x" + Integer.toHexString(c).toUpperCase() + " - '");
			
			if (c == '\n')
				debug.print("\\n");
			else if (c == '\t')
				debug.print("\\t");
			else if (c == '\r')
				debug.print("\\r");
			else if (c == ' ')
				debug.print("<space>");
			else
				debug.print(c);

			debug.println("' [" + (position-1) + "]");
			
		}

		return c;
		
	}

	public void unget() {

		position--;
		
		if (position < 0) position = 0;

		if (debug != null) debug.println(this.getClass().getName() + ".unget() [" + position + "]");
		
	}
	
	public boolean isEOF() {
		return position >= buffer.length;
	}
	
	/*
	 * Saves the current position in the buffer
	 */
	public void save() {
	
		if (saves == null) {
			saves = new Stack();
		}
		
		saves.push(new Integer(position));
		
	}
	
	/*
	 * Restores the last saved position
	 */
	public void restore() {
		
		if (saves == null || saves.isEmpty()) {
			throw new ParserException("Unmatched call to restore");
		}
		
		position = ((Integer) saves.pop()).intValue();
		
		
	}

	/**
	 * Set the <code>PrintStream</code> to use as output for debugging.
	 * If set to null, generates no debug information 
	 * 
	 * @param debug the output stream
	 */
	public void setDebug(PrintStream debug) {
		this.debug = debug;
	}
	
	public void dump(PrintStream out) {
		out.print(source);
	}
	
	public SyntaxError createException(String message) {
		return createException(message, null);
	}
	
	public SyntaxError createException(String message, Throwable cause) {
		
		int line = 0;
		int column = 0;
		
		int i = 0;
		int startOfLine = 0;
		
		for (; i < buffer.length; i++) {

			char c = buffer[i];
			
			if (i == position) {
				column = i - startOfLine;
			}

			if (c == '\n') {
				
				if (i > 0 && buffer[i - 1] != '\r') {
					
					if (i >= position) break;

					line++;
					startOfLine = i;

				}
				
			} else if (c == '\r') {
			
				if (i >= position) break;

				line++;
				startOfLine = i;

			}
			
		}

		String code = source.substring(startOfLine, i).trim();
		
		if (position >= buffer.length) {
			column = code.length();
		}
		
		if (cause == null) {
			return new SyntaxError(message, line + 1, code, column);
		} else {
			return new SyntaxError(message, line + 1, code, column, cause);
		}

	}

	String source;
	char[] buffer;
	
	int position = 0;
	
	Stack saves;
	
	private PrintStream debug;
	
}
