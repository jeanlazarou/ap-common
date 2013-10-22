/*
 * @author Jean Lazarou
 * Date: April 9, 2002
 */
package com.ap.util;

import junit.framework.*;

public class AllTests
{
    public static Test suite ()
    {
		TestSuite suite= new TestSuite("Util package");

		suite.addTest(new TestSuite(TestCSVStringToList.class));
		suite.addTest(new TestSuite(TestStringReplacement.class));
		suite.addTest(new TestSuite(TestCharReplacement.class));

        return suite;
	}
}
