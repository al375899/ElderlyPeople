package es.uji.ei1027.elderlypeople.controller;

import java.time.LocalDate;

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
	
	@RequestMapping(value = "/update/{dniElderly}/{dniVolunteer}/{date}", method = RequestMethod.GET)
	public String editHourVolunteer(Model model, @PathVariable String dniElderly, @PathVariable String dniVolunteer, @PathVariable LocalDate date) {
		model.addAttribute("hourVolunteer", hourVolunteerDao.getHourVolunteer(dniElderly, dniVolunteer, date));
		return "hourVolunteer/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("hourVolunteer") HourVolunteer hourVolunteer, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "hourVolunteer/update";
		hourVolunteerDao.updateHourVolunteer(hourVolunteer);;
		return "redirect:list";
	}
	
	@RequestMapping(value = "/delete/{dniElderly}/{dniVolunteer}/{date}")
	public String processDelete(@PathVariable String dniElderly, @PathVariable String dniVolunteer, @PathVariable LocalDate date) {
		hourVolunteerDao.deleteHourVolunteer(dniElderly, dniVolunteer, date);
		return "redirect:../list";
	}
}
