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

import es.uji.ei1027.elderlypeople.dao.VolunteerDao;
import es.uji.ei1027.elderlypeople.model.UserDetails;
import es.uji.ei1027.elderlypeople.model.Volunteer;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

	private VolunteerDao volunteerDao;
	
	@Autowired
	public void setVolunteerDao(VolunteerDao volunteerDao) {
		this.volunteerDao = volunteerDao;
	}
	
	@RequestMapping("/list")
	public String listVolunteers(Model model) {
		model.addAttribute("volunteers", volunteerDao.getVolunteers());
		return "volunteer/list";
	}
	
	@RequestMapping("/listHoursTaken")
	public String listVolunteersUsers(Model model, HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		model.addAttribute("elderlyList", volunteerDao.getElderlyList(user.getUsername()));
		return "volunteer/listHoursTaken";
	}
	
	@RequestMapping("/listHoursNotTaken")
	public String listVolunteersHours(Model model, HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		model.addAttribute("hoursList", volunteerDao.getHoursList(user.getUsername()));
		return "volunteer/listHoursNotTaken";
	}
	
	@RequestMapping(value = "/add")
	public String addVolunteer(Model model) {
		model.addAttribute("volunteer", new Volunteer());
		return "volunteer/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("volunteer") Volunteer volunteer, BindingResult bindingResult) {
		VolunteerValidator volunteerValidator = new VolunteerValidator();
		volunteerValidator.validate(volunteer, bindingResult);
		if (bindingResult.hasErrors())
			return "volunteer/add";
		try {
		volunteerDao.addVolunteer(volunteer);
		} catch (DuplicateKeyException e) { 
		    throw new ElderlyPeopleException(  
		         "Ja existeix un voluntari amb el dni: "  
		         + volunteer.getDni(), "CPduplicada"); 
		} catch (DataAccessException e) { 
		    throw new ElderlyPeopleException(  
		         "Error en l'accés a la base de dades", "ErrorAccedintDades"); 
		}
		return "redirect:list";
	}
	
	@RequestMapping(value = "/update/{dni}", method = RequestMethod.GET)
	public String editVolunteer(Model model, @PathVariable String dni) {
		model.addAttribute("volunteer", volunteerDao.getVolunteer(dni));
		return "volunteer/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("volunteer") Volunteer volunteer, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "volunteer/update";
		volunteerDao.updateVolunteer(volunteer);;
		return "redirect:list";
	}
	
	// Saca el voluntario a partir de la sesión iniciada
	@RequestMapping(value = "/updateUser", method = RequestMethod.GET)
	public String editVolunteerUser(Model model, HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		model.addAttribute("volunteer", volunteerDao.getVolunteer(user.getUsername()));
		return "volunteer/updateUser";
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String processUpdateSubmitUser(@ModelAttribute("volunteer") Volunteer volunteer, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "volunteer/updateUser";
		volunteerDao.updateVolunteer(volunteer);;
		return "redirect:/index_userVolunteer.html";
	}
	
	@RequestMapping(value = "/delete/{dni}")
	public String processDelete(@PathVariable String dni) {
		volunteerDao.deleteVolunteer(dni);;
		return "redirect:../list";
	}
	
}
