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
	public String processAddSubmitUser(@ModelAttribute("request") Request request, BindingResult bindingResult, HttpSession session) {
		
		UserDetails user = (UserDetails) session.getAttribute("user");
		
		
		if (bindingResult.hasErrors())
			return "request/addUser";
		try {
			requestDao.addRequestUser(request, user.getUsername());
		} catch (DuplicateKeyException e) {
			throw new ElderlyPeopleException("Ja existeix una solicitud amb el mateix id: " + request.getIdRequest(),
					"CPduplicada");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		} catch (IllegalArgumentException e) {
			throw new ElderlyPeopleException("Already exist an accepted request with this service type",
					"RequestDuplicada");
		}

		return "redirect:/index_ElderlyPeople.html";
	}

	@RequestMapping(value = "/updateUser/{idRequest}", method = RequestMethod.GET)
	public String editRequest(Model model, @PathVariable Integer idRequest) {
		model.addAttribute("request", requestDao.getRequest(idRequest));
		return "request/updateUser";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String processUpdateSubmitUser(@ModelAttribute("request") Request request, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "request/updateUser";
		requestDao.updateRequestUser(request);
		return "redirect:listUser";
	}

	@RequestMapping(value = "/deleteUser/{idRequest}")
	public String processDelete(@PathVariable int idRequest) {
		requestDao.deleteRequest(idRequest);
		return "redirect:/request/listUser";
	}
	
	@RequestMapping(value = "/listFilterAccepted")
	public String listFilterAccepted(Model model) {
		model.addAttribute("requestsAccepted", requestDao.getRequestsAccepted());
		return "/request/listAccepted";
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
	public String processUpdateSubmitRequest(@ModelAttribute("request") Request request, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "request/updateRequest";
		requestDao.updateRequestUser(request);
		
		return "redirect:/manageElderlyRequests.html";
	}

	@RequestMapping(value = "/updateRequest", method = RequestMethod.POST)
	public String processUpdateSubmitRequestAccept(@ModelAttribute("request") Request request, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "request/updateRequest";
		requestDao.updateRequestUser(request);
		switch(request.getState()) {
		case "Accepted":
			return "/request/listAccepted";
		case "Waiting":
			return "/request/listWaiting";
		case "Rejected":
			return "/request/listRejected";
		}
		return "/manageElderlyRequests";
	}
	
	@RequestMapping(value = "/acceptedRequest/{idRequest}", method = RequestMethod.POST)
	public String acceptRequest(Model model, @PathVariable Integer idRequest) {
		model.addAttribute("idRequest", idRequest);
		Request request = requestDao.getRequest(idRequest);
		model.addAttribute("contracts", requestDao.getContracts(request));
		return "";
	}

}