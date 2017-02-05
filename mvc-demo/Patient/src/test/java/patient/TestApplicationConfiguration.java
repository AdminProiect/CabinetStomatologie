package patient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import patient.ems.dao.PatientDAO;
import patient.ems.dao.inmemory.IMPatientDAO;
import patient.ems.service.PatientService;

@Configuration
public class TestApplicationConfiguration {

	@Bean
	public PatientService patientService() {
		return new PatientService();
	}
	
	@Bean
	public PatientDAO patientDAO() {
		return (PatientDAO) new IMPatientDAO();
	
		
	}
}
