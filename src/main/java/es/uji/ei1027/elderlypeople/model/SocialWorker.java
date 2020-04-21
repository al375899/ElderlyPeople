package es.uji.ei1027.elderlypeople.model;

public class SocialWorker {
	String name;
	String surnames;
	String phoneNumber;
	String dni;
	String email;
	
	public SocialWorker() {
		super();
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "SocialWorker [name=" + name + ", surnames=" + surnames + ", phoneNumber=" + phoneNumber + ", dni=" + dni
				+ ", email=" + email + "]";
	}
	
}
