package es.uji.ei1027.elderlypeople.model;

public class Company {
	String name;
	String contactPerson;
	String fiscalNumber;

	public Company() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getFiscalNumber() {
		return fiscalNumber;
	}

	public void setFiscalNumber(String fiscalNumber) {
		this.fiscalNumber = fiscalNumber;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", contactPerson=" + contactPerson + ", fiscalNumber=" + fiscalNumber + "]";
	}

}
