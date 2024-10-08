package com.test.utils.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.io.Resources;

public class WorkBookUtils {

	private XSSFWorkbook workbook;

	private File file;

	private WorkBookUtils() {

	}

	private WorkBookUtils(String filePath) throws FileNotFoundException, IOException {

		File file = new File(filePath);
		if (!file.exists()) {
			this.workbook = new XSSFWorkbook();
		} else {
			try {
				InputStream is = new FileInputStream(file);
				try {
					this.workbook = new XSSFWorkbook(is);
				} catch (EmptyFileException e) {
					System.out.println("The file is empty. So create new workbook");
					this.workbook = new XSSFWorkbook();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private WorkBookUtils(File file) throws IOException {

		if (!file.exists()) {
			file.createNewFile();
			this.workbook = new XSSFWorkbook();
		} else {
			try {
				InputStream is = new FileInputStream(file);
				try {
					this.workbook = new XSSFWorkbook(is);
				} catch (EmptyFileException e) {
					System.out.println("The file is empty. So create new workbook");
					this.workbook = new XSSFWorkbook();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.file = file;
	}

	public static WorkBookUtils getWorkBook(String excelFileName) throws IOException {
		String filePath = Resources.getResource(excelFileName).getFile();
		return new WorkBookUtils(filePath);
	}

	public static WorkBookUtils getWorkBook(File file) throws IOException {
		return new WorkBookUtils(file);
	}

	public static WorkBookUtils getWorkBookss(String filePath) throws IOException {	    
	    return new WorkBookUtils(filePath);
	}
	
	public List<Map<String, String>> fetchSheetData(String sheetName) {

		XSSFSheet sheet = this.workbook.getSheet(sheetName);
		List<String> headers = new ArrayList<>();
		sheet.getRow(0).forEach(cell -> headers.add(getCellValue(cell).toString()));
		List<Map<String, String>> response = new ArrayList<>();
		try {
			if (Objects.isNull(sheet)) {
				System.out.println("Given Sheet is not present in the workbook.");
				throw new NullPointerException();
			}

			IntStream.rangeClosed(1, sheet.getLastRowNum()).forEach(row -> {

			});

			sheet.forEach(row -> {

				if (row.getRowNum() != 0) {
					Map<String, String> rowValues = new LinkedHashMap<>();
					row.forEach(cell -> {
						try {
							rowValues.put(headers.get(cell.getColumnIndex()), getCellValue(cell).toString());
						} catch (IndexOutOfBoundsException e) {

						}
					});

					response.add(rowValues);
				}

			});
		} catch (NullPointerException e) {
			// To do
		}

		return response;
	}
	
	
	public Map<String, String> getRowValuesByRowNumber(String sheetName, int rowNum) {
		return fetchSheetData(sheetName).get(rowNum - 1);
	}

	
	public Map<String, String> getRowValuesByTcId(String sheetName, String tcID) {
		Optional<Map<String, String>> rowData = fetchSheetData(sheetName).stream()
				.filter(row -> row.entrySet().stream().anyMatch(entry -> entry.getValue().equalsIgnoreCase(tcID)))
				.findFirst();
		if (rowData.isPresent()) {
			return rowData.get();
		} else {
			return null;
		}

	}

	
	public String getCellValueByTcID(String sheetName, String header, String tcID) {
		return getRowValuesByTcId(sheetName, tcID).get(header);
	}

	
	public String getCellValueByRowNumber(String sheetName, String header, int rowNum) {
		return getRowValuesByRowNumber(sheetName, rowNum).get(header);
	}

	
	public boolean updateSheetWithData(String sheetName, List<Map<String, String>> data) {
		try {
			if (Objects.isNull(workbook.getSheet(sheetName))) {
				workbook.createSheet(sheetName);
			}
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Object[] headers = data.get(0).keySet().toArray();
			XSSFRow headerRow = sheet.createRow(0);
			IntStream.range(0, headers.length)
					.forEach(colCount -> headerRow.createCell(colCount).setCellValue(headers[colCount].toString()));
			IntStream.rangeClosed(1, data.size()).forEach(rowCount -> {
				XSSFRow row = sheet.createRow(rowCount);
				IntStream.range(0, headers.length).forEach(colCount -> row.createCell(colCount)
						.setCellValue(data.get(rowCount - 1).get(headers[colCount])));
			});
			writeToFile();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean addValuesWithFormulas(String sheetName, List<List<Object>> data) {

		try {
			if (Objects.isNull(workbook.getSheet(sheetName))) {
				workbook.createSheet(sheetName);
			}
			XSSFSheet sheet = workbook.getSheet(sheetName);

			IntStream.range(0, data.size()).forEach(rowCount -> {
				XSSFRow row = sheet.createRow(rowCount);
				IntStream.range(0, data.get(rowCount).size()).forEach(colCount -> {
					if (data.get(rowCount).get(colCount).toString().startsWith("=")) {
						String excludeEqualsTo = data.get(rowCount).get(colCount).toString().replace("=", "");
						row.createCell(colCount).setCellFormula(excludeEqualsTo);
					} else {
						row.createCell(colCount).setCellValue(data.get(rowCount).get(colCount).toString());
					}
				});
			});
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			evaluator.evaluateAll();
			// writeToFile();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Writes the current XSSFWorkbook to the file specified in the constructor.
	 *
	 * @throws IOException If an I/O error occurs while writing the file.
	 */
	public void writeToFile() {
		try {
			if (this.file.createNewFile()) {
				System.out.println("File Created Successfully!!");
			} else {
				System.out.println("File already Exists!!");
			}
			FileOutputStream out = new FileOutputStream(this.file);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Utility method to get the value of a cell, considering different cell types.
	 *
	 * @param cell The cell whose value needs to be retrieved.
	 * @return The value of the cell as an Object.
	 */
	public Object getCellValue(Cell cell) {

		if (cell != null) {
			if (cell.getCellType() == CellType.FORMULA) {

				switch (cell.getCachedFormulaResultType()) {
				case BOOLEAN:
					return cell.getBooleanCellValue();
				case NUMERIC:
					return cell.getNumericCellValue();
				case STRING:
					return cell.getRichStringCellValue();
				default:
					return "";
				}
			} else {
				switch (cell.getCellType()) {
				case STRING:
					return cell.getStringCellValue();
				case NUMERIC:
					return cell.getNumericCellValue();
				case BOOLEAN:
					return cell.getBooleanCellValue();
				case FORMULA:
					return cell.getCellFormula();
				case BLANK:
					return ""; // or return null, depending on your use case
				default:
					return "";
				}
			}
		} else {
			System.out.println("Cell not found.");
		}

		return null;

	}

	public List<Map<String, String>> getTableContent(String sheetName, int startRow) {
		XSSFSheet sheet = this.workbook.getSheet(sheetName);
		List<String> headers = new ArrayList<>();
		XSSFRow row = sheet.getRow(startRow - 1);
		for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
			XSSFCell cell = row.getCell(cellIndex);
			if (Objects.nonNull(cell)) {
				headers.add(getCellValue(row.getCell(cellIndex)).toString());
			}
		}

		List<Map<String, String>> response = new ArrayList<>();
		try {
			if (Objects.isNull(sheet)) {
				System.out.println("Given Sheet is not present in the workbook.");
				throw new NullPointerException();
			}

			for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Map<String, String> rowValues = new LinkedHashMap<>();
				for (int cellIndex = sheet.getRow(rowIndex).getFirstCellNum(); cellIndex < sheet.getRow(rowIndex)
						.getLastCellNum(); cellIndex++) {
					XSSFCell cell = sheet.getRow(rowIndex).getCell(cellIndex);
					if (Objects.nonNull(cell)) {
						rowValues.put(headers.get(cellIndex - sheet.getRow(rowIndex).getFirstCellNum()),
								getCellValue(cell).toString());
					}
				}
				response.add(rowValues);
			}
		} catch (NullPointerException e) {
			// To do
		}

		return response;
	}

	public void addRowValuesToSheet(String sheetName, List<Object> values, int rowIndex, int colIndex) {

		if (Objects.isNull(workbook.getSheet(sheetName))) {
			this.workbook.createSheet(sheetName);
		}
		XSSFSheet sheet = this.workbook.getSheet(sheetName);
		XSSFRow row = getRow(this.workbook.getSheet(sheetName), rowIndex);

		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		// Apply border
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);

		XSSFFont font = workbook.createFont();
		font.setFontName("Times New Roman");

		// Make header row as bold
		if (rowIndex == 0)
			font.setBold(true);

		cellStyle.setFont(font);

		IntStream.range(colIndex, colIndex + values.size()).forEach(itr -> {
			XSSFCell cell = row.createCell(itr);
			sheet.autoSizeColumn(itr);
			try {
				cell.setCellValue(Double.valueOf(values.get(itr - colIndex).toString()));
			} catch (Exception e) {
				cell.setCellValue(values.get(itr - colIndex).toString());
			}
			cell.setCellStyle(cellStyle);
		});
		// writeToFile();
	}

	public static XSSFRow getRow(XSSFSheet sheet, int rowIndex) {
		XSSFRow row;
		try {
			row = sheet.getRow(rowIndex);
			if (Objects.isNull(row)) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			row = sheet.createRow(rowIndex);
		}
		return row;
	}

	public void addBorders(String sheetName) {
		XSSFSheet sheet = this.workbook.getSheet(sheetName);
		XSSFCellStyle style = this.workbook.createCellStyle();
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);

		IntStream.rangeClosed(0, sheet.getLastRowNum()).forEach(rowNum -> {
			IntStream.range(0, sheet.getRow(rowNum).getLastCellNum()).forEach(colNum -> {
				sheet.getRow(rowNum).getCell(colNum).setCellStyle(style);
			});
		});
		writeToFile();
	}

	public int getSheetCount() {
		return this.workbook.getNumberOfSheets();
	}

	public String getSheetName(int index) {
		return this.workbook.getSheetName(index);
	}

	public List<String> getHeaders(String sheetName) {
		List<String> headers = new ArrayList<>();
		this.workbook.getSheet(sheetName).getRow(0).forEach(headerCell -> headers.add(headerCell.getStringCellValue()));
		return headers;
	}

}
