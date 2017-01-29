package ro.siit.stoma.appointment.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import ro.siit.stoma.appointment.dao.AppointmentDAO;
import ro.siit.stoma.appointment.domain.Appointment;

public class JDBCAppointmentDAO implements AppointmentDAO {

	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;
	private DataSource datasource;

	public JDBCAppointmentDAO(String host, String port, String dbName, String userName, String pass) {
		this.host = host;
		this.userName = userName;
		this.pass = pass;
		this.port = port;
		this.dbName = dbName;
	}

	public JDBCAppointmentDAO(DataSource dataSource) {
		this.datasource = dataSource;
	}

	@Override
	public Collection<Appointment> getAll() {

		Connection connection = newConnection();

		Collection<Appointment> result = new LinkedList<>();
		// "select * from public.\"Patient\""
		try (ResultSet rs = connection.createStatement().executeQuery("select * from public.\"Appointment\"")) {

			while (rs.next()) {
				result.add(extractappointment(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting appointments.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;
	}

	@Override
	public Appointment findById(int id) {
		Connection connection = newConnection();

		List<Appointment> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement().executeQuery("select * from public.\"Appointment\"where \"id\" = " + id)) {

			while (rs.next()) {
				result.add(extractappointment(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting appointment.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		if (result.size() > 1) {
			throw new IllegalStateException("Multiple treatments for Id: " + id);
		}
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public Appointment findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appointment update(Appointment app) {
		Connection connection = newConnection();
		try {
			PreparedStatement ps = null;
			if (app.getId() > 0) {
				ps = connection.prepareStatement(
						"update public.\"Appointment\" set \"submitDate\"=?, \"startDate\"=?, \"endDate\"=?, \"pacient\"=?, \"comments\"=?, \"chair\"=?,"
									+ "where \"id\" = ? returning \"id\"");

			} else {

				ps = connection.prepareStatement(
						"insert into public.\"Treatment\" (\"observation\", \"treatmentDate\", \"treatmentName\", \"description\", \"radiography\""
								
								+ ") values (?, ?, ?, ?, ?, ?) returning \"id\"");

			}
			ps.setTimestamp(1, new Timestamp(app.getSubmitDate().getTime()));
			ps.setTimestamp(2, new Timestamp(app.getStartDate().getTime()));
			ps.setTimestamp(3, new Timestamp(app.getEndDate().getTime()));
			ps.setString(4, app.getPacient());
			ps.setString(5, app.getComments());
			ps.setString(6, app.getChair());

			if (app.getId() > 0) {
				ps.setLong(7, app.getId());
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				app.setId(rs.getInt(1));
			}
			rs.close();

			connection.commit();

		} catch (SQLException ex) {

			throw new RuntimeException("Error getting appointments.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return app;
		
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

	protected Connection newConnection() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();

			Connection result = datasource.getConnection();
			result.setAutoCommit(false);

			return result;
		} catch (Exception ex) {
			throw new RuntimeException("Error getting DB connection", ex);
		}
	}

	private Appointment extractappointment(ResultSet rs) throws SQLException {
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
