package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.Invoice;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class InvoiceDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el invoice a la base de dades */
	public void addInvoice(Invoice invoice) {
		jdbcTemplate.update("INSERT INTO Invoice VALUES(?,?,?)", invoice.getInvoiceCode(), invoice.getTotalPrice(),
				invoice.getDniElderly());
	}

	/* Esborra el invoice de la base de dades */
	void deleteInvoice(Invoice invoice) {
		jdbcTemplate.update("DELETE FROM Invoice WHERE invoiceCode=?", invoice.getInvoiceCode());
	}

	public void deleteInvoice(String invoiceCode) {
		jdbcTemplate.update("DELETE FROM Invoice WHERE invoiceCode=?", invoiceCode);
	}

	/*
	 * Actualitza els atributs del invoice
	 */
	public void updateInvoice(Invoice invoice) {
		jdbcTemplate.update("UPDATE Invoice SET totalPrice = ?, dniElderly = ? WHERE invoiceCode = ?", 
				invoice.getTotalPrice(), invoice.getDniElderly(), invoice.getInvoiceCode());
	}

	/* Obté el invoice */
	public Invoice getInvoice(String invoiceCode) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM Invoice WHERE invoiceCode =?", new InvoiceRowMapper(),
					invoiceCode);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els invoices. Torna una llista buida si no n'hi ha cap. */
	public List<Invoice> getInvoices() {
		try {
			return jdbcTemplate.query("SELECT * FROM Invoice", new InvoiceRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Invoice>();
		}
	}
}
