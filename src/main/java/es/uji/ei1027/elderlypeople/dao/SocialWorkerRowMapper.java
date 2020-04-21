package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.SocialWorker;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SocialWorkerRowMapper implements RowMapper<SocialWorker> {
	public SocialWorker mapRow(ResultSet rs, int rowNum) throws SQLException {
		SocialWorker socialWorker = new SocialWorker();

		socialWorker.setName(rs.getString("name"));
		socialWorker.setSurnames(rs.getString("surnames"));
		socialWorker.setPhoneNumber(rs.getString("phoneNumber"));
		socialWorker.setDni(rs.getString("dni")); 
		socialWorker.setEmail(rs.getString("email"));
		return socialWorker;
	}
}
