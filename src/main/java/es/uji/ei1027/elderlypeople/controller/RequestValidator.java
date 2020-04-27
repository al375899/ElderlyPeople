package es.uji.ei1027.elderlypeople.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.elderlypeople.model.Request;

public class RequestValidator implements Validator {
	@Override
	public boolean supports(Class<?> cls) {
		return Request.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Request request = (Request) obj;
		if (request.getIdRequest() == null)
			errors.rejectValue("idRequest", "obligatori", "Cal introduir un valor");
		
		if (request.getDniElderly().trim().equals(""))
			errors.rejectValue("dniElderly", "obligatori", "Cal introduir un valor");
		
		if (request.getIdContract() == null)
			errors.rejectValue("idContract", "obligatori", "Cal introduir un valor");
		
		if (request.getBeginDate() == null)
			errors.rejectValue("beginDate", "obligatori", "Cal introduir un valor");
		
		if (request.getEndDate() == null)
			errors.rejectValue("endDate", "obligatori", "Cal introduir un valor");

	}
}
