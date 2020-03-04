package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InvoiceRowMapper implements RowMapper<Invoice> {
	public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
		Invoice invoice = new Invoice();
		invoice.setInvoiceCode(rs.getInt("invoiceCode"));
		invoice.setTotalPrice(rs.getInt("totalPrice"));
		invoice.setDniElderly(rs.getString("dniElderly"));
		return invoice;
	}
}
