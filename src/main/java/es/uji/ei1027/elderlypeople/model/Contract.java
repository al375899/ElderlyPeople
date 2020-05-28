package es.uji.ei1027.elderlypeople.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Contract {
	Integer idContract;
	String fnCompany;
	String serviceType;
	Integer quantity;
	Integer available;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate startDate;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate endDate;
	Integer price;

	public Contract() {
		super();
	}

	public Integer getIdContract() {
		return idContract;
	}

	public void setIdContract(Integer idContract) {
		this.idContract = idContract;
	}

	public String getFnCompany() {
		return fnCompany;
	}

	public void setFnCompany(String fnCompany) {
		this.fnCompany = fnCompany;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Contract [idContract=" + idContract + ", fnCompany=" + fnCompany + ", serviceType=" + serviceType
				+ ", quantity=" + quantity + ", startDate=" + startDate + ", endDate=" + endDate + ", price=" + price
				+ "]";
	}

}
