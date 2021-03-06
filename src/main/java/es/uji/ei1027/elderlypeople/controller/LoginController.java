package es.uji.ei1027.elderlypeople.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors; 
import org.springframework.validation.Validator;

import es.uji.ei1027.elderlypeople.dao.UserDao; 
import es.uji.ei1027.elderlypeople.model.UserDetails;

class UserValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return UserDetails.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
		UserDetails user = (UserDetails)obj;
		 if(user.getUsername().trim().equals(""))
			 errors.rejectValue("username", "obligatori", "Es necessari introduir un valor");
		 if(user.getPassword().trim().equals(""))
			 errors.rejectValue("password", "obligatori", "Es necessari introduir un valor");
	}
}

@Controller
public class LoginController {
	@Autowired
	private UserDao userDao;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserDetails());
		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") UserDetails user,  		
				BindingResult bindingResult, HttpSession session) {
		UserValidator userValidator = new UserValidator(); 
		userValidator.validate(user, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "login";
		}
	       // Comprova que el login siga correcte 
		// intentant carregar les dades de l'usuari 
		user = userDao.loadUserByUsername(user.getUsername(), user.getPassword()); 
		if (user == null) {
			bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta"); 
			return "login";
		}
		session.setAttribute("user", user); 
		if (user.getType().equals("elderly")) {
			return "redirect:/index_ElderlyPeople.html";
		}
		
		if (user.getType().equals("casManager")) {
			return "redirect:/index_casManager.html";
		}
		
		if (user.getType().equals("casVolunteer")) {
			return "redirect:/index_casVolunteer.html";
		}
		
		if (user.getType().equals("casCommitee")) {
			return "redirect:/index_casComitee.html";
		}
		
		if (user.getType().equals("volunteer")) {
			return "redirect:/index_userVolunteer.html";
		}
		
		
			
		return "redirect:/";
	}

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
}

