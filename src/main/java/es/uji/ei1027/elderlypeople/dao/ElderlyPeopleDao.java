package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.ElderlyPeople;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class ElderlyPeopleDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el elderly people a la base de dades */
	public void addElderlyPeople(ElderlyPeople elderlyPeople) {
		jdbcTemplate.update("INSERT INTO ElderlyPeople VALUES(?,?,?,?,?,?,?,?,?,?,?)", elderlyPeople.getAlergies(),
				elderlyPeople.getName(), elderlyPeople.getSurnames(), elderlyPeople.getBirthDate(),
				elderlyPeople.getDiseases(), elderlyPeople.getDni(), elderlyPeople.getAddress(),
				elderlyPeople.getPhoneNumber(), elderlyPeople.getEmail(), elderlyPeople.getBankAccountNumber(),
				elderlyPeople.getSocialWorker());
	}

	/* Esborra el elderly de la base de dades */
	public void deleteElderlyPeople(ElderlyPeople elderlyPeople) {
		jdbcTemplate.update("DELETE FROM Company WHERE fiscalNumber=?", elderlyPeople.getDni());
	}

	// Esborra el elderly per el dni
	public void deleteElderlyPeople(String dni) {
		jdbcTemplate.update("DELETE FROM ElderlyPeople WHERE dni=?", dni);
	}

	// Actualitza els atributs del elderly
	public void updateEldely(ElderlyPeople elderlyPeople) {
		jdbcTemplate.update("UPDATE Elderly SET alergies = ?, name = ?, surnames = ?, birthDate = ?, diseases = ?, "
				+ "address = ?, phoneNumber = ?, email = ?, bankAccountNumber= ?, socialWorker = ?  WHERE dni = ?",
				elderlyPeople.getAlergies(), elderlyPeople.getName(), elderlyPeople.getBirthDate(),
				elderlyPeople.getDiseases(), elderlyPeople.getAddress(), elderlyPeople.getPhoneNumber(),
				elderlyPeople.getEmail(), elderlyPeople.getBankAccountNumber(), elderlyPeople.getSocialWorker(),
				elderlyPeople.getDni());
	}

	/* Obté el elderly */
	public ElderlyPeople getElderlyPeople(String dni) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM ElderlyPeople WHERE dni =?", new ElderlyPeopleRowMapper(), dni);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els ElderlyPeople. Torna una llista buida si no n'hi ha cap. */
	public List<ElderlyPeople> getElderlysPeoples() {
		try {
			return jdbcTemplate.query("SELECT * FROM ElderlyPeople", new ElderlyPeopleRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<ElderlyPeople>();
		}
	}
}
