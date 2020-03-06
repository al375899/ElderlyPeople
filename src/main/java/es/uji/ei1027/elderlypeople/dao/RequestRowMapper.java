package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class RequestRowMapper implements RowMapper<Request> {
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
		Request request = new Request();

		request.setIdRequest(rs.getInt("idRequest"));
		request.setServiceType(rs.getString("serviceType"));
		request.setState(rs.getString("state"));
		request.setBeginDate(rs.getObject("beginDate", LocalDate.class)); // localdate y date
		request.setEndDate(rs.getObject("endDate", LocalDate.class)); // localdate y date
		request.setDateApprobation(rs.getObject("dateApprobation", LocalDate.class)); // localdate y date
		request.setComments(rs.getString("comments"));
		request.setIdContract(rs.getInt("idContract"));
		request.setDniElderly(rs.getString("dniElderly"));
		return request;
	}
}
