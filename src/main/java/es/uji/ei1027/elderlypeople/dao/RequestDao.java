package es.uji.ei1027.elderlypeople.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.elderlypeople.model.Contract;
import es.uji.ei1027.elderlypeople.model.Line;
import es.uji.ei1027.elderlypeople.model.Request;
import es.uji.ei1027.elderlypeople.model.UserDetails;

import javax.sql.DataSource;

import java.time.LocalDate;
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
	
	public void addRequestUser(Request request, String dniElderly) {
		request.setState("Waiting");
		String comando = "SELECT * FROM Request WHERE idRequest = (SELECT MAX(idRequest) FROM Request)";
		
		Request prueba = jdbcTemplate.queryForObject(comando, new RequestRowMapper());
		int cont = prueba.getIdRequest();
		request.setIdRequest(cont+1);
		request.setDateApprobation(null);
		request.setIdContract(null);
		request.setDniElderly(dniElderly);
			
		
		
		String comando2="SELECT * FROM Request WHERE dniElderly = ? AND (state = 'Approved' OR state = 'Waiting')";
		List <Request> aceptadas = jdbcTemplate.query(comando2, new RequestRowMapper(), request.getDniElderly());
		
		for (Request solicitud : aceptadas) {
			if(solicitud.getServiceType().equals(request.getServiceType())) {
				throw new IllegalArgumentException();
			}
		}
		
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
		List<Line> list = jdbcTemplate.query("SELECT * FROM Line WHERE idRequest = ?", new LineRowMapper(), idRequest);
		if (list.size() > 0) {
			throw new IllegalArgumentException();
		}
		jdbcTemplate.update("DELETE FROM Request WHERE idRequest=?", idRequest );
	}

	// Actualitza els atributs del Request
	public void updateRequestUser(Request request) {
		jdbcTemplate.update("UPDATE Request SET beginDate = ?, endDate = ?, comments = ? WHERE idRequest=?",
				request.getBeginDate(),
				request.getEndDate(),
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
	
	
	public List<Request> getRequestsUser(String dniElderly) {
		try {
			return jdbcTemplate.query("SELECT * FROM Request WHERE dniElderly=?", new RequestRowMapper(), dniElderly);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Request>();
		}
	}
	
	public List<Request> getRequestsApproved (){
		try {
			return jdbcTemplate.query("SELECT * FROM Request WHERE state = 'Approved'", new RequestRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Request>();
		}
	}
	
	public List<Request> getRequestsWaiting (){
		try {
			return jdbcTemplate.query("SELECT * FROM Request WHERE state = 'Waiting'", new RequestRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Request>();
		}
	}
	
	public List<Request> getRequestsRejected (){
		try {
			return jdbcTemplate.query("SELECT * FROM Request WHERE state = 'Rejected'", new RequestRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Request>();
		}
	}
	
	public List<Contract> getContracts(Request request){
		try {
			return jdbcTemplate.query("SELECT * FROM Contract WHERE serviceType = ? AND available > 0", new ContractRowMapper(), request.getServiceType());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Contract>();
		}
	}
	
	public void confirmApproveRequest(Integer idRequest, Integer idContract) {
		Contract contract = jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idContract = ?", new ContractRowMapper(), idContract);
		System.out.println(contract);
		jdbcTemplate.update("UPDATE Contract SET available = ? WHERE idContract = ?", contract.getAvailable() - 1, idContract);
		jdbcTemplate.update("UPDATE Request SET state = 'Approved', dateApprobation = ?, idContract = ? WHERE idRequest = ?", LocalDate.now(), idContract, idRequest);	
	}
	
	public void waitRequest(Request request) {
		String state = request.getState();
		if (state.equals("Approved")) {
			Contract contract = jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idContract = ?", new ContractRowMapper(), request.getIdContract());
			jdbcTemplate.update("UPDATE Contract SET available = ? WHERE idContract = ?", contract.getAvailable() + 1, request.getIdContract());
			jdbcTemplate.update("UPDATE Request SET idContract = null, dateApprobation = null, state = 'Waiting' WHERE idRequest = ?", request.getIdRequest());
		} else if (state.equals("Rejected")) {
			jdbcTemplate.update("UPDATE Request SET state = 'Waiting' WHERE idRequest = ?", request.getIdRequest());
		}
	}
	
	public void rejectRequest(Request request) {
		String state = request.getState();
		if (state.equals("Approved")) {
			Contract contract = jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idContract = ?", new ContractRowMapper(), request.getIdContract());
			jdbcTemplate.update("UPDATE Contract SET available = ? WHERE idContract = ?", contract.getAvailable() + 1, request.getIdContract());
			jdbcTemplate.update("UPDATE Request SET idContract = null, dateApprobation = null, state = 'Rejected' WHERE idRequest = ?", request.getIdRequest());
		} else if (state.equals("Waiting")) {
			jdbcTemplate.update("UPDATE Request SET state = 'Rejected' WHERE idRequest = ?", request.getIdRequest());
		}
	}
}
