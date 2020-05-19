package es.uji.ei1027.elderlypeople.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Search {
	String day;
	@DateTimeFormat(pattern = "HH:mm")
	LocalTime startHour;
	@DateTimeFormat(pattern = "HH:mm")
	LocalTime endHour;
	
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public LocalTime getStartHour() {
		return startHour;
	}
	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}
	public LocalTime getEndHour() {
		return endHour;
	}
	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}
	
	
}
