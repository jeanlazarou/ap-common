/*
 * @author: Jean Lazarou
 * @date: Aug 1, 2002
 */
package com.ap.io.zip;

import java.io.*;

import java.util.zip.*;

public class FilesZipper
{
    static final int sChunk = 8192;

	public static void zip (File files[], OutputStream out) throws IOException {
    	
        ZipOutputStream zipout = new ZipOutputStream(out);

		zip(files, zipout);

        zipout.close();
    }

	public static void zip (File files[], ZipOutputStream zipout) throws IOException {

		byte[] buffer = new byte[sChunk];

		int n = files.length;

		for (int i = 0; i < n; ++i) {
			if (files[i].isDirectory()) {
				zipFolder(files[i], zipout, buffer, "");
			} else {
				zipFile(files[i], zipout, buffer, "");
			}
		}
		
	}

    static void zipFolder(File dir, ZipOutputStream out, byte[] buffer, String relativePath) throws IOException {

		relativePath += dir.getName() + "/";

		ZipEntry entry = new ZipEntry(relativePath);
		
		entry.setTime(dir.lastModified());
		
		out.putNextEntry(entry);
		out.closeEntry();

        File[] content = dir.listFiles();

        int n = content.length;

        for (int i = 0; i < n; ++i) {

            if (content[i].isDirectory()) {
                zipFolder(content[i], out, buffer, relativePath);
            } else {
                zipFile(content[i], out, buffer, relativePath);
            }
        }
    }

    static void zipFile(File file, ZipOutputStream out, byte[] buffer, String relativePath) throws IOException {
        int length;

        //System.err.println(relativePath + file.getName());
        ZipEntry entry = new ZipEntry(relativePath + file.getName());

        entry.setTime(file.lastModified());

        out.putNextEntry(entry);

        FileInputStream in = new FileInputStream(file);

        while ((length = in.read(buffer, 0, sChunk)) != -1) {
            out.write(buffer, 0, length);
        }

        in.close();

        out.closeEntry();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage is : FilesZipper ZipOutputFile file1 file2 ...");
            System.err.println("           where file<i> can be a file or a directory");
            System.exit(1);
        }

        File out = new File(args[0]);

        File[] list = new File[args.length - 1];

        for (int i = 1; i < args.length; ++i) {
            list[i - 1] = new File(args[i]);
        }

        try {
            zip(list, new FileOutputStream(out));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
