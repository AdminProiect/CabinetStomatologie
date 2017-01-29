package ro.siit.stoma.appointment.dao;

import java.util.Collection;
import java.util.Date;

import ro.siit.stoma.appointment.domain.Appointment;


public interface AppointmentDAO extends BaseDAO<Appointment>{

	Collection<Appointment> searchByName(String query);
	
	Collection<Appointment> searchBySameStartDay(Date date);
	
	Collection<Appointment> searchByChair(String chair);
}
