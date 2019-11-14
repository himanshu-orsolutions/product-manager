package com.manage;

import org.bson.Document;

import com.manage.parser.SchoolsSheetParser;

public class ProductManager {

	public static void main(String[] args) {

		Document schools = SchoolsSheetParser.parse("ORS.xlsx");
		System.out.println(schools.toJson());
	}
}
