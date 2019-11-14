package com.manage.mapper;

import org.bson.Document;

import com.manage.model.School;

/**
 * The model SchoolMapper. It holds the mandatory School fields.
 */
public class SchoolMapper {

	private SchoolMapper() {
		// Its a utility class. Thus instantiation is not allowed.
	}

	/**
	 * Maps row data to School model
	 * 
	 * @param schoolDocument The row document
	 * @return The prepared School
	 */
	public static School map(Document schoolDocument) {

		School school = new School();
		school.setFirstName(schoolDocument.getString("First Name"));
		school.setLastName(schoolDocument.getString("Last Name"));
		school.setCountry(schoolDocument.getString("School country"));
		school.setCentreName(schoolDocument.getString("Centre Name"));
		school.setTotalLocalFee(schoolDocument.getString("Total Local Fee ($)"));
		school.setNumberOfExams(schoolDocument.getString("No of Exams"));
		school.setRegistrationId(schoolDocument.getString("Registration ID"));
		school.setPaymentReference(schoolDocument.getString("Payment Reference"));
		return school;
	}
}
