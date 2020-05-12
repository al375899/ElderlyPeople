package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.HourVolunteer;

import javax.sql.DataSource;

import java.time.LocalTime;
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
				hourVolunteer.getDniVolunteer(), hourVolunteer.getDay(), hourVolunteer.getStartHour(),
				hourVolunteer.getEndHour(), hourVolunteer.getTaken());
	}

	/* Esborra el hourvolunteer de la base de dades */
	public void deleteHourVolunteer(HourVolunteer hourVolunteer) {
		jdbcTemplate.update("DELETE FROM HourVolunteer WHERE dniVolunteer=?, day = ? startHour = ? AND endHour = ?",
				hourVolunteer.getDniVolunteer(), hourVolunteer.getDay(), hourVolunteer.getStartHour(), hourVolunteer.getEndHour());
	}

	public void deleteHourVolunteer(String dniVolunteer, String day, LocalTime startHour, LocalTime endHour) {
		jdbcTemplate.update("DELETE FROM HourVolunteer WHERE dniVolunteer=?, day = ?, startHour=?  AND endHour = ?", dniVolunteer,
				day, startHour, endHour);
	}

	/*
	 * Actualitza els atributs del hourvolunteer
	 */
	public void updateHourVolunteer(HourVolunteer hourVolunteer) {
		jdbcTemplate.update(
				"UPDATE HourVolunteer SET dniElderly=?, taken = ? WHERE dniVolunteer=? day = ?, startHour = ? AND endHour = ?",
				hourVolunteer.getDniElderly(), hourVolunteer.getTaken(), hourVolunteer.getDniVolunteer(), hourVolunteer.getDay(), 
				hourVolunteer.getStartHour(), hourVolunteer.getEndHour());
	}

	/* Obté el hourvolunteer */
	public HourVolunteer getHourVolunteer(String dniVolunteer, String day, LocalTime startHour, LocalTime endHour) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM HourVolunteer WHERE dniVolunteer = ?, day = ?, startHour = ? AND endHour = ?", new HourVolunteerRowMapper(),
					dniVolunteer, day, startHour, endHour);
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