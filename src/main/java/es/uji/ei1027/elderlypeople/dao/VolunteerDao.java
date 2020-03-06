package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.Volunteer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class VolunteerDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el volunteer a la base de dades */
	public void addVolunteer(Volunteer volunteer) {
		jdbcTemplate.update("INSERT INTO Volunteer VALUES(?,?,?,?,?,?,?,?)", volunteer.getDni(),
				volunteer.getRequestDate(), volunteer.getAcceptationDate(), volunteer.getEndDate(), volunteer.getName(),
				volunteer.getPhoneNumber(), volunteer.getGender(), volunteer.getAge());
	}

	/* Esborra el volunteer de la base de dades */
	void deleteVolunteer(Volunteer volunteer) {
		jdbcTemplate.update("DELETE FROM Volunteer WHERE dni=?", volunteer.getDni());
	}

	public void deleteVolunteer(String dni) {
		jdbcTemplate.update("DELETE FROM Volunteer WHERE dni=?", dni);
	}

	/*
	 * Actualitza els atributs del volunteer
	 */
	public void updateVolunteer(Volunteer volunteer) {
		jdbcTemplate.update("UPDATE Volunteer SET requestDate = ?, acceptationDate = ?, endDate = ?, name = ?, phoneNumber = ?, gender = ?, age = ? WHERE dni = ?",
				volunteer.getRequestDate(), volunteer.getAcceptationDate(), volunteer.getEndDate(), volunteer.getName(),
				volunteer.getPhoneNumber(), volunteer.getGender(), volunteer.getAge(), volunteer.getDni());
	}

	/* Obté el volunteer */
	public Volunteer getVolunteer(String dni) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE dni =?",
					new VolunteerRowMapper(), dni);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els volunteers. Torna una llista buida si no n'hi ha cap. */
	public List<Volunteer> getVolunteers() {
		try {
			return jdbcTemplate.query("SELECT * FROM Volunteer", new VolunteerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Volunteer>();
		}
	}
}
