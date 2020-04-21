package es.uji.ei1027.elderlypeople.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Volunteer {
	String dni;
	
	@DateTimeFormat (pattern = "dd-MM-yyyy")
	LocalDate requestDate;
	
	@DateTimeFormat (pattern = "dd-MM-yyyy")
	LocalDate acceptationDate;
	
	@DateTimeFormat (pattern = "dd-MM-yyyy")
	LocalDate endDate;
	
	String name;
	String phoneNumber;
	String gender;
	
	@DateTimeFormat (pattern = "dd-MM-yyyy")
	LocalDate birthDate;

	public Volunteer() {
		super();
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public LocalDate getAcceptationDate() {
		return acceptationDate;
	}

	public void setAcceptationDate(LocalDate acceptationDate) {
		this.acceptationDate = acceptationDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Volunteer [dni=" + dni + ", requestDate=" + requestDate + ", acceptationDate=" + acceptationDate
				+ ", endDate=" + endDate + ", name=" + name + ", phoneNumber=" + phoneNumber + ", gender=" + gender
				+ ", birthDate=" + birthDate + "]";
	}

}
