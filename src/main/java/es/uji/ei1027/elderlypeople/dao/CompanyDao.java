package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.Company;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class CompanyDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el company a la base de dades */
	public void addCompany(Company company) {
		jdbcTemplate.update("INSERT INTO Company VALUES(?,?,?)", company.getName(), company.getContactPerson(),
				company.getFiscalNumber());
	}

	/* Esborra el company de la base de dades */
	public void deleteCompany(Company company) {
		jdbcTemplate.update("DELETE FROM Company WHERE fiscalNumber=?", company.getFiscalNumber());
	}

	public void deleteCompany(String fiscalNumber) {
		jdbcTemplate.update("DELETE FROM Company WHERE fiscalNumber=?", fiscalNumber);
	}

	/*
	 * Actualitza els atributs del Company
	 */
	public void updateCompany(Company company) {
		jdbcTemplate.update("UPDATE Company SET name = ?, contactPerson = ? WHERE fiscalNumber = ?", company.getName(),
				company.getContactPerson(), company.getFiscalNumber());
	}

	/* Obté el Company */
	public Company getCompany(String fiscalNumber) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE fiscalNumber =?", new CompanyRowMapper(),
					fiscalNumber);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els companies. Torna una llista buida si no n'hi ha cap. */
	public List<Company> getCompanies() {
		try {
			return jdbcTemplate.query("SELECT * FROM Company", new CompanyRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Company>();
		}
	}
}
