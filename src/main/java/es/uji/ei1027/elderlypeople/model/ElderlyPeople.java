package es.uji.ei1027.elderlypeople.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class ElderlyPeople {
	String alergies;
	String name;
	String surname;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate birthDate;
	String diseases;
	String dni;
	String address;
	String phoneNumber;
	String email;
	String banckAccountNumber;
	String socialWorker;

	// Constructor
	public ElderlyPeople() {
		super();
	}

	public String getAlergies() {
		return alergies;
	}

	public void setAlergies(String alergies) {
		this.alergies = alergies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getDiseases() {
		return diseases;
	}

	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBanckAccountNumber() {
		return banckAccountNumber;
	}

	public void setBanckAccountNumber(String banckAccountNumber) {
		this.banckAccountNumber = banckAccountNumber;
	}

	public String getSocialWorker() {
		return socialWorker;
	}

	public void setSocialWorker(String socialWorker) {
		this.socialWorker = socialWorker;
	}

	@Override
	public String toString() {
		return "ElderlyPeople [alergies=" + alergies + ", name=" + name + ", surname=" + surname + ", birthDate="
				+ birthDate + ", disease=" + diseases + ", dni=" + dni + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", banckAccountNumber=" + banckAccountNumber + ", socialWorker="
				+ socialWorker + "]";
	}

}
