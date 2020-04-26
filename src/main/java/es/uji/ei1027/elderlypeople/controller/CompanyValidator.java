package es.uji.ei1027.elderlypeople.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.elderlypeople.model.Company;

public class CompanyValidator implements Validator {
  @Override
  public boolean supports(Class<?> cls) {
	  return Company.class.equals(cls);
   }
 
  @Override
  public void validate(Object obj, Errors errors) {
	 Company company = (Company)obj;
	 if (company.getFiscalNumber().trim().equals(""))
	       errors.rejectValue("fiscalNumber", "obligatori", "Cal introduir un valor");
	 
	 if (company.getContactPerson().trim().equals(""))
	       errors.rejectValue("contactPerson", "obligatori", "Cal introduir un valor");
	 
	 if (company.getName().trim().equals(""))
	       errors.rejectValue("name", "obligatori", "Cal introduir un valor");
	 
	 
   }
}
