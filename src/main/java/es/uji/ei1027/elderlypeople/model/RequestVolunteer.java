package es.uji.ei1027.elderlypeople.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class RequestVolunteer {
	HourVolunteer hourVolunteer;
	Volunteer volunteer;
	Search search;
	int age;
	
	
	public HourVolunteer getHourVolunteer() {
		return hourVolunteer;
	}
	public void setHourVolunteer(HourVolunteer hourVolunteer) {
		this.hourVolunteer = hourVolunteer;
	}
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	
	
}
