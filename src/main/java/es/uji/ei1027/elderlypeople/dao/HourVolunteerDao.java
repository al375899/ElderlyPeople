package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.HourVolunteer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class HourVolunteerDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el hourvolunteer a la base de dades */
	public void addHourVolunteer(HourVolunteer hourVolunteer) {
		jdbcTemplate.update("INSERT INTO HourVolunteer VALUES(?,?,?,?,?,?)", hourVolunteer.getDniElderly(),
				hourVolunteer.getDniVolunteer(), hourVolunteer.getDate(), hourVolunteer.getStartHour(),
				hourVolunteer.getEndHour(), hourVolunteer.getTaken());
	}

	/* Esborra el hourvolunteer de la base de dades */
	public void deleteHourVolunteer(HourVolunteer hourVolunteer) {
		jdbcTemplate.update("DELETE FROM HourVolunteer WHERE dniElderly=? AND dniVolunteer = ?",
				hourVolunteer.getDniElderly(), hourVolunteer.getDniVolunteer());
	}

	public void deleteHourVolunteer(String dniElderly, String dniVolunteer) {
		jdbcTemplate.update("DELETE FROM HourVolunteer WHERE dniElderly=? AND dniVolunteer=?", dniElderly,
				dniVolunteer);
	}

	/*
	 * Actualitza els atributs del hourvolunteer
	 */
	public void updateHourVolunteer(HourVolunteer hourVolunteer) {
		jdbcTemplate.update(
				"UPDATE HourVolunteer SET date = ?, startHour = ?, endHour = ?, taken = ? WHERE dniElderly=? AND dniVolunteer=?",
				hourVolunteer.getDate(), hourVolunteer.getStartHour(), hourVolunteer.getEndHour(),
				hourVolunteer.getTaken(), hourVolunteer.getDniElderly(), hourVolunteer.getDniVolunteer());
	}

	/* Obté el hourvolunteer */
	public HourVolunteer getHourVolunteer(String dniElderly, String dniVolunteer) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM HourVolunteer WHERE dniElderly =? AND dniVolunteer=?", new HourVolunteerRowMapper(),
					dniElderly, dniVolunteer);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els hourvolunteers. Torna una llista buida si no n'hi ha cap. */
	public List<HourVolunteer> getHourVolunteers() {
		try {
			return jdbcTemplate.query("SELECT * FROM HourVolunteer", new HourVolunteerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<HourVolunteer>();
		}
	}
}