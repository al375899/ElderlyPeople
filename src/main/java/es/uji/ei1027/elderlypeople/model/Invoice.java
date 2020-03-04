package es.uji.ei1027.elderlypeople.model;

public class Invoice {
	Integer invoiceCode;
	Integer totalPrice;
	String dniElderly;

	public Invoice() {
		super();
	}

	public Integer getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(Integer invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDniElderly() {
		return dniElderly;
	}

	public void setDniElderly(String dniElderly) {
		this.dniElderly = dniElderly;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceCode=" + invoiceCode + ", totalPrice=" + totalPrice + ", dniElderly=" + dniElderly
				+ "]";
	}

}
