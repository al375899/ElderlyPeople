package es.uji.ei1027.elderlypeople.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Request {

	Integer idRequest;
	String serviceType;
	String state;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate beginDate;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate endDate;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate dateApprobation;

	String comments;
	Integer idContract;
	String dniElderly;

	public Request() {
		super();
	}

	public Integer getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(Integer idRequest) {
		this.idRequest = idRequest;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getDateApprobation() {
		return dateApprobation;
	}

	public void setDateApprobation(LocalDate dateApprobation) {
		this.dateApprobation = dateApprobation;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getIdContract() {
		return idContract;
	}

	public void setIdContract(Integer idContract) {
		this.idContract = idContract;
	}

	public String getDniElderly() {
		return dniElderly;
	}

	public void setDniElderly(String dniElderly) {
		this.dniElderly = dniElderly;
	}

	@Override
	public String toString() {
		return "Request [idRequest=" + idRequest + ", serviceType=" + serviceType + ", state=" + state + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", dateApprobation=" + dateApprobation + ", comments=" + comments
				+ ", idContract=" + idContract + ", dniElderly=" + dniElderly + "]";
	}
	
	
}
