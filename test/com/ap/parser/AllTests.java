/*
 * Created on 14-d�c.-2004
 */
package com.ap.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Jean Lazarou
 */
public class AllTests {

	public static Test suite() {
        
    	TestSuite suite= new TestSuite("Parser Tests");

		suite.addTestSuite(TestSourceBuffer.class);

        return suite;
    }

}
