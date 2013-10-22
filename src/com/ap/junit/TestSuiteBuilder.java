/*
 * Created on 21-mai-2005
 */
package com.ap.junit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Jean Lazarou
 */
public class TestSuiteBuilder {

	TestSuite suite;
	
	Map subsuites = new HashMap();
	
   	/**
	 * Constructs a test suite builder that creates an unnamed suite
	 */
	public TestSuiteBuilder() {
		suite = new TestSuite();
	}

   	/**
	 * Constructs a test suite builder that create a suite with the given name
	 * 
	 * @param suiteName the name of suite that the builder creates
	 */
	public TestSuiteBuilder(String suiteName) {	
		suite = new TestSuite(suiteName);
	}
	
	/**
	 * Creates the test suite
	 * 
	 * @return a <code>TestSuite</code>
	 */
	public TestSuite create() {
		return suite;
	}
	
	/**
	 * All the tests from the given class will be added to
	 * the test suite 
	 * 
	 * @param testClass the test class
	 */
	public void addTestsFrom(Class testClass) {
		suite.addTestSuite(testClass);
	}
	
	/**
	 * The static <code>suite</code> method of the given class
	 * is going to be used to get the test suite to add to
	 * the one created by this builder
	 * 
	 * @param suiteClass the test suite class
	 */
	public void addTestSuiteFrom(Class suiteClass) {
		
		try {
			
			Method suiteMethod = suiteClass.getDeclaredMethod("suite", new Class[0]);
		
			Object test = suiteMethod.invoke(null, null);
			
			if (test instanceof Test) {
				suite.addTest((Test) test);
			} else {
				throw new RuntimeException(suiteClass.getName()+ ".suite() didn't return a " + Test.class.getName()
				                           + " but a " + test.getClass().getName());
			}
			
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * The given fixture will be used to find a registered conformance
	 * test class and will be added to the test suite created by this
	 * builder.
	 * <p>
	 * <code>fixtureClass</code> must be a class that implements one registerred
	 * fixture interface
	 * 
	 * @param fixtureClass the fixture class used by some registerd conformance test
	 * @throws IllegalArgumentException if the fixture is an interface or if instance creation fails
	 */
	public void addConformanceTestsFor(Class fixtureClass) {
		
		if (fixtureClass.isInterface()) {
			throw new IllegalArgumentException("Cannot create an instance of " + fixtureClass.getName() + ", appears to be an interface");
		}
	
		Object fixture = null;
		
		try {
			fixture = fixtureClass.newInstance();
		} catch (InstantiationException e) {
			cannotCreateInstanceException(fixtureClass, e);
		} catch (IllegalAccessException e) {
			cannotCreateInstanceException(fixtureClass, e);
		}
		
		Class[] interfaces = fixtureClass.getInterfaces();
		
		for (int i = 0; i < interfaces.length; i++) {
		
			Class iface = interfaces[i];
			
			if (fixtures.containsKey(iface)) {
				
				Test test = newConformanceTest(fixture, iface);
				
				if (subsuites.containsKey(iface)) {
					
					TestSuite parent = (TestSuite) subsuites.get(iface);
					
					// maybe the fixture/conformance test registration has changed
					if(test instanceof TestSuite) {
						
						Enumeration it = ((TestSuite) test).tests();
						
						while (it.hasMoreElements()) {
							
							Test t = (Test) it.nextElement();
							
							parent.addTest(t);
							
						}
					}
					
				} else {
					
					suite.addTest((Test) test);
					
					if (test instanceof TestSuite) {
						subsuites.put(iface, test);
					}
					
				}
				
			}
			
		}
		
	}

	/**
	 * Register a fixture interface that can be used to create a suite instance
	 * beeing an instance of the given conformance tests class.
	 * <p>
	 * The <code>conformanceTests</code> class is a <code>TestSuite</code> and
	 * must have a constructor with one parameter of the <code>fixtureInterface</code>
	 * interface
	 * 
	 * @param fixtureInterface the fixture interface class to register
	 * @param conformanceTests the conformance tests class that uses the given fixture
	 */
	public static synchronized void registerFixture(Class fixtureInterface, Class conformanceTests) {
		fixtures.put(fixtureInterface, conformanceTests);
	}
	
	/**
	 * Deregister the given fixture interface
	 * 
	 * @param fixtureInterface the interface to deregister
	 */
	public static synchronized void deregisterFixture(Class fixtureInterface) {
		fixtures.remove(fixtureInterface);
	}
    
	/**
     * Retrieves an Iterator with all of the currently registerred fixture
     * interfaces
     *
     * @return the list of the fixture interfaces
	 */
	public static synchronized Iterator getFixtures() {
		return fixtures.keySet().iterator();
    }
	

	private Test newConformanceTest(Object fixture, Class iface) {

		Object test = null;
		
		Class conformanceTests = (Class) fixtures.get(iface);
		
		try {
			
			Constructor ctr = conformanceTests.getConstructor(new Class[] {iface});
			
			test = ctr.newInstance(new Object[] {fixture});
			
			if (!(test instanceof Test)) {
				throw new ClassCastException("Registered conformance tests class " + conformanceTests.getName()
						+ " does not implement the " + Test.class.getName() + " interface");
			}
			
		} catch (SecurityException e) {
			cannotCreateInstanceException(conformanceTests, e);
		} catch (NoSuchMethodException e) {
			cannotCreateInstanceException(conformanceTests, e);
		} catch (IllegalArgumentException e) {
			cannotCreateInstanceException(conformanceTests, e);
		} catch (InstantiationException e) {
			cannotCreateInstanceException(conformanceTests, e);
		} catch (IllegalAccessException e) {
			cannotCreateInstanceException(conformanceTests, e);
		} catch (InvocationTargetException e) {
			cannotCreateInstanceException(conformanceTests, e);
		}

		return (Test) test;
		
	}

	private void cannotCreateInstanceException(Class conformanceTests, Exception e) {
		throw new IllegalArgumentException("Cannot create instance of " + conformanceTests.getName() + " [" + e.getMessage() + "]");
	}

	static Map fixtures = new HashMap();
	
}
