package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.Line;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class LineRowMapper implements RowMapper<Line> {
	public Line mapRow(ResultSet rs, int rowNum) throws SQLException {
		Line line = new Line();
		line.setInvoiceCode(rs.getInt("invoiceCode"));
		line.setIdRequest(rs.getInt("idRequest"));
		line.setConcept(rs.getString("concept"));
		line.setImporte(rs.getInt("importe"));
		return line;
	}
}