package es.uji.ei1027.elderlypeople.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import es.uji.ei1027.elderlypeople.dao.RequestDao;
import es.uji.ei1027.elderlypeople.model.Request;
import es.uji.ei1027.elderlypeople.model.UserDetails;

@Controller
@RequestMapping("/request")
public class RequestController {

	private RequestDao requestDao;

	@Autowired
	public void setRequestDao(RequestDao requestDao) {
		this.requestDao = requestDao;
	}

	@RequestMapping("/list")
	public String listRequest(Model model) {
		model.addAttribute("requests", requestDao.getRequests());
		return "request/list";
	}

	@RequestMapping("/listUser")
	public String listRequestUser(Model model, HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		model.addAttribute("requests", requestDao.getRequestsUser(user.getUsername()));
		return "request/listUser";
	}

	@RequestMapping(value = "/add")
	public String addRequest(Model model) {
		model.addAttribute("request", new Request());
		return "request/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("request") Request request, BindingResult bindingResult) {
		RequestValidator requestValidator = new RequestValidator();
		requestValidator.validate(request, bindingResult);
		if (bindingResult.hasErrors())
			return "request/add";
		try {
			requestDao.addRequest(request);
		} catch (DuplicateKeyException e) {
			throw new ElderlyPeopleException("Ja existeix una solicitud amb el mateix id: " + request.getIdRequest(),
					"CPduplicada");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		}

		return "redirect:list";
	}

	@RequestMapping(value = "/addUser")
	public String addRequestUser(Model model) {
		model.addAttribute("request", new Request());
		return "request/addUser";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String processAddSubmitUser(@ModelAttribute("request") Request request, BindingResult bindingResult,
			HttpSession session) {

		UserDetails user = (UserDetails) session.getAttribute("user");

		if (bindingResult.hasErrors())
			return "request/addUser";
		try {
			requestDao.addRequestUser(request, user.getUsername());
			session.setAttribute("message", "Your request has been created correctly");
			session.setAttribute("reference", "/index_ElderlyPeople.html");
		} catch (IllegalArgumentException e) {
			session.setAttribute("message", "Already exist an accepted or waiting request with this service type");
			session.setAttribute("reference", "/request/addUser");
		}
		return "/notification";
	}

	@RequestMapping(value = "/updateUser/{idRequest}", method = RequestMethod.GET)
	public String editRequest(Model model, @PathVariable Integer idRequest) {
		model.addAttribute("request", requestDao.getRequest(idRequest));
		return "request/updateUser";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String processUpdateSubmitUser(@ModelAttribute("request") Request request, BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors())
			return "request/updateUser";
		requestDao.updateRequestUser(request);
		session.setAttribute("message", "Your request has been updated correctly");
		session.setAttribute("reference", "/request/listUser");
		return "/notification";
	}

	@RequestMapping(value = "/deleteUser/{idRequest}")
	public String processDelete(@PathVariable int idRequest, HttpSession session) {
		try {
			requestDao.deleteRequest(idRequest);
			session.setAttribute("message", "Your request has been deleted correctly");
		} catch (IllegalArgumentException e) {
			session.setAttribute("message", "This request have one or more invoices, so it cannot be deleted");
		}
		session.setAttribute("reference", "/request/listUser");
		return "/notification";
	}

	@RequestMapping(value = "/listFilterApproved")
	public String listFilterAccepted(Model model) {
		model.addAttribute("requestsApproved", requestDao.getRequestsApproved());
		return "/request/listApproved";
	}

	@RequestMapping(value = "/listFilterWaiting")
	public String listFilterWaiting(Model model) {
		model.addAttribute("requestsWaiting", requestDao.getRequestsWaiting());
		return "/request/listWaiting";
	}

	@RequestMapping(value = "/listFilterRejected")
	public String listFilterRejected(Model model) {
		model.addAttribute("requestsRejected", requestDao.getRequestsRejected());
		return "/request/listRejected";
	}

	@RequestMapping(value = "/updateRequest/{idRequest}")
	public String updateRequest(Model model, @PathVariable Integer idRequest) {
		model.addAttribute("request", requestDao.getRequest(idRequest));
		return "request/updateRequest";
	}

	@RequestMapping(value = "/updateRequest", method = RequestMethod.POST)
	public String processUpdateSubmitRequestAccept(@ModelAttribute("request") Request request,
			BindingResult bindingResult, HttpSession session) {

		if (bindingResult.hasErrors())
			return "request/updateRequest";

		requestDao.updateRequestUser(request);
		
		session.setAttribute("message", "Request has been updated correctly");
		
		switch (request.getState()) {
		case "Approved":
			session.setAttribute("reference", "/request/listFilterApproved");
			break;
		case "Waiting":
			session.setAttribute("reference", "/request/listFilterWaiting");
			break;
		case "Rejected":
			session.setAttribute("reference", "/request/listFilterRejected");
			break;
		}
		return "/notification";
	}

	// Le pasa los contratos disponibles al casCommitee
	@RequestMapping(value = "/approvedRequest/{idRequest}")
	public String appoveRequest(Model model, @PathVariable Integer idRequest) {
		model.addAttribute("idRequest", idRequest);
		Request request = requestDao.getRequest(idRequest);
		model.addAttribute("contracts", requestDao.getContracts(request));
		return "/request/acceptAndSelectCompany";
	}
	
	// Acaba de aceptar la peticion desde el casCommitee
	@RequestMapping(value = "/confirmApprove/{idRequest}/{idContract}")
	public String confirmApprove(@PathVariable Integer idRequest, @PathVariable Integer idContract, HttpSession session) {
		String state = requestDao.getRequest(idRequest).getState();
		requestDao.confirmApproveRequest(idRequest, idContract);
		session.setAttribute("message", "Request has been approved correctly");
		switch(state) {
		case "Waiting":
			session.setAttribute("reference", "/request/listFilterWaiting");
			break;
		case "Rejected":
			session.setAttribute("reference", "/request/listFilterRejected");
			break;
		}
		return "/notification";
	}
	
	
	// Pone en espera una peticion desde el casCommitee
	@RequestMapping(value = "/waitRequest/{idRequest}")
	public String waitRequest(@PathVariable Integer idRequest, HttpSession session) {
		Request request = requestDao.getRequest(idRequest);
		requestDao.waitRequest(request);
		session.setAttribute("message", "Request has been waited correctly");
		String state = request.getState();
		switch(state) {
		case "Approved":
			session.setAttribute("reference", "/request/listFilterApproved");
			break;
		case "Rejected":
			session.setAttribute("reference", "/request/listFilterRejected");
			break;
		}
		return "/notification";
	}
	
	// Rechaza una peticion desde el casCommitee
	@RequestMapping(value = "/rejectedRequest/{idRequest}")
	public String rejectRequest(@PathVariable Integer idRequest, HttpSession session) {
		Request request = requestDao.getRequest(idRequest);
		requestDao.rejectRequest(request);
		String state = request.getState();
		session.setAttribute("message", "Request has been rejected correctly");
		switch(state) {
		case "Approved":
			session.setAttribute("reference", "/request/listFilterApproved");
			break;
		case "Waiting":
			session.setAttribute("reference", "/request/listFilterWaiting");
			break;
		}
		return "/notification";
	}
	
	// Borra una request desde el casCommitee
	@RequestMapping(value = "/deleteRequest/{idRequest}")
	public String processDeleteRequest(@PathVariable int idRequest, HttpSession session) {
		Request request = requestDao.getRequest(idRequest);
		String state = request.getState();
		try {
			requestDao.deleteRequest(idRequest);
			session.setAttribute("message", "Request has been deleted correctly");
		} catch (IllegalArgumentException e) {
			session.setAttribute("message", "This request have one or more invoices, so it cannot be deleted");
		}
		switch (state) {
		case "Approved":
			session.setAttribute("reference", "/request/listFilterApproved");
			break;
		case "Waiting":
			session.setAttribute("reference", "/request/listFilterWaiting");
			break;
		case "Rejected":
			session.setAttribute("reference", "/request/listFilterRejected");
			break;
		}
		return "/notification";
		
	}
}