package treatment.ems.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import treatment.ems.dao.TreatmentDAO;
import treatment.ems.domain.Treatment;

public class JDBCTreatmentDAO implements TreatmentDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCTreatmentDAO.class);

	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;

	private DataSource datasource;
	

	public JDBCTreatmentDAO(String host, String port, String dbName, String userName, String pass) {
		this.host = host;
		this.userName = userName;
		this.pass = pass;
		this.port = port;
		this.dbName = dbName;
	}
	
	public JDBCTreatmentDAO(DataSource dataSource){
	   this.datasource= dataSource;
}

	

	@Override
	public Collection<Treatment> getAll() {
		Connection connection = newConnection();

		Collection<Treatment> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from public.\"treatment")) {

			while (rs.next()) {
				result.add(extractTreatment(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting treatments.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return result;
	}

	@Override
	public Treatment findById(int Id) {
		Connection connection = newConnection();

		List<Treatment> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from public.\"treatment\" where \"Id\" = " + Id)) {

			while (rs.next()) {
				result.add(extractTreatment(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting treatments.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		if (result.size() > 1) {
			throw new IllegalStateException("Multiple treatments for Id: " + Id);
		}
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public Treatment update(Treatment model, int treatmentId) {
		Connection connection = newConnection();
		try {
			PreparedStatement ps = null;
			if (model.getId() > 0) {
				ps = connection.prepareStatement(
						"update public.\"treatment\" set \"Observation\"=?, \"TreatmentDate\"=?, \"TreatmentName\"=?, \"Description\"=?, \"Radiography\"=?,"
									+ "where \"id\" = ? returning \"id\"");

			} else {

				ps = connection.prepareStatement(
						"insert into public.\"treatment\" (\"Observation\", \"TreatmentDate\", \"TreatmentName\", \"Description\", \"Radiography\""
								
								+ ") values (?, ?, ?, ?, ?) returning \"Id\"");

			}
			ps.setString(1, model.getObservation());
			ps.setTimestamp(2, new Timestamp(model.getTreatmentDate().getTime()));
			ps.setString(3, model.getTreatmentName());
			ps.setString(4, model.getDescription());
			ps.setString(5, model.getRadiography());

			if (model.getId() > 0) {
				ps.setLong(6, model.getId());
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				model.setId(rs.getInt(1));
			}
			rs.close();

			connection.commit();

		} catch (SQLException ex) {

			throw new RuntimeException("Error getting treatments.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return model;
	}

	@Override
	public boolean delete(Treatment model) {
		boolean result = false;
		Connection connection = newConnection();
		try {
			Statement statement = connection.createStatement();
			result = statement.execute("delete from public.\"treatment\" where \"id\" = " + model.getId());
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error updating treatments.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;

	}

	@Override
	public Collection<Treatment> searchByTreatmentName(String query) {
		if (query == null) {
			query = "";
		} else {
			query = query.trim();
		}

		Connection connection = newConnection();

		Collection<Treatment> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from treatment "
						+ "where lower(treatment_name ||) like '%"
						+ query.toLowerCase() + "%'")) {

			while (rs.next()) {
				result.add(extractTreatment(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting treatments.", ex);
		}

		return result;
	}

	protected Connection newConnection() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();

			String url = new StringBuilder()
					.append("jdbc:")
					.append("postgresql")
					.append("://")
					.append(host)
					.append(":")
					.append(port)
					.append("/")
					.append(dbName)
					.append("?user=")
					.append(userName)
					.append("&password=")
					.append(pass).toString();
			Connection result = DriverManager.getConnection(url);
			result.setAutoCommit(false);

			return result;
		} catch (Exception ex) {
			throw new RuntimeException("Error getting DB connection", ex);
		}

	}

	private Treatment extractTreatment(ResultSet rs) throws SQLException {
		Treatment treatment = new Treatment();
		treatment.setId(rs.getLong("id"));
		treatment.setObservation(rs.getString("observation"));
		treatment.setTreatmentName(rs.getString("treatment_name"));
		treatment.setTreatmentDate(new Date(rs.getTimestamp("treatment_date").getTime()));
		treatment.setDescription(rs.getString("description"));
		treatment.setRadiography(rs.getString("radiography"));
		
		return treatment;

	}

	@Override
	public Treatment findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}