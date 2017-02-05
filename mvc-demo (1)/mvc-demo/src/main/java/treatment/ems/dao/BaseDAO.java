package treatment.ems.dao;

import java.util.Collection;

import treatment.ems.domain.AbstractModel;

public interface BaseDAO<T extends AbstractModel> {

	Collection<T> getAll();
	
	T findById(int id);
	
	T update(T model,int patientId);
	
	boolean delete(T model);

	T findById(Long id);
}
