package es.uji.ei1027.elderlypeople.controller;

public class ElderlyPeopleException extends RuntimeException {

	   String message;
	   String errorName;     

	   public ElderlyPeopleException(String message, String errorName)
	   {
	       this.message=message;
	       this.errorName=errorName;
	   }

	   public String getMessage() {
	       return message;
	   }

	   public void setMessage(String message) {
	       this.message = message;
	   }

	   public String getErrorName() {
	       return errorName;
	   }

	   public void setErrorName(String errorName) {
	       this.errorName = errorName;
	   }
	}

