package com.ap.parser;

import com.ap.parser.SourceBuffer;
import com.ap.parser.SyntaxError;

import junit.framework.TestCase;

/**
 * @author Jean Lazarou
 */
public class TestSourceBuffer extends TestCase {
	
	public void testOneSaveRestore() {
		
		SourceBuffer buffer = new SourceBuffer("abcdef\nvwxyz");

		buffer.next();
		buffer.next();
		
		buffer.save();

		buffer.next();
		buffer.next();

		buffer.restore();

		assertEquals('c', buffer.next());
		
	}
	
	public void testSaveRestore() {
		
		SourceBuffer buffer = new SourceBuffer("abcdef\nvwxyz");

		buffer.next();
		buffer.next();
		
		buffer.save();

		buffer.next();
		buffer.next();

		buffer.save();

		buffer.next();
		buffer.next();

		buffer.save();

		buffer.next();
		buffer.next();

		buffer.restore();
		buffer.restore();
		buffer.restore();

		assertEquals('c', buffer.next());
		
	}
	
	public void testUnget() {
		
		SourceBuffer buffer = new SourceBuffer("abcdef\nvwxyz");

		buffer.next();
		buffer.next();
		
		buffer.unget();

		assertEquals('b', buffer.next());

		buffer.next();
		buffer.next();
		
		buffer.unget();

		assertEquals('d', buffer.next());

		for (int i = 0; i < 15; i++) buffer.unget();

		assertEquals('a', buffer.next());
		
		for (int i = 0; i < 15; i++) buffer.next();
		
		buffer.unget();

		assertEquals('z', buffer.next());

	}

	public void testErrorAtBeginning() {
		
		SourceBuffer buffer = new SourceBuffer("line 1\nline 2");
		
		SyntaxError error = buffer.createException("<error>");
		
		assertEquals(1, error.getLine());
		assertEquals("line 1", error.getCode());
		assertEquals(0, error.getPosition());

		assertEquals("<error> at 1\n        line 1\n        ^", error.getMessage());

	}

	public void testErrorAtEnd() {
		
		SourceBuffer buffer = new SourceBuffer("line 1\nline 2");
		
		for (int i = 0; !buffer.isEOF(); i++) {
			buffer.next();
		}
		
		SyntaxError error = buffer.createException("<error>");
		
		assertEquals(2, error.getLine());
		assertEquals("line 2", error.getCode());
		assertEquals(6, error.getPosition());

		assertEquals("<error> at 2\n        line 2\n              ^", error.getMessage());

	}

	public void testErrorAtLine1() {
		
		SourceBuffer buffer = new SourceBuffer("line 1\nline 2");
		
		for (int i = 0; i < 3; i++) {
			buffer.next();
		}
		
		SyntaxError error = buffer.createException("<error>");
		
		assertEquals(1, error.getLine());
		assertEquals("line 1", error.getCode());
		assertEquals(3, error.getPosition());

		assertEquals("<error> at 1\n        line 1\n           ^", error.getMessage());

	}

	public void testErrorAtEndOfLine1() {
		
		SourceBuffer buffer = new SourceBuffer("line 1\nline 2");
		
		for (int i = 0; i < "line 1".length(); i++) {
			buffer.next();
		}
		
		SyntaxError error = buffer.createException("<error>");
		
		assertEquals(1, error.getLine());
		assertEquals("line 1", error.getCode());
		assertEquals(6, error.getPosition());

		assertEquals("<error> at 1\n        line 1\n              ^", error.getMessage());

	}

	public void testErrorAtLine2() {
		
		SourceBuffer buffer = new SourceBuffer("line 1\nline 2");
		
		for (int i = 0; i < "line 1".length() + 3; i++) {
			buffer.next();
		}
		
		SyntaxError error = buffer.createException("<error>");
		
		assertEquals(2, error.getLine());
		assertEquals("line 2", error.getCode());
		assertEquals(3, error.getPosition());

		assertEquals("<error> at 2\n        line 2\n           ^", error.getMessage());

	}

}
