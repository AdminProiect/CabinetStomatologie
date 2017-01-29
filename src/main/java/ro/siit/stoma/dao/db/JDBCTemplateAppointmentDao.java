package ro.siit.stoma.appointment.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ro.siit.stoma.appointment.dao.AppointmentDAO;
import ro.siit.stoma.appointment.domain.Appointment;

public class JDBCTemplateAppointmentDao implements AppointmentDAO{
	
	private JdbcTemplate jdbcTemplate;
	public JDBCTemplateAppointmentDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Collection<Appointment> getAll() {
		return jdbcTemplate.query("select * from Appointment", new AppointmentMapper());
	
	}

	@Override
	public Appointment findById(int id) {
		return jdbcTemplate.queryForObject("select * from Appointment where id = ?",
				new Integer[] { id },
				new AppointmentMapper());
	}

	@Override
	public Appointment update(Appointment model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Appointment model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Appointment> searchByName(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Appointment> searchBySameStartDay(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Appointment> searchByChair(String chair) {
		// TODO Auto-generated method stub
		return null;
	}

	private static class AppointmentMapper implements RowMapper<Appointment> {

		@Override
		public Appointment mapRow(ResultSet rs, int arg1) throws SQLException {
			Appointment appointment = new Appointment();
			appointment.setId(rs.getInt("id"));
			appointment.setChair(rs.getString("chair"));
			appointment.setSubmitDate(new Date(rs.getTimestamp("submitDate").getTime()));
			appointment.setStartDate(new Date(rs.getTimestamp("startDate").getTime()));
			appointment.setEndDate(new Date(rs.getTimestamp("endDate").getTime()));
			appointment.setPacient(rs.getString("pacient"));
			appointment.setComments(rs.getString("comments"));
			return appointment;
		}

	}

	@Override
	public Appointment findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
