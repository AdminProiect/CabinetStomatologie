package treatment.ems.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import treatment.ems.dao.TreatmentDAO;
import treatment.ems.domain.Treatment;


public class JdbcTemplateTreatmentDAO implements TreatmentDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplateTreatmentDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Collection<Treatment> getAll() {
		return jdbcTemplate.query("select * from treatment", new TreatmentMapper());
	}

	@Override
	public Treatment findById(int id) {
		return jdbcTemplate.queryForObject("select * from treatment where id = ?",
				new Integer[] { id },
				new TreatmentMapper());
	}

	@Override
	public Treatment update(Treatment model,int treatment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Treatment model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Treatment> searchByTreatmentName(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	private static class TreatmentMapper implements RowMapper<Treatment> {

		@Override
		public Treatment mapRow(ResultSet rs, int arg1) throws SQLException {
			Treatment treatment = new Treatment();
			treatment.setId(rs.getInt("id"));
			treatment.setTreatmentName(rs.getString("treatment_name"));
			treatment.setDescription(rs.getString("description"));
			treatment.setObservation(rs.getString("observation"));
			treatment.setTreatmentDate(new Date(rs.getTimestamp("treatment_date").getTime()));
			treatment.setRadiography(rs.getString("radiography"));
			
			return treatment;
		}

	}

	@Override
	public Treatment findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
