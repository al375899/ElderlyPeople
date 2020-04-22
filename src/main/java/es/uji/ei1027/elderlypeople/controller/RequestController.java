package es.uji.ei1027.elderlypeople.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
		if (bindingResult.hasErrors())
			return "request/add";
		requestDao.addRequest(request);
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

	@RequestMapping(value = "/delete/{idRequest}{idContract}{dniElderly}")
	public String processDelete(
			@PathVariable Integer idRequest, 
			@PathVariable Integer idContract,
			@PathVariable String dniElderly) {
		requestDao.deleteRequest(idRequest, idContract, dniElderly);
		return "redirect:../list";
	}

}