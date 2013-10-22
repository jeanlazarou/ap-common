/*
 * @author: Jean Lazarou
 * @date: April 9, 2004
 */
package com.ap;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
    public static Test suite() {
        TestSuite suite= new TestSuite("com.ap.common Tests");

		suite.addTest(com.ap.util.AllTests.suite());
		suite.addTest(com.ap.parser.AllTests.suite());

        return suite;
    }
}
