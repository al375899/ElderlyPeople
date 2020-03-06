package es.uji.ei1027.elderlypeople.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.elderlypeople.model.Contract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ContractRowMapper implements RowMapper<Contract> {
	public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contract contract = new Contract();
		contract.setIdContract(rs.getInt("idContract"));
		contract.setFnCompany(rs.getString("fnCompany"));
		contract.setServiceType(rs.getString("serviceType"));
		contract.setQuantity(rs.getInt("quantity"));
		contract.setStartDate(rs.getObject("startDate",LocalDate.class));
		contract.setEndDate(rs.getObject("endDate",LocalDate.class));
		contract.setPrice(rs.getInt("price"));
		return contract;
	}
}