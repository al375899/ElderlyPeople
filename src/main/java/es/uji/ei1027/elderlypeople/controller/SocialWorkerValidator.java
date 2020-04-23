package es.uji.ei1027.elderlypeople.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.elderlypeople.model.SocialWorker;

public class SocialWorkerValidator implements Validator {
  @Override
  public boolean supports(Class<?> cls) {
	  return SocialWorker.class.equals(cls);
   }
 
  @Override
  public void validate(Object obj, Errors errors) {
	 SocialWorker socialWorker = (SocialWorker)obj;
	 if (socialWorker.getDni().trim().equals(""))
	       errors.rejectValue("dni", "obligatori",
	                          "Cal introduir un valor");	   
   }
}
