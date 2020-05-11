package es.uji.ei1027.elderlypeople.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class HourVolunteer {
	String dniElderly;
	String dniVolunteer;
	String day;
	@DateTimeFormat(pattern = "HH:mm")
	LocalTime startHour;
	@DateTimeFormat(pattern = "HH:mm")
	LocalTime endHour;
	Boolean taken;

	public HourVolunteer() {
		super();
	}

	public String getDniElderly() {
		return dniElderly;
	}

	public void setDniElderly(String dniElderly) {
		this.dniElderly = dniElderly;
	}

	public String getDniVolunteer() {
		return dniVolunteer;
	}

	public void setDniVolunteer(String dniVolunteer) {
		this.dniVolunteer = dniVolunteer;
	}

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

	public Boolean getTaken() {
		return taken;
	}

	public void setTaken(Boolean taken) {
		this.taken = taken;
	}

	@Override
	public String toString() {
		return "HourVolunteer [dniElderly=" + dniElderly + ", dniVolunteer=" + dniVolunteer + ", day=" + day
				+ ", startHour=" + startHour + ", endHour=" + endHour + ", taken=" + taken + "]";
	}

}
