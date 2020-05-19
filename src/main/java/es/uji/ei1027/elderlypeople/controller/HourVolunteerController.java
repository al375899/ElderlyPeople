package es.uji.ei1027.elderlypeople.controller;

import java.time.LocalTime;

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
import org.springframework.web.bind.annotation.RequestParam;

import es.uji.ei1027.elderlypeople.dao.HourVolunteerDao;
import es.uji.ei1027.elderlypeople.model.HourVolunteer;
import es.uji.ei1027.elderlypeople.model.Search;

@Controller
@RequestMapping("/hourVolunteer")
public class HourVolunteerController {

	private HourVolunteerDao hourVolunteerDao;

	@Autowired
	public void setHourVolunteerDao(HourVolunteerDao hourVolunteerDao) {
		this.hourVolunteerDao = hourVolunteerDao;
	}
	
	@RequestMapping("/list")
	public String listHourVolunteers(Model model) {
		model.addAttribute("hourVolunteers", hourVolunteerDao.getHourVolunteers());
		return "hourVolunteer/list";
	}

	@RequestMapping(value = "/add")
	public String addHourVolunteer(Model model) {
		model.addAttribute("hourVolunteer", new HourVolunteer());
		return "hourVolunteer/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("hourVolunteer") HourVolunteer hourVolunteer, BindingResult bindingResult) {
		HourVolunteerValidator hourVolunteerValidator = new HourVolunteerValidator();
		hourVolunteerValidator.validate(hourVolunteer, bindingResult);
		if (bindingResult.hasErrors())
			return "hourVolunteer/add";
		try {
		hourVolunteerDao.addHourVolunteer(hourVolunteer);
		} catch (DuplicateKeyException e) { 
		    throw new ElderlyPeopleException(  
		         "Ja existeix una persona major amb aquests valors: "  
		         + "DNI voluntari: "+ hourVolunteer.getDniVolunteer() 
		         + "Día: "+ hourVolunteer.getDay()
		         + "Hora inici: " + hourVolunteer.getStartHour()
		    	 + "Hora final: " + hourVolunteer.getEndHour(), "CPduplicada");
		} catch (DataAccessException e) { 
		    throw new ElderlyPeopleException(  
		         "Error en l'accés a la base de dades", "ErrorAccedintDades"); 
		}
		return "redirect:list";
	}
	
	@RequestMapping(value = "/update/{dniVolunteer}/{day}/{startHour}/{endHour}", method = RequestMethod.GET)
	public String editHourVolunteer(Model model, @PathVariable String dniVolunteer, @PathVariable String day, @PathVariable LocalTime startHour, @PathVariable LocalTime endHour) {
		model.addAttribute("hourVolunteer", hourVolunteerDao.getHourVolunteer(dniVolunteer, day, startHour, endHour));
		return "hourVolunteer/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("hourVolunteer") HourVolunteer hourVolunteer, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "hourVolunteer/update";
		hourVolunteerDao.updateHourVolunteer(hourVolunteer);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/delete/{dniVolunteer}/{day}/{startHour}/{endHour}")
	public String processDelete(@PathVariable String dniVolunteer, @PathVariable String day, @PathVariable LocalTime startHour, @PathVariable LocalTime endHour) {
		hourVolunteerDao.deleteHourVolunteer(dniVolunteer, day, startHour, endHour);
		return "redirect:/hourVolunteer/list";
	}
	
	@RequestMapping(value = "/listFilter")
	public String listFilter(Model model) {
		model.addAttribute("search", new Search());
		return "checkHourCompatibility";
	}
	
	@RequestMapping(value = "/listFilterUser", method = RequestMethod.POST)
	public String listFilterUser(Model model, @ModelAttribute("search") Search search, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "/checkHourCompatibility";
		try {
			model.addAttribute("hourVolunteers", hourVolunteerDao.getHourVolunteersFilter(search));
		} catch (DataAccessException e) { 
		    throw new ElderlyPeopleException(  
		         "Error en l'accés a la base de dades", "ErrorAccedintDades"); 
		}
		return "hourVolunteer/listVolunteerFilter";
	}
}
