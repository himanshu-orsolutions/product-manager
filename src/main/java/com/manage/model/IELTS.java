package com.manage.model;

/**
 * The model IELTS. It holds the mandatory IELTS fields.
 */
public class IELTS {

	private String country;
	private String location;
	private String candidateName;
	private String reference;
	private String examFormat;
	private String registrationDate;
	private String testDate;
	private String paymentRef;
	private String total;
	private String paymentType;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getExamFormat() {
		return examFormat;
	}

	public void setExamFormat(String examFormat) {
		this.examFormat = examFormat;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getPaymentRef() {
		return paymentRef;
	}

	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the string representation of the IELTS model
	 */
	public String toString() {

		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(this.country);
		keyBuilder.append(" ");
		keyBuilder.append(this.candidateName);
		keyBuilder.append(" ");

		if (!this.reference.equals("")) {
			keyBuilder.append(this.reference.substring(this.reference.length() - 7));
		}
		return keyBuilder.toString();
	}
}
