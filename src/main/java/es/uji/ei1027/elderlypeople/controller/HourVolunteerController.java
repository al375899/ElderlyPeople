package es.uji.ei1027.elderlypeople.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.elderlypeople.dao.HourVolunteerDao;
import es.uji.ei1027.elderlypeople.model.HourVolunteer;

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
		if (bindingResult.hasErrors())
			return "hourVolunteer/add";
		hourVolunteerDao.addHourVolunteer(hourVolunteer);;
		return "redirect:list";
	}
	
	@RequestMapping(value = "/update/{dni}", method = RequestMethod.GET)
	public String editHourVolunteer(Model model, @PathVariable String dniElderly, String dniVolunteer) {
		model.addAttribute("socialWorker", hourVolunteerDao.getHourVolunteer(dniElderly, dniVolunteer)); //DUDAA y la fecha? taibién forma parte de la clave primaria
		return "hourVolunteer/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("hourVolunteer") HourVolunteer hourVolunteer, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "hourVolunteer/update";
		hourVolunteerDao.updateHourVolunteer(hourVolunteer);;
		return "redirect:list";
	}
	
	//DUDAA:
	// y la fecha? taibién forma parte de la clave primaria
	@RequestMapping(value = "/delete/{dni}")
	public String processDelete(@PathVariable String dniElderly, String dniVolunteer) {
		hourVolunteerDao.deleteHourVolunteer(dniElderly, dniVolunteer);
		return "redirect:../list";
	}
}
