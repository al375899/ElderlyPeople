package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.ElderlyList;
import es.uji.ei1027.elderlypeople.model.ElderlyPeople;
import es.uji.ei1027.elderlypeople.model.HourVolunteer;
import es.uji.ei1027.elderlypeople.model.Volunteer;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.time.Period;
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
				volunteer.getPhoneNumber(), volunteer.getGender(), volunteer.getBirthDate());
	}

	/* Esborra el volunteer de la base de dades */
	public void deleteVolunteer(Volunteer volunteer) {
		jdbcTemplate.update("DELETE FROM Volunteer WHERE dni=?", volunteer.getDni());
	}

	public void deleteVolunteer(String dni) {
		jdbcTemplate.update("DELETE FROM Volunteer WHERE dni=?", dni);
	}

	/*
	 * Actualitza els atributs del volunteer
	 */
	public void updateVolunteer(Volunteer volunteer) {
		jdbcTemplate.update("UPDATE Volunteer SET requestDate = ?, acceptationDate = ?, endDate = ?, name = ?, phoneNumber = ?, gender = ?, birthDate = ? WHERE dni = ?",
				volunteer.getRequestDate(), volunteer.getAcceptationDate(), volunteer.getEndDate(), volunteer.getName(),
				volunteer.getPhoneNumber(), volunteer.getGender(), volunteer.getBirthDate(), volunteer.getDni());
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
	
	public List<ElderlyList> getElderlyList(String username){
		try {
			List<ElderlyList> list = new ArrayList<>();
			List<HourVolunteer> listaHoras = jdbcTemplate.query("SELECT * FROM HourVolunteer WHERE taken=true AND dniVolunteer = ?", new HourVolunteerRowMapper(), username);
			for (HourVolunteer hora : listaHoras) {
				String dniElderly = hora.getDniElderly();
				ElderlyPeople elderly = jdbcTemplate.queryForObject("SELECT * FROM ElderlyPeople WHERE dni=?", new ElderlyPeopleRowMapper(), dniElderly);
				ElderlyList elderlyList = new ElderlyList();
				elderlyList.setHourVolunteer(hora);
				elderlyList.setElderly(elderly);
				LocalDate now = LocalDate.now();
				elderlyList.setAge(Period.between(elderly.getBirthDate(),now).getYears());
				list.add(elderlyList);
			}
			return list;
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<ElderlyList>();
		}
	}
	
	public List<HourVolunteer> getHoursList(String username){
		try {
			return jdbcTemplate.query("SELECT * FROM HourVolunteer WHERE taken=false AND dniVolunteer = ?", new HourVolunteerRowMapper(), username);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<HourVolunteer>();
		}
	}
}
