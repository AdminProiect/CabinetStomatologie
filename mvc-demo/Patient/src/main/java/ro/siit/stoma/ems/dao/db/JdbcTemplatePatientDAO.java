package patient.ems.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import patient.ems.dao.PatientDAO;
import patient.ems.domain.Patient;


public  class JdbcTemplatePatientDAO implements PatientDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplatePatientDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Collection<Patient> getAll() {
		return jdbcTemplate.query("select * from patient", new PatientMapper());
	}

	@Override
	public Patient findById(int id) {
		return jdbcTemplate.queryForObject("select * from patient where id = ?",
				new Integer[] { id },
				new PatientMapper());
	}

	@Override
	public Patient update( Patient model, int patientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Patient model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Patient> searchByName(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	private static class PatientMapper implements RowMapper<Patient> {

		@Override
		public Patient mapRow(ResultSet rs, int arg1) throws SQLException {
			Patient patient = new Patient();
			patient.setId(rs.getInt("id"));
			patient.setFirstName(rs.getString("first_name"));
			patient.setLastName(rs.getString("last_name"));
			patient.setCnp(rs.getString("cnp"));
			patient.setDateRegistration(new Date(rs.getTimestamp("date_registration").getTime()));
			patient.setModifiedAt(new Date(rs.getTimestamp("modification_date").getTime()));
			patient.setAddress(rs.getString("address"));
			patient.setDoctorInCharge(rs.getString("doctor_in_charge"));
			patient.setEmail(rs.getString("email"));
			patient.setPatientPathology(rs.getString("patient_pathology"));
			patient.setPatientPhysiology(rs.getString("patient_physiology"));
			patient.setTelephone(rs.getString("telephone"));
			patient.setGender(rs.getString("gender"));
			patient.setVisitReason(rs.getString("visit_reason"));
			return patient;
		}

	}

}
