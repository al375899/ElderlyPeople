package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.SocialWorker;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class SocialWorkerDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el social worker a la base de dades */
	public void addSocialWorker(SocialWorker socialWorker) {
		jdbcTemplate.update("INSERT INTO SocialWorker VALUES(?,?,?,?,?)", 
				socialWorker.getName(),
				socialWorker.getSurnames(),
				socialWorker.getPhoneNumber(),
				socialWorker.getDni(),
				socialWorker.getEmail()
				);
	}

	/* Esborra el social worker de la base de dades */
	public void deleteSocialWorker(SocialWorker socialWorker) {
		jdbcTemplate.update("DELETE FROM SocialWorker WHERE dni=?", 
				socialWorker.getDni()
				);
	}

	// Esborra el Request per el id
	public void deleteSocialWorker(String dni) {
		jdbcTemplate.update("DELETE FROM SocialWorker WHERE dni=?", 
				dni
				);
	}

	// Actualitza els atributs del socialWorker
	public void updateSocialWorker(SocialWorker socialWorker) {
		jdbcTemplate.update("UPDATE SocialWorker SET name = ?, surnames = ?, phoneNumber = ?, email = ? WHERE dni = ?",
				socialWorker.getName(),
				socialWorker.getSurnames(),
				socialWorker.getPhoneNumber(),
				socialWorker.getEmail(),
				socialWorker.getDni()
				);
	}

	/* Obté el SocialWorker */
	public SocialWorker getSocialWorker(String dni) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM SocialWorker WHERE dni =?", new SocialWorkerRowMapper(), dni);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els SocialWorkers. Torna una llista buida si no n'hi ha cap. */
	public List<SocialWorker> getSocialWorkers() {
		try {
			return jdbcTemplate.query("SELECT * FROM SocialWorker", new SocialWorkerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<SocialWorker>();
		}
	}
}