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
	
	//String dniElderly, String day, LocalTime startHour, LocalTime endHour
	public void validate(Object obj, Errors errors) {
		HourVolunteer hourVolunteer = (HourVolunteer) obj;
		if (hourVolunteer.getDniElderly().trim().equals(""))
			errors.rejectValue("dniElderly", "obligatori", "Cal introduir un valor");
		
		if (hourVolunteer.getDay().trim().equals(""))
			errors.rejectValue("day", "obligatori", "Cal introduir un valor");
		
		if (hourVolunteer.getStartHour()==null)
			errors.rejectValue("startHour", "obligatori", "Cal introduir un valor");
		
		if (hourVolunteer.getEndHour()==null)
			errors.rejectValue("endHour", "obligatori", "Cal introduir un valor");
	
	}
	
	
}
