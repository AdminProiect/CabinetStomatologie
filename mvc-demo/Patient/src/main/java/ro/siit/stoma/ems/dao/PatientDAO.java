package patient.ems.dao;

import java.util.Collection;

import patient.ems.domain.Patient;

public interface PatientDAO extends BaseDAO<Patient>{

	Collection<Patient> searchByName(String query) ;
}
