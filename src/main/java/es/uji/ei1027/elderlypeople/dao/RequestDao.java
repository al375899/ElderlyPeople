package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.Request;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class RequestDao {

	private JdbcTemplate jdbcTemplate;

	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* Afegeix el request people a la base de dades */
	public void addRequest(Request request) {
		jdbcTemplate.update("INSERT INTO Request VALUES(?,?,?,?,?,?,?,?,?)", 
				request.getIdRequest(),
				request.getServiceType(),
				request.getState(),
				request.getBeginDate(),
				request.getEndDate(),
				request.getDateApprobation(),
				request.getComments(),
				request.getIdContract(),
				request.getDniElderly()
				);
	}
	
	public void addRequestUser(Request request) {
		request.setState("Waiting");
		String comando = "SELECT * FROM Request WHERE idRequest = (SELECT MAX(idRequest) FROM Request)";
		Request prueba = jdbcTemplate.queryForObject(comando, new RequestRowMapper());
		int cont = prueba.getIdRequest();
		request.setIdRequest(cont+1);
		request.setDateApprobation(null);
		jdbcTemplate.update("INSERT INTO Request VALUES(?,?,?,?,?,?,?,?,?)", 
				request.getIdRequest(),
				request.getServiceType(),
				request.getState(),
				request.getBeginDate(),
				request.getEndDate(),
				request.getDateApprobation(),
				request.getComments(),
				request.getIdContract(),
				request.getDniElderly()
				);
	}

	/* Esborra el Request de la base de dades */
	public void deleteRequest(Request request) {
		jdbcTemplate.update("DELETE FROM Request WHERE idRequest=? AND idContract=? AND dniElderly=?", 
				request.getIdRequest(),
				request.getIdContract(),
				request.getDniElderly()
				);
	}

	// Esborra el Request per el id
	public void deleteRequest(Integer idRequest) {
		jdbcTemplate.update("DELETE FROM Request WHERE idRequest=?", 
				idRequest 
				);
	}

	// Actualitza els atributs del Request
	public void updateRequest(Request request) {
		jdbcTemplate.update("UPDATE Request SET serviceType = ?, state = ?, beginDate = ?, endDate = ?, dateApprobation = ?, "
				+ "comments = ? WHERE idRequest=?",
				request.getServiceType(),
				request.getState(),
				request.getBeginDate(),
				request.getEndDate(),
				request.getDateApprobation(),
				request.getComments(),
				
				request.getIdRequest()
				);
	}

	/* Obté el Request */
	public Request getRequest(Integer idRequest) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE idRequest =?", new RequestRowMapper(), idRequest);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* Obté tots els Requests. Torna una llista buida si no n'hi ha cap. */
	public List<Request> getRequests() {
		try {
			return jdbcTemplate.query("SELECT * FROM Request", new RequestRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Request>();
		}
	}
}
