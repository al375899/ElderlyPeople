package es.uji.ei1027.elderlypeople.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

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
import es.uji.ei1027.elderlypeople.dao.HourVolunteerDao;
import es.uji.ei1027.elderlypeople.model.BadHoursException;
import es.uji.ei1027.elderlypeople.model.HourVolunteer;
import es.uji.ei1027.elderlypeople.model.Search;
import es.uji.ei1027.elderlypeople.model.UserDetails;

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

	@RequestMapping("/listUser")
	public String listHourVolunteersUser(Model model, HttpSession session) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		model.addAttribute("requestVolunteers", hourVolunteerDao.getHourVolunteersUser(user.getUsername()));
		return "hourVolunteer/listVolunteerRequest";
	}

	@RequestMapping(value = "/add")
	public String addHourVolunteer(Model model) {
		model.addAttribute("hourVolunteer", new HourVolunteer());
		return "hourVolunteer/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("hourVolunteer") HourVolunteer hourVolunteer,
			BindingResult bindingResult) {
		HourVolunteerValidator hourVolunteerValidator = new HourVolunteerValidator();
		hourVolunteerValidator.validate(hourVolunteer, bindingResult);
		if (bindingResult.hasErrors())
			return "hourVolunteer/add";
		try {
			hourVolunteerDao.addHourVolunteer(hourVolunteer);
		} catch (DuplicateKeyException e) {
			throw new ElderlyPeopleException(
					"Ja existeix una persona major amb aquests valors: " + "DNI voluntari: "
							+ hourVolunteer.getDniVolunteer() + "Día: " + hourVolunteer.getDay() + "Hora inici: "
							+ hourVolunteer.getStartHour() + "Hora final: " + hourVolunteer.getEndHour(),
					"CPduplicada");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		}
		return "redirect:list";
	}

	@RequestMapping(value = "/addHour")
	public String addHourVolunteerUser(Model model) {
		model.addAttribute("hourVolunteer", new HourVolunteer());
		return "hourVolunteer/addHour";
	}

	@RequestMapping(value = "/addHour", method = RequestMethod.POST)
	public String processAddSubmitUser(@ModelAttribute("hourVolunteer") HourVolunteer hourVolunteer,
			BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors())
			return "hourVolunteer/addHour";
		try {
			hourVolunteer.setTaken(false);
			hourVolunteer.setDniElderly(null);
			UserDetails user = (UserDetails) session.getAttribute("user");
			hourVolunteer.setDniVolunteer(user.getUsername());
			hourVolunteerDao.addHourVolunteer(hourVolunteer);
			session.setAttribute("message", "Your hour has been created correctly");
			session.setAttribute("reference", "/volunteer/listHoursNotTaken");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		} catch (IllegalArgumentException e) {
			session.setAttribute("message", "This hour is already picked");
			session.setAttribute("reference", "/volunteer/listHoursNotTaken");
		} catch (ArithmeticException e) {
			session.setAttribute("message", "Hours isn't correct. You must correct it");
			session.setAttribute("reference", "/volunteer/listHoursNotTaken");
		}
		return "/notification";
	}

	@RequestMapping(value = "/update/{dniVolunteer}/{day}/{startHour}/{endHour}", method = RequestMethod.GET)
	public String editHourVolunteer(Model model, @PathVariable String dniVolunteer, @PathVariable String day,
			@PathVariable LocalTime startHour, @PathVariable LocalTime endHour) {
		model.addAttribute("hourVolunteer", hourVolunteerDao.getHourVolunteer(dniVolunteer, day, startHour, endHour));
		return "hourVolunteer/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmitUser(@ModelAttribute("hourVolunteer") HourVolunteer hourVolunteer,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "hourVolunteer/update";
		hourVolunteerDao.updateHourVolunteer(hourVolunteer);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{dniVolunteer}/{day}/{startHour}/{endHour}")
	public String processDelete(@PathVariable String dniVolunteer, @PathVariable String day,
			@PathVariable LocalTime startHour, @PathVariable LocalTime endHour) {
		hourVolunteerDao.deleteHourVolunteer(dniVolunteer, day, startHour, endHour);
		return "redirect:/hourVolunteer/list";
	}

	@RequestMapping(value = "/deleteUser/{dniVolunteer}/{day}/{startHour}/{endHour}")
	public String processDeleteUser(@PathVariable String dniVolunteer, @PathVariable String day, @PathVariable LocalTime startHour, @PathVariable LocalTime endHour, HttpSession session) {
		hourVolunteerDao.deleteHourVolunteer(dniVolunteer, day, startHour, endHour);
		session.setAttribute("message", "Your hour has been deleted correctly");
		session.setAttribute("reference", "/volunteer/listHoursNotTaken");
		return "/notification";
	}

	@RequestMapping(value = "/deleteHourTaken/{dniVolunteer}/{day}/{startHour}/{endHour}")
	public String processDeleteHourTaken(@PathVariable String dniVolunteer, @PathVariable String day, @PathVariable LocalTime startHour, @PathVariable LocalTime endHour, HttpSession session) {
		hourVolunteerDao.deleteHourVolunteer(dniVolunteer, day, startHour, endHour);
		session.setAttribute("message", "Your hour has been deleted correctly");
		session.setAttribute("reference", "/volunteer/listHoursTaken");
		return "/notification";
	}

	@RequestMapping(value = "/listFilter")
	public String listFilter(Model model) {
		model.addAttribute("search", new Search());
		return "checkHourCompatibility";
	}

	@RequestMapping(value = "/listFilterUser", method = RequestMethod.POST)
	public String listFilterUser(Model model, @ModelAttribute("search") Search search, BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors())
			return "/checkHourCompatibility";
		try {
			model.addAttribute("requestVolunteers", hourVolunteerDao.getHourVolunteersFilter(search));
			return "hourVolunteer/listVolunteerFilter";
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		} catch (ArithmeticException e) {
			session.setAttribute("message", "Hours isn't correct. You must correct it");
			session.setAttribute("reference", "/checkHourCompatibility");
		}
		return "/notification";
		
	}

	@RequestMapping(value = "/create/{startHourElderly}/{endHourElderly}/{dniVolunteer}/{day}/{startHour}/{endHour}", method = {
			RequestMethod.GET, RequestMethod.PUT })
	public String takeHour(@PathVariable LocalTime startHourElderly, @PathVariable LocalTime endHourElderly,
			@PathVariable String dniVolunteer, @PathVariable String day, @PathVariable LocalTime startHour,
			@PathVariable LocalTime endHour, HttpSession session) {

		UserDetails user = (UserDetails) session.getAttribute("user");
		try {
			hourVolunteerDao.takeHour(startHourElderly, endHourElderly, dniVolunteer, day, startHour, endHour, user.getUsername());
			session.setAttribute("message", "Your request has been created correctly");
			session.setAttribute("reference", "/index_ElderlyPeople.html");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		}
		return "/notification";
	}
}
