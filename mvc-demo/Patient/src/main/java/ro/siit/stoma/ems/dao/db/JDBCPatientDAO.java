package patient.ems.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patient.ems.dao.PatientDAO;
import patient.ems.domain.Patient;

public class JDBCPatientDAO implements PatientDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCPatientDAO.class);

	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;

	private DataSource datasource;

	public JDBCPatientDAO(String host, String port, String dbName, String userName, String pass) {
		this.host = host;
		this.userName = userName;
		this.pass = pass;
		this.port = port;
		this.dbName = dbName;
	}

	public JDBCPatientDAO(DataSource dataSource) {
		this.datasource = dataSource;
	}

	@Override
	public Collection<Patient> getAll() {
		Connection connection = newConnection();

		Collection<Patient> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement().executeQuery("select * from public.\"Patient\"")) {

			while (rs.next()) {
				result.add(extractPatient(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting patients.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return result;
	}

	@Override
	public Patient findById(int Id) {
		Connection connection = newConnection();

		List<Patient> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from public.\"Patient\" where \"Id\" = " + Id)) {

			while (rs.next()) {
				result.add(extractPatient(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting patients.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		if (result.size() > 1) {
			throw new IllegalStateException("Multiple Patients for Id: " + Id);
		}
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public Patient update(Patient model, int patientId) {
		Connection connection = newConnection();
		try {
			PreparedStatement ps = null;
			if (patientId > 0) {
				ps = connection.prepareStatement(
						"update public.\"Patient\" set \"FirstName\"=?, \"LastName\"=?, \"CNP\"=?, \"Address\"=?, \"Gender\"=?,"
								+ "\"Telephone\"=?, \"Email\"=?, \"RegistrationDate\"=?, \"ModifiedAt\"=?, \"DoctorInCharge\"=?, "
								+ "\"PatientPathology\"=?, \"PatientPhysiology\"=?, \"VisitReason\"=?"
								+ "where \"Id\" = ? returning \"Id\"");

			} else {

				ps = connection.prepareStatement(
						"insert into public.\"Patient\" (\"FirstName\", \"LastName\", \"CNP\", \"Address\", \"Gender\""
								+ ",\"Telephone\", \"Email\", \"RegistrationDate\", \"ModifiedAt\", \"DoctorInCharge\","
								+ "\"PatientPathology\", \"PatientPhysiology\", \"VisitReason\""
								+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning \"Id\"");

			}
			ps.setString(1, model.getFirstName());
			ps.setString(2, model.getLastName());
			ps.setString(3, model.getCnp());
			ps.setString(4, model.getAddress());
			ps.setString(5, model.getGender());
			ps.setString(6, model.getTelephone());
			ps.setString(7, model.getEmail());
			ps.setTimestamp(8, new Timestamp(model.getDateRegistration().getTime()));
			ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			ps.setString(10, model.getDoctorInCharge());
			ps.setString(11, model.getPatientPathology());
			ps.setString(12, model.getPatientPhysiology());
			ps.setString(13, model.getVisitReason());

			if (model.getId() > 0) {
				ps.setLong(14, model.getId());
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				model.setId(rs.getInt(1));
			}
			rs.close();

			connection.commit();

		} catch (SQLException ex) {

			throw new RuntimeException("Error getting patient.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return model;
	}

	@Override
	public boolean delete(Patient model) {
		boolean result = false;
		Connection connection = newConnection();
		try {
			Statement statement = connection.createStatement();
			result = statement.execute("delete from public.\"Patient\" where \"Id\" = " + model.getId());
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error updating patient.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;

	}

	@Override
	public Collection<Patient> searchByName(String query) {
		if (query == null) {
			query = "";
		} else {
			query = query.trim();
		}

		Connection connection = newConnection();

		Collection<Patient> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement().executeQuery("select * from patient "
				+ "where lower(FirstName || ' ' || LastName) like '%" + query.toLowerCase() + "%'")) {

			while (rs.next()) {
				result.add(extractPatient(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting patient.", ex);
		}

		return result;
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

	private Patient extractPatient(ResultSet rs) throws SQLException {
		Patient patient = new Patient();
		patient.setId(rs.getInt("Id"));
		patient.setFirstName(rs.getString("FirstName"));
		patient.setLastName(rs.getString("LastName"));
		patient.setAddress(rs.getString("Address"));
		patient.setDateRegistration(new Date(rs.getTimestamp("RegistrationDate").getTime()));
		patient.setModifiedAt(new Date(rs.getTimestamp("ModifiedAt").getTime()));
		patient.setGender(rs.getString("Gender"));
		patient.setAge(getAge(rs.getString("Age")));
		patient.setCnp(rs.getString("CNP"));
		patient.setEmail(rs.getString("Email"));
		patient.setPatientPathology(rs.getString("PatientPathology"));
		patient.setPatientPhysiology(rs.getString("PatientPhysiology"));
		patient.setTelephone(rs.getString("Telephone"));
		patient.setDoctorInCharge(rs.getString("DoctorInCharge"));
		patient.setVisitReason(rs.getString("VisitReason"));
		return patient;

	}

	private int getAge(String cnp) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yy");

		Date d = new Date();
		try {
			d = dt.parse(cnp.substring(5, 7) + "/" + cnp.substring(3, 5) + "/" + cnp.substring(1, 3));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int p;
		Date dat = new Date();
		long duration = dat.getTime() - d.getTime();

		p = (int) (duration / 3.154e+10);

		return p;
	}

}