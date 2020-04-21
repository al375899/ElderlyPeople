package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.Line;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class LineDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el line a la base de dades */
	public void addLine(Line line) {
		jdbcTemplate.update("INSERT INTO Line VALUES(?,?,?,?)", line.getInvoiceCode(), line.getIdRequest(),
				line.getConcept(), line.getImporte());
	}

	/* Esborra el line de la base de dades */
	public void deleteLine(Line line) {
		jdbcTemplate.update("DELETE FROM Line WHERE invoiceCode=? AND idRequest=?", line.getInvoiceCode(), line.getIdRequest());
	}

	public void deleteLine(Integer invoiceCode, Integer idRequest) {
		jdbcTemplate.update("DELETE FROM Line WHERE invoiceCode=? AND idRequest=?", invoiceCode, idRequest);
	}

	/*
	 * Actualitza els atributs del line
	 */
	public void updateLine(Line line) {
		jdbcTemplate.update("UPDATE Line SET concept = ?, importe = ? WHERE invoiceCode = ? AND idRequest = ?", 
				line.getConcept(), line.getImporte(), line.getInvoiceCode(), line.getIdRequest());
	}

	/* Obté el line */
	public Line getLine(Integer invoiceCode, Integer idRequest) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM Line WHERE invoiceCode =? AND idRequest = ?", new LineRowMapper(),
					invoiceCode, idRequest);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els lines. Torna una llista buida si no n'hi ha cap. */
	public List<Line> getLines() {
		try {
			return jdbcTemplate.query("SELECT * FROM Line", new LineRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Line>();
		}
	}
}
