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

import es.uji.ei1027.elderlypeople.dao.SocialWorkerDao;
import es.uji.ei1027.elderlypeople.model.SocialWorker;

@Controller
@RequestMapping("/socialWorker")
public class SocialWorkerController {

	private SocialWorkerDao socialWorkerDao;

	@Autowired
	public void setSocialWorkerDao(SocialWorkerDao socialWorkerDao) {
		this.socialWorkerDao = socialWorkerDao;
	}

	@RequestMapping("/list")
	public String listSocialWorkers(Model model) {
		model.addAttribute("socialWorkers", socialWorkerDao.getSocialWorkers());
		return "socialWorker/list";
	}

	@RequestMapping(value = "/add")
	public String addSocialWorker(Model model) {
		model.addAttribute("socialWorker", new SocialWorker());
		return "socialWorker/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("socialWorker") SocialWorker socialWorker, BindingResult bindingResult) {
		SocialWorkerValidator socialWorkerValidator = new SocialWorkerValidator();
		socialWorkerValidator.validate(socialWorker, bindingResult);
		if (bindingResult.hasErrors())
			return "socialWorker/add";
		try {
			socialWorkerDao.addSocialWorker(socialWorker);
		} catch (DuplicateKeyException e) { 
		    throw new ElderlyPeopleException(  
		         "Ja existeix un treballador social amb el dni: "  
		         + socialWorker.getDni(), "CPduplicada"); 
		} catch (DataAccessException e) { 
		    throw new ElderlyPeopleException(  
		         "Error en l'accés a la base de dades", "ErrorAccedintDades"); 
		}

		return "redirect:list";
	}

	@RequestMapping(value = "/update/{dni}", method = RequestMethod.GET)
	public String editSocialWorker(Model model, @PathVariable String dni) {
		model.addAttribute("socialWorker", socialWorkerDao.getSocialWorker(dni));
		return "socialWorker/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("socialWorker") SocialWorker socialWorker, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "socialWorker/update";
		socialWorkerDao.updateSocialWorker(socialWorker);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{dni}")
	public String processDelete(@PathVariable String dni) {
		socialWorkerDao.deleteSocialWorker(dni);
		return "redirect:../list";
	}
}
