package ro.siit.stoma.dao.db;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.siit.stoma.dao.ObjDAO;
import ro.siit.stoma.domain.Obj;



//@Repository
public class JDBCObjDAO implements ObjDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCObjDAO.class);

	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;

	
	

	public JDBCObjDAO(String host, String port, String dbName, String userName, String pass) {
		this.host = host;
		this.userName = userName;
		this.pass = pass;
		this.port = port;
		this.dbName = dbName;
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