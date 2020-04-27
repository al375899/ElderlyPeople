package es.uji.ei1027.elderlypeople.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.elderlypeople.model.Volunteer;

public class VolunteerValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> cls) {
		return Volunteer.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Volunteer volunteer = (Volunteer) obj;
		if (volunteer.getDni().trim().equals(""))
			errors.rejectValue("dni", "obligatori", "Cal introduir un valor");
		
		if (volunteer.getName().trim().equals(""))
			errors.rejectValue("name", "obligatori", "Cal introduir un valor");
		
	}
	

	
}
