package patient.ems.dao;

import java.util.Collection;

import patient.ems.domain.AbstractModel;

public interface BaseDAO<T extends AbstractModel> {

	Collection<T> getAll();
	
	T findById(int id);
	
	T update(T model, int patientId);
	
	boolean delete(T model);
}
