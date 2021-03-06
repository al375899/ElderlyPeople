package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.elderlypeople.model.Contract;
import es.uji.ei1027.elderlypeople.model.Request;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class ContractDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el contract a la base de dades */
	public void addContract(Contract contract) {
		jdbcTemplate.update("INSERT INTO Contract VALUES(?,?,?,?,?,?,?,?)", contract.getIdContract(),
				contract.getFnCompany(), contract.getServiceType(), contract.getQuantity(), contract.getAvailable(),  contract.getStartDate(),
				contract.getEndDate(), contract.getPrice());
	}
	
	public void addContractUser(Contract contract) {
		if (contract.getStartDate().compareTo(contract.getEndDate()) > 0) {
			throw new ArithmeticException("");
		}
		String comando = "SELECT * FROM Contract WHERE idContract = (SELECT MAX(idContract) FROM Contract)";
		List<Contract> contracts = jdbcTemplate.query("SELECT * FROM Contract", new ContractRowMapper());
		if (contracts.size() == 0) {
			contract.setIdContract(1);
		} else {
			Contract prueba = jdbcTemplate.queryForObject(comando, new ContractRowMapper());
			int cont = prueba.getIdContract();
			contract.setIdContract(cont+1);
		}
		contract.setAvailable(contract.getQuantity());
		
		jdbcTemplate.update("INSERT INTO Contract VALUES(?,?,?,?,?,?,?,?)", contract.getIdContract(),
				contract.getFnCompany(), contract.getServiceType(), contract.getQuantity(), contract.getAvailable(),  contract.getStartDate(),
				contract.getEndDate(), contract.getPrice());
	}

	/* Esborra el contract de la base de dades */
	public void deleteContract(Contract contract) {
		jdbcTemplate.update("DELETE FROM Contract WHERE idContract=?", contract.getIdContract());
	}

	public void deleteContract(Integer idContract) {
		Contract contract = jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idContract = ?", new ContractRowMapper(), idContract);
		if (contract.getQuantity() != contract.getAvailable()) {
			throw new IllegalArgumentException();
		}
		jdbcTemplate.update("DELETE FROM Contract WHERE idContract=?", idContract);
	}

	/*
	 * Actualitza els atributs del contract
	 */
	public void updateContract(Contract contract) {
		jdbcTemplate.update(
				"UPDATE Contract SET fnCompany = ?, serviceType = ?, quantity = ?, startDate = ?, endDate = ?, price = ? WHERE idContract = ?",
				contract.getFnCompany(), contract.getServiceType(), contract.getQuantity(), contract.getStartDate(),
				contract.getEndDate(), contract.getPrice(), contract.getIdContract());
	}

	/* Obté el contract */
	public Contract getContract(Integer idContract) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idContract =?", new ContractRowMapper(), idContract);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els contract. Torna una llista buida si no n'hi ha cap. */
	public List<Contract> getContracts() {
		try {
			return jdbcTemplate.query("SELECT * FROM Contract", new ContractRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Contract>();
		}
	}
	
	public List<Contract> getContractsUser(String fnCompany) {
		try {
			return jdbcTemplate.query("SELECT * FROM Contract WHERE fnCompany=? ORDER BY 5", new ContractRowMapper(), fnCompany);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Contract>();
		}
	}
	
	public void allowCreateContract(String fnCompany) {
		List<Contract> contracts = jdbcTemplate.query("SELECT * FROM Contract WHERE fnCompany = ?", new ContractRowMapper(), fnCompany);
		if (contracts.size() == 0) {
			return;
		} else {
			Contract contract = jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE fnCompany = ? AND endDate IN (SELECT MAX(endDate) FROM Contract WHERE fnCompany = ?)", new ContractRowMapper(), fnCompany, fnCompany);
			if (contract.getEndDate().compareTo(LocalDate.now()) >= 0) {
				throw new IllegalArgumentException();
			}
		}
		
	}
}