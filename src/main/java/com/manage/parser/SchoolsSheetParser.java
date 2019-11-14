package com.manage.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import com.monitorjbl.xlsx.StreamingReader;

/**
 * The SchoolSheetParser. It holds implementation to parse the Schools sheet.
 */
public class SchoolsSheetParser {

	private SchoolsSheetParser() {
		// Its a utility class. Thus instantiation is not allowed.
	}

	/**
	 * Parses the Schools Sheet
	 * 
	 * @param filePath The file path
	 * @return The parsed document
	 */
	public static Document parse(String filePath) {

		Document schools = new Document(); // It holds the final parsed document
		List<Document> data = new ArrayList<>(); // It holds the list of school records

		try (Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096)
				.open(new File(filePath))) { // The file is read in chunks to avoid crashing in case of large sizes
			Sheet sheet = workbook.getSheet("Unpaid");
			Iterator<Row> iterator = sheet.iterator();

			if (iterator.hasNext()) {

				// Parsing the column headings
				List<String> headings = new ArrayList<>();
				Row header = iterator.next();
				Iterator<Cell> cellIterator = header.cellIterator();

				while (cellIterator.hasNext()) {
					headings.add(cellIterator.next().getStringCellValue().trim());
				}

				if (iterator.hasNext()) {
					// Parsing the the column values from further rows
					int totalColumns = headings.size();

					while (iterator.hasNext()) {
						Document rowDocument = new Document();
						Row row = iterator.next();

						for (int c = 0; c < totalColumns; c++) {
							Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							rowDocument.append(headings.get(c), cell.getStringCellValue().trim());
						}
						data.add(rowDocument);
					}
				}
			}
			schools.put("data", data);
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println("Error: The Schools report not found.");
		} catch (IOException ioException) {
			System.out.println("Error: Reading Schools report failed.");
		}
		return schools;
	}
}
