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

import es.uji.ei1027.elderlypeople.dao.ElderlyPeopleDao;
import es.uji.ei1027.elderlypeople.model.ElderlyPeople;

@Controller
@RequestMapping("/elderlyPeople")
public class ElderlyPeopleController {

	private ElderlyPeopleDao elderlyPeopleDao;
	
	@Autowired
	public void setElderlyPeopleDao(ElderlyPeopleDao elderlyPeopleDao) {
		this.elderlyPeopleDao = elderlyPeopleDao;
	}

	
	@RequestMapping("/list")
	public String listElderlyPeople(Model model) {
		model.addAttribute("elderlysPeoples", elderlyPeopleDao.getElderlysPeoples());
		return "elderlyPeople/list";
	}
	
	@RequestMapping(value = "/add")
	public String addElderlyPeople(Model model) {
		model.addAttribute("elderlyPeople", new ElderlyPeople());
		return "elderlyPeople/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("elderlyPeople") ElderlyPeople elderlyPeople, BindingResult bindingResult) {
		ElderlyPeopleValidator elderlyPeopleValidator = new ElderlyPeopleValidator();
		elderlyPeopleValidator.validate(elderlyPeople, bindingResult);
		if (bindingResult.hasErrors())
			return "elderlyPeople/add";
		try {
		elderlyPeopleDao.addElderlyPeople(elderlyPeople);
		} catch (DuplicateKeyException e) { 
		    throw new ElderlyPeopleException(  
		         "Ja existeix una persona major amb el dni: "  
		         + elderlyPeople.getDni(), "CPduplicada"); 
		} catch (DataAccessException e) { 
		    throw new ElderlyPeopleException(  
		         "Error en l'accés a la base de dades", "ErrorAccedintDades"); 
		}
		return "redirect:list";
	}
	
	@RequestMapping(value = "/update/{dni}", method = RequestMethod.GET)
	public String editElderlyPeople(Model model, @PathVariable String dni) {
		model.addAttribute("elderlyPeople", elderlyPeopleDao.getElderlyPeople(dni));
		return "elderlyPeople/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("elderlyPeople") ElderlyPeople elderlyPeople, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "elderlyPeople/update";
		elderlyPeopleDao.updateEldely(elderlyPeople);;
		return "redirect:list";
	}
	
	@RequestMapping(value = "/delete/{dni}")
	public String processDelete(@PathVariable String dni) {
		elderlyPeopleDao.deleteElderlyPeople(dni);
		return "redirect:../list";
	}

}
