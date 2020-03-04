package es.uji.ei1027.elderlypeople.model;

public class Line {
	Integer invoiceCode;
	Integer idRequest;
	String concept;
	Integer importe;

	public Line() {
		super();
	}

	public Integer getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(Integer invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Integer getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(Integer idRequest) {
		this.idRequest = idRequest;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public Integer getImporte() {
		return importe;
	}

	public void setImporte(Integer importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "Line [invoiceCode=" + invoiceCode + ", idRequest=" + idRequest + ", concept=" + concept + "]";
	}

}
