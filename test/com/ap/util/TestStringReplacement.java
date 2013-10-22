/*
 * Created on 09-avr.-2005
 */
package com.ap.util;

import junit.framework.TestCase;

/**
 * @author Jean Lazarou
 */
public class TestStringReplacement extends TestCase {

    public void testSearchStringNotFound() {
        
        String result = Strings.replace("Hello World", "No", "Byebye", -1);
        
        assertEquals("Hello World", result);
        
    }

    public void testReplace() {
        
        String result = Strings.replace("One On None", "on", "x", 1);
        
        assertEquals("One On Nxe", result);
        
    }

    public void testReplaceOne() {
        
        String result = Strings.replace("one on None", "on", "x", 1);
        
        assertEquals("xe on None", result);
        
    }

    public void testReplaceTwo() {
        
        String result = Strings.replace("one on None", "on", "x", 2);
        
        assertEquals("xe x None", result);
        
    }

    public void testReplaceAll() {
        
        String result = Strings.replace("one on None", "on", "x", -1);
        
        assertEquals("xe x Nxe", result);
        
    }

    public void testSearchStringAtTheEnd() {
        
        String result = Strings.replace("one on Non", "on", "x", -1);
        
        assertEquals("xe x Nx", result);
        
    }
    
}
