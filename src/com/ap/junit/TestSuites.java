/*
 * @author: Jean Lazarou
 * @date: April 13, 2004
 */
package com.ap.junit;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestSuites {
	
	public static void addTestMethods(TestSuite parent, String suiteName, Class theTestClass, Class paramType, Object param) {
		addTestMethods(parent, suiteName, theTestClass, 
	              new Class[] {paramType},
				  new Object[]{param});
	}
	
	public static void addTestMethods(TestSuite parent, String suiteName, Class theTestClass, Class[] paramTypes, Object[] args) {

		TestSuite suite = new TestSuite(suiteName);
			
		Constructor constructor = null;

		Class[] types = new Class[paramTypes.length + 1];
		
		System.arraycopy(paramTypes, 0, types, 0, paramTypes.length);
		
		types[paramTypes.length] = String.class;
		
		Object[] values = new Object[paramTypes.length + 1];
		
		System.arraycopy(args, 0, values, 0, paramTypes.length);
				
		try {	
			constructor = theTestClass.getConstructor(types);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		Method[] methods = theTestClass.getDeclaredMethods();

		for (int i= 0; i < methods.length; i++) {
			
			if (isTestMethod(methods[i])) {
				try {
					values[paramTypes.length] = methods[i].getName();
					suite.addTest((Test)constructor.newInstance(values));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		
		parent.addTest(suite);
		
	}

	public static void addTestMethods(TestSuite parent, Class theTestClass, Class paramType, Object param) {
		addTestMethods(parent, theTestClass, 
	              new Class[] {paramType},
				  new Object[]{param});
	}

	public static void addTestMethods(TestSuite parent, Class theTestClass, 
			                                            Class paramType1, Object param1, 
			                                            Class paramType2, Object param2) {
		addTestMethods(parent, theTestClass, 
				              new Class[] {paramType1, paramType2},
							  new Object[] {param1, param2});
		
	}

	public static void addTestMethods(TestSuite parent, Class theTestClass, 
				  Class paramType1, Object param1, 
				  Class paramType2, Object param2, 
				  Class paramType3, Object param3) {

			addTestMethods(parent, theTestClass, 
							new Class[] {paramType1, paramType2, paramType3},
							new Object[] {param1, param2, param3});
	
		}

	public static void addTestMethods(TestSuite parent, Class theTestClass, Class[] paramTypes, Object[] args) {

		TestSuite suite = new TestSuite();
			
		suite.setName(theTestClass.getName());
		
		Constructor constructor = null;

		Class[] types = new Class[paramTypes.length + 1];
		
		System.arraycopy(paramTypes, 0, types, 0, paramTypes.length);
		
		types[paramTypes.length] = String.class;
		
		Object[] values = new Object[paramTypes.length + 1];
		
		System.arraycopy(args, 0, values, 0, paramTypes.length);
				
		try {	
			constructor = theTestClass.getConstructor(types);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		Method[] methods = theTestClass.getDeclaredMethods();

		for (int i= 0; i < methods.length; i++) {
			
			if (isTestMethod(methods[i])) {
				try {
					values[paramTypes.length] = methods[i].getName();
					suite.addTest((Test)constructor.newInstance(values));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		
		parent.addTest(suite);
		
	}

	//////////
	
	public static void addInheritedTestMethods(TestSuite parent, Class theTestClass, Class paramType, Object param) {
		addInheritedTestMethods(parent, theTestClass, 
	              new Class[] {paramType},
				  new Object[]{param});
	}

	public static void addInheritedTestMethods(TestSuite parent, Class theTestClass, 
			                                            Class paramType1, Object param1, 
			                                            Class paramType2, Object param2) {
		addInheritedTestMethods(parent, theTestClass, 
				              new Class[] {paramType1, paramType2},
							  new Object[] {param1, param2});
		
	}

	public static void addInheritedTestMethods(TestSuite parent, Class theTestClass, 
				  Class paramType1, Object param1, 
				  Class paramType2, Object param2, 
				  Class paramType3, Object param3) {

		addInheritedTestMethods(parent, theTestClass, 
							new Class[] {paramType1, paramType2, paramType3},
							new Object[] {param1, param2, param3});
	
		}

	public static void addInheritedTestMethods(TestSuite parent, Class theTestClass, Class[] paramTypes, Object[] args) {

		TestSuite suite = new TestSuite();
			
		suite.setName(theTestClass.getName());
		
		Constructor constructor = null;

		Class[] types = new Class[paramTypes.length + 1];
		
		System.arraycopy(paramTypes, 0, types, 0, paramTypes.length);
		
		types[paramTypes.length] = String.class;
		
		Object[] values = new Object[paramTypes.length + 1];
		
		System.arraycopy(args, 0, values, 0, paramTypes.length);
				
		try {	
			constructor = theTestClass.getConstructor(types);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		Method[] methods = theTestClass.getMethods();

		for (int i= 0; i < methods.length; i++) {
			
			if (isTestMethod(methods[i])) {
				try {
					values[paramTypes.length] = methods[i].getName();
					suite.addTest((Test)constructor.newInstance(values));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		
		parent.addTest(suite);
		
	}
	
	public static boolean isTestMethod(Method m) {
		String name = m.getName();
		Class[] parameters = m.getParameterTypes();
		Class returnType = m.getReturnType();
		
		return parameters.length == 0 && name.startsWith("test") && returnType.equals(Void.TYPE);
	 }

}
