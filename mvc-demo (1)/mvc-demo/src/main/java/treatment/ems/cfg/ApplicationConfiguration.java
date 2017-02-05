package treatment.ems.cfg;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import treatment.ems.dao.TreatmentDAO;
import treatment.ems.dao.db.JDBCTreatmentDAO;


@Configuration
public class ApplicationConfiguration {

	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		String url = new StringBuilder()
				.append("jdbc:")
				.append("postgresql")
				.append("://")
				.append("localhost")
				.append(":")
				.append("5432")
				.append("/")
				.append("ems").toString();
		
		dataSource.setUrl(url);
		dataSource.setUsername("........");
		dataSource.setPassword("........");
		return dataSource;
		
	}
	
	@Bean
	@Primary
	public TreatmentDAO treatmentDAO() {
		return new JDBCTreatmentDAO(dataSource());
		
	}
}