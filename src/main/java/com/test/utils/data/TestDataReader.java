package com.test.utils.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Multiset.Entry;

public class TestDataReader {
	
	static String DataFolderPath = Paths.get(System.getProperty("user.dir"), "Data").toString();

	public static Map<String, String> readData(String fileName, String sheetName, String tcID) {
		Optional<Map<String, String>> rowData;
		try {
			rowData = WorkBookUtils.getWorkBook("Data/" + fileName).fetchSheetData(sheetName).stream()
					.filter(row -> row.entrySet().stream().anyMatch(entry -> entry.getValue().equalsIgnoreCase(tcID)))
					.findFirst();
			if (rowData.isPresent()) {
				return rowData.get();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static String[][] readAllDataOld(String fileName, String sheetName, String tcID) {
	    try {
	        List<Map<String,String>> dataList = WorkBookUtils.getWorkBook(".Data/"+ fileName).fetchSheetData(sheetName).stream()
	                .filter(row -> row.values().stream().anyMatch(value -> value.equalsIgnoreCase(tcID)))
	                .collect(Collectors.toList());

	        int numRows = dataList.size();
	        int numCols = numRows > 0 ? dataList.get(0).size() : 0;
	        String[][] dataArray = new String[numRows][numCols];

	        for (int i = 0; i < numRows; i++) {
	            Map<String, String> rowMap = dataList.get(i);
	            int j = 0;
	            for (String value : rowMap.values()) {
	                dataArray[i][j++] = value;
	            }
	        }
	        return dataArray;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public static String[][] readAllData(String filePath, String sheetName, String tcID) {
	    try {
	        // Verify that the file exists before attempting to read it
	        File file = new File(filePath);
	        if (!file.exists()) {
	            throw new FileNotFoundException("File not found: " + filePath);
	        }
	        String fileName = file.getName();
	        System.out.println("Reading data from file: " + fileName);
	        // Proceed with reading the data
	        List<Map<String, String>> dataList = WorkBookUtils.getWorkBookss(filePath)
	                .fetchSheetData(sheetName)
	                .stream()
	                .filter(row -> row.values().stream()
	                        .anyMatch(value -> value.equalsIgnoreCase(tcID)))
	                .collect(Collectors.toList());

	        int numRows = dataList.size();
	        int numCols = numRows > 0 ? dataList.get(0).size() : 0;
	        String[][] dataArray = new String[numRows][numCols];

	        for (int i = 0; i < numRows; i++) {
	            Map<String, String> rowMap = dataList.get(i);
	            int j = 0;
	            for (String value : rowMap.values()) {
	                dataArray[i][j++] = value;
	            }
	        }
	        return dataArray;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}


}