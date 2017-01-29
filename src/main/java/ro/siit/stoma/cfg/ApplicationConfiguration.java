package ro.siit.stoma.aaconfig;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ro.siit.stoma.appointment.dao.AppointmentDAO;
import ro.siit.stoma.appointment.dao.db.JDBCAppointmentDAO;
import ro.siit.stoma.patient.dao.PatientDAO;
import ro.siit.stoma.patient.dao.db.JDBCPatientDAO;
import ro.siit.stoma.treatment.dao.TreatmentDAO;
import ro.siit.stoma.treatment.dao.db.JDBCTreatmentDAO;


@Configuration
public class ApplicationConfiguration {
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");

		String url = new StringBuilder().append("jdbc:").append("postgresql").append("://").append("localhost")
				.append(":").append("5432").append("/").append("stoma").toString();

		dataSource.setUrl(url);
		dataSource.setUsername("postgres");
		dataSource.setPassword("graf559360");
		return dataSource;

	}
	
	@Bean
	public AppointmentDAO appointmentDAO() {
		return new JDBCAppointmentDAO(dataSource());
		
	}
	
	@Bean
	public PatientDAO patientDAO() {
		return new JDBCPatientDAO(dataSource());

	}
	
	@Bean
	public TreatmentDAO treatmentDAO() {
		return new JDBCTreatmentDAO(dataSource());
		
	}
}
