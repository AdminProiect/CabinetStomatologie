package ro.siit.stoma.appointment.dao.inmemory;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ro.siit.stoma.appointment.dao.AppointmentDAO;
import ro.siit.stoma.appointment.domain.Appointment;

//@Component
public class IMAppointmentDAO extends IMBaseDAO<Appointment> implements AppointmentDAO {
	
	@Override
	public Collection<Appointment> searchByName(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		
		Collection<Appointment> all = new LinkedList<Appointment>(getAll());
		for (Iterator<Appointment> it = all.iterator(); it.hasNext();) {
			Appointment app = it.next();
			String ss = app.getPacient() + " " + app.getPacient();
			if (!ss.toLowerCase().contains(query.toLowerCase())) {
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Collection<Appointment> searchBySameStartDay(Date date) {
		if (StringUtils.isEmpty(date)) {
			return getAll();
		}
		
		Collection<Appointment> all = new LinkedList<Appointment>(getAll());
		for (Iterator<Appointment> it = all.iterator(); it.hasNext();) {
			Appointment app = it.next();
			
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			if(!fmt.format(app.getStartDate()).equals(fmt.format(date))){   //old way: if (!app.getStartDate().equals(date)) {
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Collection<Appointment> searchByChair(String chair) {
		if (StringUtils.isEmpty(chair)) {
			return getAll();
		}
		String ch = chair.toUpperCase();
		Collection<Appointment> all = new LinkedList<Appointment>(getAll());
		for (Iterator<Appointment> it = all.iterator(); it.hasNext();) {
			Appointment app = it.next();
			
			if (!app.getChair().equals(ch)) {
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Appointment findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appointment update(Appointment model) {
		// TODO Auto-generated method stub
		return null;
	}
}
