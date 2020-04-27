package es.uji.ei1027.elderlypeople.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.elderlypeople.model.ElderlyPeople;

public class ElderlyPeopleValidator implements Validator {
	@Override
	public boolean supports(Class<?> cls) {
		return ElderlyPeople.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ElderlyPeople elderlyPeople = (ElderlyPeople) obj;
		if (elderlyPeople.getDni().trim().equals(""))
			errors.rejectValue("dni", "obligatori", "Cal introduir un valor");
		
		if (elderlyPeople.getName().trim().equals(""))
			errors.rejectValue("name", "obligatori", "Cal introduir un valor");
		
		if (elderlyPeople.getSurnames().trim().equals(""))
			errors.rejectValue("surnames", "obligatori", "Cal introduir un valor");
	}
}
