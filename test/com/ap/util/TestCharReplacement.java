/*
 * Created on 09-avr.-2005
 */
package com.ap.util;

import junit.framework.TestCase;

/**
 * @author Jean Lazarou
 */
public class TestCharReplacement extends TestCase {

    public void testSearchStringNotFound() {
        
        String result = Strings.replace("Hello World", 'N', "Byebye", -1);
        
        assertEquals("Hello World", result);
        
    }

    public void testReplace() {
        
        String result = Strings.replace("One On None", 'o', "x", 1);
        
        assertEquals("One On Nxne", result);
        
    }

    public void testReplaceOne() {
        
        String result = Strings.replace("one on None", 'o', "x", 1);
        
        assertEquals("xne on None", result);
        
    }

    public void testReplaceTwo() {
        
        String result = Strings.replace("one on None", 'o', "x", 2);
        
        assertEquals("xne xn None", result);
        
    }

    public void testReplaceAll() {
        
        String result = Strings.replace("one on None", 'o', "x", -1);
        
        assertEquals("xne xn Nxne", result);
        
    }

    public void testReplaceLastCharacter() {
        
        String result = Strings.replace("one on No", 'o', "x", -1);
        
        assertEquals("xne xn Nx", result);
        
    }
    
}
