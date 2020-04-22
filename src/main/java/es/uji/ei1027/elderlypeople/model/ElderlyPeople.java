package es.uji.ei1027.elderlypeople.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class ElderlyPeople {
	String alergies;
	String name;
	String surnames;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate birthDate;
	String diseases;
	String dni;
	String address;
	String phoneNumber;
	String email;
	String bankAccountNumber;
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

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
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

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getSocialWorker() {
		return socialWorker;
	}

	public void setSocialWorker(String socialWorker) {
		this.socialWorker = socialWorker;
	}

	@Override
	public String toString() {
		return "ElderlyPeople [alergies=" + alergies + ", name=" + name + ", surnames=" + surnames + ", birthDate="
				+ birthDate + ", disease=" + diseases + ", dni=" + dni + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", bankAccountNumber=" + bankAccountNumber + ", socialWorker="
				+ socialWorker + "]";
	}

}
