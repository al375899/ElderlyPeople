package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.elderlypeople.model.HourVolunteer;
import es.uji.ei1027.elderlypeople.model.RequestVolunteer;
import es.uji.ei1027.elderlypeople.model.Search;
import es.uji.ei1027.elderlypeople.model.Volunteer;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
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
		if (hourVolunteer.getStartHour().compareTo(hourVolunteer.getEndHour()) > 0) {
			throw new ArithmeticException("");
		}
		jdbcTemplate.update("INSERT INTO HourVolunteer VALUES(?,?,?,?,?,?)", hourVolunteer.getDniElderly(),
				hourVolunteer.getDniVolunteer(), hourVolunteer.getDay(), hourVolunteer.getStartHour(),
				hourVolunteer.getEndHour(), hourVolunteer.getTaken());
	}

	/* Esborra el hourvolunteer de la base de dades */
	public void deleteHourVolunteer(HourVolunteer hourVolunteer) {
		jdbcTemplate.update("DELETE FROM HourVolunteer WHERE dniVolunteer=? AND day=? AND startHour=? AND endHour=?",
				hourVolunteer.getDniVolunteer(), hourVolunteer.getDay(), hourVolunteer.getStartHour(), hourVolunteer.getEndHour());
	}

	public void deleteHourVolunteer(String dniVolunteer, String day, LocalTime startHour, LocalTime endHour) {
		jdbcTemplate.update("DELETE FROM HourVolunteer WHERE dniVolunteer=? AND day=? AND startHour=? AND endHour=?", dniVolunteer, day, startHour, endHour);
	}

	/*
	 * Actualitza els atributs del hourvolunteer
	 */
	public void updateHourVolunteer(HourVolunteer hourVolunteer) {
		jdbcTemplate.update(
				"UPDATE HourVolunteer SET dniElderly=?, taken = ? WHERE dniVolunteer=? AND day = ? AND startHour = ? AND endHour = ?",
				hourVolunteer.getDniElderly(), hourVolunteer.getTaken(), hourVolunteer.getDniVolunteer(), hourVolunteer.getDay(), 
				hourVolunteer.getStartHour(), hourVolunteer.getEndHour());
	}

	/* Obté el hourvolunteer */
	public HourVolunteer getHourVolunteer(String dniVolunteer, String day, LocalTime startHour, LocalTime endHour) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM HourVolunteer WHERE dniVolunteer = ? AND day = ? AND startHour = ? AND endHour = ?", 
					new HourVolunteerRowMapper(), dniVolunteer, day, startHour, endHour);
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
	
	public List<RequestVolunteer> getHourVolunteersFilter(Search search) {
		try {
			if (search.getStartHour().compareTo(search.getEndHour()) > 0) {
				throw new ArithmeticException("");
			}
			List<RequestVolunteer> list = new ArrayList<>();
			List<HourVolunteer> listaHoras = jdbcTemplate.query("SELECT * FROM HourVolunteer WHERE taken=false AND day=? AND startHour<=? AND endHour>=?", new HourVolunteerRowMapper(), search.getDay(), search.getStartHour(), search.getEndHour());
			for (HourVolunteer hora : listaHoras) {
				String dniVolunteer = hora.getDniVolunteer();
				Volunteer voluntario = jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE dni = ?", new VolunteerRowMapper(), dniVolunteer);
				RequestVolunteer requestVolunteer = new RequestVolunteer();
				requestVolunteer.setHourVolunteer(hora);
				requestVolunteer.setVolunteer(voluntario);
				requestVolunteer.setSearch(search);
				LocalDate now = LocalDate.now();
				requestVolunteer.setAge(Period.between(voluntario.getBirthDate(),now).getYears());
				list.add(requestVolunteer);
			}
			return list;
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<RequestVolunteer>();
		}
	}
	
	public void takeHour(LocalTime startHourElderly, LocalTime endHourElderly, String dniVolunteer, String day, LocalTime startHour, LocalTime endHour, String dniElderly) {
		System.out.println("Entra al modelo");
		try {
			jdbcTemplate.update(
					"UPDATE HourVolunteer SET taken=true, dniElderly=?, startHour=?, endHour=? WHERE dniVolunteer=? AND day = ? AND startHour = ? AND endHour = ?",
					dniElderly, startHourElderly, endHourElderly, dniVolunteer, day, startHour, endHour);
			System.out.println("Actualiza la bb.dd");
		} catch (Exception e) { // MODIFICAR LOS ERRORES
			e.printStackTrace();
		}
	}
	
	public List<RequestVolunteer> getHourVolunteersUser(String username){
		try {
			List<RequestVolunteer> list = new ArrayList<>();
			List<HourVolunteer> listaHoras = jdbcTemplate.query("SELECT * FROM HourVolunteer WHERE taken=true AND dniElderly=?", new HourVolunteerRowMapper(), username);
			for (HourVolunteer hora : listaHoras) {
				String dniVolunteer = hora.getDniVolunteer();
				Volunteer voluntario = jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE dni = ?", new VolunteerRowMapper(), dniVolunteer);
				RequestVolunteer requestVolunteer = new RequestVolunteer();
				requestVolunteer.setHourVolunteer(hora);
				requestVolunteer.setVolunteer(voluntario);
				LocalDate now = LocalDate.now();
				requestVolunteer.setAge(Period.between(voluntario.getBirthDate(),now).getYears());
				list.add(requestVolunteer);
			}
			return list;
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<RequestVolunteer>();
		}
	}
}