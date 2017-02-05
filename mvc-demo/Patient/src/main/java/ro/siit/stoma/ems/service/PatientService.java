package patient.ems.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import patient.ems.dao.PatientDAO;
import patient.ems.domain.Patient;

@Service
public class PatientService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);

	@Autowired
	private PatientDAO dao;

	@Transactional
	public Collection<Patient> listAll() {
		return dao.getAll();
	}

	public Collection<Patient> search(String query) {
		LOGGER.debug("Searching for " + query);
		return dao.searchByName(query);
	}

	public boolean delete(Patient model) {
		LOGGER.debug("Deleting patient for id: " + model.getId());
		Patient pat = dao.findById(model.getId());
		if (pat != null) {
			dao.delete(model);
			return true;
		}

		return false;
	}

	public Patient get(int id) {
		LOGGER.debug("Getting patient for id: " + id);
		return dao.findById(id);

	}

	public void save(Patient patient, int patientId) throws ValidationException {
		LOGGER.debug("Saving: " + patient);
		validate(patient);

		dao.update(patient, patientId);
	}

	private void validate(Patient patient) throws ValidationException {
		//Date currentDate = new Date();
		List<String> errors = new LinkedList<String>();
		if (StringUtils.isEmpty(patient.getFirstName())) {
			errors.add("First Name is Empty");
		}

		if (StringUtils.isEmpty(patient.getLastName())) {
			errors.add("Last Name is Empty");
		}

		if (patient.getGender() == null) {
			errors.add("Gender is Empty");
		}

		if (StringUtils.isEmpty(patient.getCnp())) {
			errors.add("Cnp is Empty");
		}

		if (patient.getAddress() == null) {
			errors.add("Adress is Empty");
		}

		if (patient.getDoctorInCharge() == null) {
			errors.add("DoctorInCharge is Empty");
		}

		if (patient.getEmail() == null) {
			errors.add("Email is Empty");

		}
		if (patient.getModifiedAt() == null) {
			errors.add("Modification date is Empty");

		}

		if (patient.getTelephone() == null) {
			errors.add("Telephone is Empty");

		}

		if (patient.getVisitReason() == null) {
			errors.add("VisitReason is Empty");

		}
		
		if (patient.getPatientPathology() == null) {
			errors.add("PatientPathology is Empty");

		}
		if (patient.getPatientPhysiology() == null) {
			errors.add("PatientPhysiology is Empty");
		}
		if (patient.getDateRegistration() == null) {
			errors.add("DateRegistration is Empty");
		} else {
			if (patient.getDateRegistration()==patient.getModifiedAt()){
				errors.add("DateRegistration has to be before Modification Date");
			}
		}

		if (!errors.isEmpty()) {
			throw new ValidationException(errors.toArray(new String[] {}));
		}
	}

	public PatientDAO getDao() {
		return dao;
	}

	public void setDao(PatientDAO dao) {
		this.dao = dao;
	}

}
