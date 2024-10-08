package com.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.openqa.selenium.WebDriver;

public class FileUtils {
	
	private static final String PROPERTIES_FILE_PATH = "./src/main/resources/config.properties";
	
	public static boolean writeToFile(String fileContent, String filePath, String fileName) {
		Path path=Paths.get(filePath, fileName);
		byte[] strToBytes = fileContent.getBytes();
		try {
			Files.write(path, strToBytes);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return isFileExists(filePath + File.separator +fileName);
	}
	
	
	public static String readFile(String filePath, String fileName) throws IOException {
		Path path=Paths.get(filePath, fileName);
		return new String(Files.readAllBytes(path));
	}
	
	
	public static boolean isFileExists(String filePath) {
		File f = new File(filePath.toString());
		return f.exists();
	}
	
	
	public static boolean isDomFileExists(String domName) {
		Path resourcePath=Paths.get("src", "main", "resources", "pagedom");
		String absolutePath = resourcePath.toFile().getAbsolutePath();
		File f = new File(absolutePath + File.separator + domName + ".txt");
		return f.exists();
	}
	
	
	public static void copyFile(File file1, File file2) throws IOException {
	    try {
	    	InputStream in = new FileInputStream(file1);
	        OutputStream out = new FileOutputStream(file2);
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
	    } catch(IOException e) {
	        e.printStackTrace();
	        throw e;  // Re-throw the exception after logging it
	    }
	}
	
	
	public static void moveFile(String oldFilepath, String newFilePath) throws IOException {
		File oldFile = new File(oldFilepath);
		File newFile = new File(newFilePath);
		copyFile(oldFile, newFile);
		oldFile.delete();
	}
	
	public static String readFile(String fileName) throws IOException {
	    String text = "";
	    try {
	    	FileInputStream textFile = new FileInputStream(System.getProperty("user.dir") + fileName);
	        text = new String(textFile.readAllBytes(), StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        System.err.println("Something went wrong while reading the file: " + e.getMessage());
	    }
	    return text;
	}
	
	
	/* Folder utilities*/
	
	public static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws IOException {
		File folder = new File(srcFile);
		InputStream in = null;
		try {
			if(folder.isDirectory()) {
				addFileToZip(path, srcFile, zip);
			}else {
				byte[] buf = new byte[1024];
		        int len;
		        in = new FileInputStream(srcFile);
		        zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
		        while ((len=in.read(buf))>0) {
		        	zip.write(buf, 0, len);
		        }
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
	}
	
	
	public static void addFileToZipWithoutFolder(String path, String srcFile, ZipOutputStream zip) throws IOException {
		byte[] buf = new byte[1024];
        int len;
        try {
        	FileInputStream in = new FileInputStream(srcFile);
   	        zip.putNextEntry(new ZipEntry(new File(srcFile).getName()));
   	        while ((len = in.read(buf)) > 0) {
   	            zip.write(buf, 0, len);
   	        }
   	    } catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	public static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
		File folder = new File(srcFolder);
		
		for(String filename : folder.list()) {
			if(path.isEmpty()) {
				addFileToZip(folder.getName(), srcFolder + "/" + filename, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + filename, zip);
			}
		}
	}
	
	
	public static void ZipFolder(List<String> srcFolders, String destZipFile) throws IOException {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		
		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);
		for(String srcFolder : srcFolders) {
			if(new File(srcFolder).isDirectory()) {
				addFileToZip("", srcFolder, zip);
			} else {
				addFileToZipWithoutFolder(new File(srcFolder).getName(), srcFolder, zip);
			}
			zip.flush();
		}
        zip.close();
	}
	
	
	/******New methods*********/
	
	public static void writeStringToFile(String filePath, String content) throws IOException {
		try{
			FileWriter writer = new FileWriter(filePath);
			writer.write(content);
			Log.message("Successfully written to the file: " + filePath);
		}catch(IOException e) {
			Log.failsoft("Error writting to file " + e.getMessage());
		}
	}
	
	
	
	
}
