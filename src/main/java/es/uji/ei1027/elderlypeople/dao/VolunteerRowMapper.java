package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.Volunteer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class VolunteerRowMapper implements RowMapper<Volunteer> {
	public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Volunteer volunteer = new Volunteer();
		volunteer.setDni(rs.getString("dni"));
		volunteer.setRequestDate(rs.getObject("requestDate",LocalDate.class));
		volunteer.setAcceptationDate(rs.getObject("acceptationDate",LocalDate.class));
		volunteer.setEndDate(rs.getObject("endDate",LocalDate.class));
		volunteer.setName(rs.getString("name"));
		volunteer.setPhoneNumber(rs.getString("phoneNumber"));
		volunteer.setGender(rs.getString("gender"));
		volunteer.setBirthDate(rs.getObject("birthDate",LocalDate.class));
		return volunteer;
	}
}