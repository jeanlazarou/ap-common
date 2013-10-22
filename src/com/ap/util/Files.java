/*
 * @author: Jean Lazarou
 * @date: March 11, 2004
 */
package com.ap.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class Files {

	private Files() {}

	public static String getExtension(File file) {
		
		int i = file.getName().lastIndexOf('.');
		
		if (i != -1) {
			return file.getName().substring(i + 1);
		}
		
		return file.getName();
		
	}
	
	public static String read(File f) throws IOException {
		
		Reader reader = null;
		
		try {
			
			reader = new FileReader(f);
			
			char[] buffer = new char[1024];
			StringBuffer content = new StringBuffer();
			
			int read = 0;
			
			while ((read = reader.read(buffer)) != -1) {
				
				content.append(buffer, 0, read);
				
			}
			
			return content.toString(); 

		} finally {
			if (reader != null) reader.close();
		}
	}
	
	public static void write(String content, File f) throws IOException {
		FileWriter out = new FileWriter(f);
		out.write(content); 
		out.flush();
		out.close();
	}
		
	/**
	 * Copies the source file to the destination file. Is source is a directory,
	 * a recursive copy is made.
	 * 
	 * @param src file to copy
	 * @param dest copy file to create
	 * @throws IOException can occur as file io is made 
	 */
	public static void deepCopy(File src, File dest) throws IOException {
		
		if (src.isDirectory()) {
			
			dest.mkdirs();
			
			File[] files = src.listFiles();
			
			for (int i = 0; i < files.length; i++) {
				deepCopy(files[i], new File(dest, files[i].getName()));
			}
			
		} else {
			copy(src, dest);
		}
		
	}
		
	/**
	 * a recursive copy is made.
	 * 
	 * @param src file to copy
	 * @param dest copy file to create
	 * @param exclude filter to exclude files from the copy
	 * @throws IOException can occur as file io is made 
	 */
	public static void deepCopy(File src, File dest, FileFilter exclude) throws IOException {
		
		if (src.isDirectory()) {
			
			dest.mkdirs();
			
			File[] files = src.listFiles(exclude);
			
			for (int i = 0; i < files.length; i++) {
				deepCopy(files[i], new File(dest, files[i].getName()), exclude);
			}
			
		} else {
			copy(src, dest);
		}
		
	}
	
	/**
	 * Copies the source file to the destination file.
	 * The source file is not expected to be a directory.
	 * 
	 * @param src file to copy
	 * @param dest copy file to create
	 * @throws IOException can occur as file io is made 
	 */
	public static void copy(File src, File dest) throws IOException {
		
		int read;
		
		byte[] buffer = new byte[1024];
		
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);
		
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		
		in.close();
		out.close();
		
	}
	
	public static void deleteFolder(File dir) {

		if (dir.exists()) {
			
			File[] file = dir.listFiles();
			
			if (file != null) {
			
				for (int i = 0; i < file.length; i++) {
					
					if (file[i].isDirectory()) {
						deleteFolder(file[i]);
					} else {
						file[i].delete();
					}
					
				}
			
			}
			
			dir.delete();
			
		}
		
	}

}
