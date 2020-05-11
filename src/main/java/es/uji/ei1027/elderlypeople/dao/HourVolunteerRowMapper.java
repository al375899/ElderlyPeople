package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.HourVolunteer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public final class HourVolunteerRowMapper implements RowMapper<HourVolunteer> {
	public HourVolunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
		HourVolunteer hourVolunteer = new HourVolunteer();
		hourVolunteer.setDniElderly(rs.getString("dniElderly"));
		hourVolunteer.setDniVolunteer(rs.getString("dniVolunteer"));
		hourVolunteer.setDay(rs.getString("day"));
		hourVolunteer.setStartHour(rs.getObject("startHour",LocalTime.class));
		hourVolunteer.setEndHour(rs.getObject("EndHour",LocalTime.class));
		hourVolunteer.setTaken(rs.getBoolean("taken"));
		return hourVolunteer;
	}
}