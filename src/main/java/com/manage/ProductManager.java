package com.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
	private static HashSet<String> countries = new HashSet<>();

	/**
	 * The names set
	 */
	private static HashSet<String> names = new HashSet<>();

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

		// The candidates list
		ieltsRecords.forEach(record -> names.add(record.getCandidateName()));
		schoolRecords.forEach(record -> names.add(record.getCandidateName()));
	}

	/**
	 * Execution starts from here
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {

	}
}
