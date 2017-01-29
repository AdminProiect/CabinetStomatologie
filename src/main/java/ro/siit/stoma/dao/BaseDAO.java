package ro.siit.stoma.appointment.dao;

import java.util.Collection;

import ro.siit.stoma.appointment.domain.AbstractModel;

public interface BaseDAO<T extends AbstractModel> {

	Collection<T> getAll();
	
	T findById(Long id);
	
	T findById(int id);
	
	T update(T model);
	
	boolean delete(T model);
}
