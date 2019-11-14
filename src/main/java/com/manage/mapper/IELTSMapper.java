package com.manage.mapper;

import org.bson.Document;

import com.manage.model.IELTS;

/**
 * The IELTSMapper. It maps row data to IELTS model
 */
public class IELTSMapper {

	private IELTSMapper() {
		// Its a utility class. Thus instantiation is not allowed.
	}

	/**
	 * Maps row data to IELTS model
	 * 
	 * @param ieltsDocument The row document
	 * @return The prepared IELTS
	 */
	public static IELTS map(Document ieltsDocument) {

		IELTS ielts = new IELTS();
		ielts.setCountry(ieltsDocument.getString("Country"));
		ielts.setLocation(ieltsDocument.getString("Location"));
		ielts.setCandidateName(ieltsDocument.getString("Candidate Name"));
		ielts.setReference(ieltsDocument.getString("Reference"));
		ielts.setExamFormat(ieltsDocument.getString("Exam Format"));
		ielts.setRegistrationDate(ieltsDocument.getString("Registration Date"));
		ielts.setTestDate(ieltsDocument.getString("Test Date"));
		ielts.setPaymentRef(ieltsDocument.getString("Payment Ref"));
		ielts.setTotal(ieltsDocument.getString("Total"));
		ielts.setPaymentType(ieltsDocument.getString("Payment Type"));
		return ielts;
	}
}
