package es.uji.ei1027.elderlypeople.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.elderlypeople.model.Contract;

public class ContractValidator implements Validator {
  @Override
  public boolean supports(Class<?> cls) {
	  return Contract.class.equals(cls);
   }
 
  @Override
  public void validate(Object obj, Errors errors) {
	  Contract contract = (Contract)obj;
	  
	  if (contract.getFnCompany().trim().equals(""))
	       errors.rejectValue("fnCompany", "obligatori", "Cal introduir un valor");
	  
	  if (contract.getIdContract()==null)
	       errors.rejectValue("idContract", "obligatori", "Cal introduir un valor");
	  
	  if (contract.getEndDate() == null)
	       errors.rejectValue("endDate", "obligatori", "Cal introduir un valor");
	  
	  if (contract.getStartDate()==null)
	       errors.rejectValue("startDate", "obligatori", "Cal introduir un valor");
	  
	  if (contract.getQuantity()==null)
	       errors.rejectValue("quantity", "obligatori", "Cal introduir un valor");
	  
	  if (contract.getPrice()==null)
	       errors.rejectValue("price", "obligatori", "Cal introduir un valor");
 
	  
   }
}
