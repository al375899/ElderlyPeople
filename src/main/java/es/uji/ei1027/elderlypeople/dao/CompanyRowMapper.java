package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CompanyRowMapper implements RowMapper<Company> {
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company company = new Company();
		company.setName(rs.getString("name"));
		company.setContactPerson(rs.getString("contactPerson"));
		company.setFiscalNumber(rs.getString("fiscalNumber"));
		return company;
	}
}
