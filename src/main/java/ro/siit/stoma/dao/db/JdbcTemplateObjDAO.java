package ro.siit.stoma.dao.db;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ro.siit.stoma.dao.ObjDAO;
import ro.siit.stoma.domain.Obj;


public class JdbcTemplateObjDAO implements ObjDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplateObjDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Collection<Obj> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Obj findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Obj update(Obj model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Obj model) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
