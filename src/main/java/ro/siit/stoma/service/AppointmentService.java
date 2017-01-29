package ro.siit.stoma.appointment.service;

import static java.time.DayOfWeek.MONDAY;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ro.siit.stoma.appointment.dao.AppointmentDAO;
import ro.siit.stoma.appointment.domain.Appointment;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentDAO dao;

	public Collection<Appointment> listAll() {
		return dao.getAll();
	}

	public Collection<Appointment> searchPacientName(String query) {
		return dao.searchByName(query);
	}

	public void saveAppointment(Appointment app, int appId) throws ValidationException {

		app.setSubmitDate(new Date());
		validate(app);
		System.out.println(app.toString());
		dao.update(app);
	}

	public Appointment get(Long id) {
		return dao.findById(id);
	}

	public boolean delete(Long id) {
		Appointment app = dao.findById(id);
		if (app != null) {
			dao.delete(app);
			return true;
		}

		return false;
	}

	private void validate(Appointment app) throws ValidationException {
		Date currentDate = new Date();
		List<String> errors = new LinkedList<String>();

		if (StringUtils.isEmpty(app.getChair())) {
			errors.add("No chair selected!");
		}

		if (StringUtils.isEmpty(app.getPacient())) {
			errors.add("Pacient first name is empty");
		}

		if (StringUtils.isEmpty(app.getPacient())) {
			errors.add("Pacient last name is empty");
		}

		if (app.getStartDate() == null) {
			errors.add("Start date is empty");
		} else if (app.getStartDate().before(currentDate)) {
			errors.add("Start date in the past");
		}

		if (app.getEndDate() == null) {
			errors.add("End date is empty");
		} else if (app.getEndDate().before(app.getStartDate())) {
			errors.add("End date is set before the start date");
		}

		if (!errors.isEmpty()) {
			throw new ValidationException(errors.toArray(new String[] {}));
		}
	}

	public AppointmentDAO getDao() {
		return dao;
	}

	public void setDao(AppointmentDAO dao) {
		this.dao = dao;
	}

	public Map<Integer, Appointment> listAllPer7Days() {
		// get monday of the current week
		LocalDate firstDayOfTheWeek = LocalDate.now().with(previousOrSame(MONDAY));
		List<Date> week = new LinkedList<>();
		
		// get all 7 day by date of the week
		for (int i = 0; i < 7; i++) {
			week.add(java.sql.Date.valueOf(firstDayOfTheWeek.plusDays(i)));
		}

		// get from dao all appointments on the specific date.
		Map<LocalDate, LinkedList<Appointment>> mapDateAndAppointment = new LinkedHashMap<>();
		LinkedList<Appointment> appointmentsPerDay = new LinkedList<>();
		for (Date date : week) {
			appointmentsPerDay = (LinkedList<Appointment>) dao.getAll();
			Instant instant = Instant.ofEpochMilli(date.getTime());
			LocalDate currentDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
			mapDateAndAppointment.put(currentDate, appointmentsPerDay);
		}
		
		// prepare list of appointments for UI table
		Map<Integer, Appointment> toUI = new TreeMap<>();
		Set<LocalDate> keys = mapDateAndAppointment.keySet();
		for (LocalDate key : keys) {
			switch (key.getDayOfWeek().name()) {
			case "MONDAY":
				populateMapForUi(mapDateAndAppointment.get(key), toUI, 1);
				break;
			case "TUESDAY":
				populateMapForUi(mapDateAndAppointment.get(key), toUI, 2);
				break;
			case "WEDNESDAY":
				populateMapForUi(mapDateAndAppointment.get(key), toUI, 3);
				break;
			case "THURSDAY":
				populateMapForUi(mapDateAndAppointment.get(key), toUI, 4);
				break;
			case "FRIDAY":
				populateMapForUi(mapDateAndAppointment.get(key), toUI, 5);
				break;
			case "SATURDAY":
				populateMapForUi(mapDateAndAppointment.get(key), toUI, 6);
				break;
			case "SUNDAY":
				populateMapForUi(mapDateAndAppointment.get(key), toUI, 7);
				break;
			}
		}
		/*System.out.println("toUI->");
		if(toUI.isEmpty()){
			System.out.println("toUI is empty????");
		}
		for (Map.Entry<Integer, Appointment> entry : toUI.entrySet()){
			System.out.println(entry.getKey()+ " -->?????????????? " + entry.toString());
		}*/
		//toUI
		for(int i =0; i < 767; i++){
			if(!toUI.containsKey(i)){
				toUI.put(i, null);
			}
		}
		return toUI;
	}

	/**
	 * @param appointmentsPerDay
	 * @param toUI
	 */
	protected void populateMapForUi(LinkedList<Appointment> appointmentsPerDay, Map<Integer, Appointment> toUI, Integer offset) {
		int durataApp = 0;
		for (Appointment appointment : appointmentsPerDay) {
			Instant instantS = Instant.ofEpochMilli(appointment.getStartDate().getTime());
			LocalDateTime localDS = LocalDateTime.ofInstant(instantS, ZoneId.systemDefault());
			int pozUI = (((localDS.getHour() * 60 + localDS.getMinute()) / 15 * 8) + offset);
			toUI.put(pozUI, appointment);
			
			Instant instantE = Instant.ofEpochMilli(appointment.getEndDate().getTime());
			LocalDateTime localDE = LocalDateTime.ofInstant(instantE, ZoneId.systemDefault());
			
			durataApp = (localDE.getHour() * 60 + localDE.getMinute()) - (localDS.getHour() * 60 + localDS.getMinute()) ;
			while(durataApp > 15){
				toUI.put(pozUI+=8, appointment);
				durataApp = durataApp - 15;
			}
		}
	}

	
}
