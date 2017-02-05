package patient.ems.cfg;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import patient.ems.dao.PatientDAO;
import patient.ems.dao.db.JDBCPatientDAO;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");

		String url = new StringBuilder().append("jdbc:").append("postgresql").append("://").append("localhost")
				.append(":").append("5432").append("/").append("Patient").toString();

		dataSource.setUrl(url);
		dataSource.setUsername("postgres");
		dataSource.setPassword("proiect");
		return dataSource;

	}

	@Bean
	@Primary
	public PatientDAO patientDAO() {
		return new JDBCPatientDAO(dataSource());

	}
}
