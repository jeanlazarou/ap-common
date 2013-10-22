/*
 * @author: Jean Lazarou
 * @date: April 9, 2004
 */
package com.ap.util;

import java.util.List;

import junit.framework.TestCase;

public class TestCSVStringToList extends TestCase {

	public TestCSVStringToList(String name) {
		super(name);
	}
	
	public void testNoQuotes() {
		List res = Lists.csvSplit("Hello World, Hey!, 778.9");
		
		assertEquals("Hello World", res.get(0));
		assertEquals("Hey!", res.get(1));
		assertEquals("778.9", res.get(2));

		res = Lists.csvSplit("Hello World,Hey!,778.9");
		
		assertEquals("Hello World", res.get(0));
		assertEquals("Hey!", res.get(1));
		assertEquals("778.9", res.get(2));
	}
	
	public void testWithQuotes() {
		List res = Lists.csvSplit("\"Hello World\", \"Hey!\", \"778.9\"");
		
		assertEquals("Hello World", res.get(0));
		assertEquals("Hey!", res.get(1));
		assertEquals("778.9", res.get(2));

		res = Lists.csvSplit("\"Hello World\",\"Hey!\",\"778.9\"");
		
		assertEquals("Hello World", res.get(0));
		assertEquals("Hey!", res.get(1));
		assertEquals("778.9", res.get(2));
	}
	
	public void testMixedSeparators() {
		List res = Lists.csvSplit("\"Hello World\", Hey!, \"778.9\"");
		
		assertEquals("Hello World", res.get(0));
		assertEquals("Hey!", res.get(1));
		assertEquals("778.9", res.get(2));

		res = Lists.csvSplit("Hello World, Hey!, \"778.9\"");
		
		assertEquals("Hello World", res.get(0));
		assertEquals("Hey!", res.get(1));
		assertEquals("778.9", res.get(2));
	}
	
	public void testExplicitNull() {
		List res = Lists.csvSplit("\"Hello World\", null, Null, 12, NULL", true);
		
		assertEquals("Hello World", res.get(0));
		assertEquals(null, res.get(1));
		assertEquals(null, res.get(2));
		assertEquals("12", res.get(3));
		assertEquals(null, res.get(4));
	}
	
	public void testImplicitNull() {
		List res = Lists.csvSplit("\"Hello World\", , ", true);
		
		assertEquals("Hello World", res.get(0));
		assertEquals(null, res.get(1));
		assertEquals(null, res.get(2));
	}
	
	public void testNullAsString() {
		List res = Lists.csvSplit("\"Hello World\",, \"null\", null", true);
		
		assertEquals("Hello World", res.get(0));
		assertEquals(null, res.get(1));
		assertEquals("null", res.get(2));
		assertEquals(null, res.get(3));

		res = Lists.csvSplit("\"Hello World\",, \"null\", null", false);
		
		assertEquals("Hello World", res.get(0));
		assertEquals("", res.get(1));
		assertEquals("null", res.get(2));
		assertEquals(null, res.get(3));
	}

	public void testEscapedString() {
		
		List res = Lists.csvSplit("Hello \\\"World, \"Hey \\\"java\\\"\", Hello World!");
		
		assertEquals("Hello \"World", res.get(0));
		assertEquals("Hey \"java\"", res.get(1));
		assertEquals("Hello World!", res.get(2));
		
		res = Lists.csvSplit("Hello \"\"World\"\", Hey \"\"java\"\", \"Hello World!\"");
		
		assertEquals("Hello \"World\"", res.get(0));
		assertEquals("Hey \"java\"", res.get(1));
		assertEquals("Hello World!", res.get(2));
		
		res = Lists.csvSplit("Hello \"\"World, Hey \"\"java\"\", \"Hello World!\"");
		
		assertEquals("Hello \"World", res.get(0));
		assertEquals("Hey \"java\"", res.get(1));
		assertEquals("Hello World!", res.get(2));
		
		res = Lists.csvSplit("Hello \"\"World, \"Hey \"\"java\"\"\", Hello World!");
		
		assertEquals("Hello \"World", res.get(0));
		assertEquals("Hey \"java\"", res.get(1));
		assertEquals("Hello World!", res.get(2));
		
	}

}
