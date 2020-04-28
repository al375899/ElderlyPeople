package es.uji.ei1027.elderlypeople.controller;

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
			throw new ElderlyPeopleException(
					"Ja existeix una solicitud amb el mateix id: " + request.getIdRequest(),
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
	public String processAddSubmitUser(@ModelAttribute("request") Request request, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "request/addUser";
		try {
			requestDao.addRequestUser(request);
		} catch (DuplicateKeyException e) {
			throw new ElderlyPeopleException(
					"Ja existeix una solicitud amb el mateix id: " + request.getIdRequest(),
					"CPduplicada");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		}
		return "redirect:list";
	}

	@RequestMapping(value = "/update/{idRequest}", method = RequestMethod.GET)
	public String editRequest(Model model, @PathVariable Integer idRequest) {
		model.addAttribute("request", requestDao.getRequest(idRequest));
		return "request/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("request") Request request, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "request/update";
		requestDao.updateRequest(request);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{idRequest}")
	public String processDelete(@PathVariable int idRequest) {
		requestDao.deleteRequest(idRequest);
		return "redirect:../list";
	}


}