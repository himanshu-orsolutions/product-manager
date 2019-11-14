package com.manage.model;

/**
 * The model School. It holds the mandatory School fields.
 */
public class School {

	private String firstName;
	private String lastName;
	private String country;
	private String centreName;
	private String totalLocalFee;
	private String numberOfExams;
	private String registrationId;
	private String paymentReference;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCentreName() {
		return centreName;
	}

	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	public String getTotalLocalFee() {
		return totalLocalFee;
	}

	public void setTotalLocalFee(String totalLocalFee) {
		this.totalLocalFee = totalLocalFee;
	}

	public String getNumberOfExams() {
		return numberOfExams;
	}

	public void setNumberOfExams(String numberOfExams) {
		this.numberOfExams = numberOfExams;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * Gets the string representation of the IELTS model
	 */
	public String toString() {

		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(this.country);
		keyBuilder.append(" ");
		keyBuilder.append(this.lastName);
		keyBuilder.append(" ");

		if (!this.registrationId.equals("")) {
			keyBuilder.append(this.registrationId.substring(this.registrationId.length() - 7));
		}
		return keyBuilder.toString();
	}
}
