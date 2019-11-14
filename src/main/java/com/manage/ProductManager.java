package com.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.bson.Document;

import com.manage.mapper.IELTSMapper;
import com.manage.mapper.SchoolMapper;
import com.manage.model.IELTS;
import com.manage.model.School;
import com.manage.parser.IELTSSheetParser;
import com.manage.parser.SchoolsSheetParser;

/**
 * The Product Manager.
 */
public class ProductManager {

	/**
	 * The School map
	 */
	private static HashMap<String, School> schoolMap = new HashMap<>();

	/**
	 * The IELTS map
	 */
	private static HashMap<String, IELTS> ieltsMap = new HashMap<>();

	/**
	 * The countries set
	 */
	private static TreeSet<String> countries = new TreeSet<>();

	/**
	 * The names set
	 */
	private static TreeSet<String> names = new TreeSet<>();

	/**
	 * Maps row IETLS data into models
	 * 
	 * @param ieltsInfo The row IELTS info
	 * @return The list of prepared IETLS
	 */
	@SuppressWarnings("unchecked")
	private static List<IELTS> mapRowIELTS(Document ieltsInfo) {

		List<IELTS> ieltsRecords = new ArrayList<>();
		List<Document> data = (List<Document>) ieltsInfo.get("data");

		if (data != null && !data.isEmpty()) {
			for (Document ieltsDocument : data) {
				ieltsRecords.add(IELTSMapper.map(ieltsDocument));
			}
		}
		return ieltsRecords;
	}

	/**
	 * Maps row School data into models
	 * 
	 * @param schoolInfo The row School info
	 * @return The list of prepared School
	 */
	@SuppressWarnings("unchecked")
	private static List<School> mapRowSchool(Document schoolInfo) {

		List<School> schoolRecords = new ArrayList<>();
		List<Document> data = (List<Document>) schoolInfo.get("data");

		if (data != null && !data.isEmpty()) {
			for (Document schoolDocument : data) {
				schoolRecords.add(SchoolMapper.map(schoolDocument));
			}
		}
		return schoolRecords;
	}

	/**
	 * Initializes the application
	 */
	private static void init() {

		// Parsing the data
		Document ieltsInfo = IELTSSheetParser.parse("ORS.xlsx");
		Document schoolInfo = SchoolsSheetParser.parse("schools.xlsx");

		// Mapping row data to models
		List<IELTS> ieltsRecords = mapRowIELTS(ieltsInfo);
		List<School> schoolRecords = mapRowSchool(schoolInfo);

		// Preparing UI data sources
		// The data maps
		ieltsRecords.forEach(record -> ieltsMap.put(record.toString(), record));
		schoolRecords.forEach(record -> schoolMap.put(record.toString(), record));

		// The countries list
		ieltsRecords.forEach(record -> countries.add(record.getCountry()));
		schoolRecords.forEach(record -> countries.add(record.getCountry()));
		countries.remove("");

		// The candidates list
		ieltsRecords.forEach(record -> names.add(record.getCandidateName()));
		schoolRecords.forEach(record -> names.add(record.getCandidateName()));
		names.remove("");
	}

	/**
	 * Gets the information of product
	 * 
	 * @param productType   The product type
	 * @param country       The country
	 * @param candidateName the candidate name
	 * @param referenceId   The reference ID
	 * @return The product information
	 */
	private static String getInformation(String productType, String country, String candidateName, String referenceId) {

		if (productType.equals("IELTS")) {
			IELTS ielts = ieltsMap.get(country + " " + candidateName + " " + referenceId);
			System.out.println(ielts.toString());
		} else {
			School school = schoolMap.get(country + " " + candidateName + " " + referenceId);
			System.out.println(school.toString());
		}

		return "";
	}

	/**
	 * Execution starts from here
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {

		init(); // Initializes the application environment
	}
}
