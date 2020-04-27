package es.uji.ei1027.elderlypeople.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.elderlypeople.model.HourVolunteer;

public class HourVolunteerValidator implements Validator {
	@Override
	public boolean supports(Class<?> cls) {
		return HourVolunteer.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		HourVolunteer hourVolunteer = (HourVolunteer) obj;
		if (hourVolunteer.getDniElderly().trim().equals(""))
			errors.rejectValue("dniElderly", "obligatori", "Cal introduir un valor");
		
		if (hourVolunteer.getDniVolunteer().trim().equals(""))
			errors.rejectValue("dniVolunteer", "obligatori", "Cal introduir un valor");
		
		//DUDA, com se fa amb un tipus LocalTime?
		//if (hourVolunteer.getDate().trim().equals(""))
		//	errors.rejectValue("date", "obligatori", "Cal introduir un valor");
	
	}
	
	
}
