package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.ElderlyPeople;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ElderlyPeopleRowMapper implements RowMapper<ElderlyPeople> {
	public ElderlyPeople mapRow(ResultSet rs, int rowNum) throws SQLException {
		ElderlyPeople elderlyPeople = new ElderlyPeople();
		
		elderlyPeople.setAlergies(rs.getString("alergies"));
		elderlyPeople.setName(rs.getString("name"));
		elderlyPeople.setSurname(rs.getString("surname"));
		elderlyPeople.setBirthDate(rs.getObject("birthDate", LocalDate.class)); // localdate y date
		elderlyPeople.setDiseases(rs.getString("diseases"));
		elderlyPeople.setDni(rs.getString("dni"));
		elderlyPeople.setAddress(rs.getString("address"));
		elderlyPeople.setPhoneNumber(rs.getString("phoneNumber"));
		elderlyPeople.setEmail(rs.getString("email"));
		elderlyPeople.setBanckAccountNumber(rs.getString("bankAccountNumber"));
		elderlyPeople.setSocialWorker(rs.getString("socialWorker"));

		return elderlyPeople;
	}
}
