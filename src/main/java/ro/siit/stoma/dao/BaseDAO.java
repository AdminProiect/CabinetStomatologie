package ro.siit.stoma.dao;

import java.util.Collection;

import ro.siit.stoma.domain.AbstractModel;

public interface BaseDAO<T extends AbstractModel> {

	Collection<T> getAll();
	
	T findById(Long id);
	
	T update(T model);
	
	boolean delete(T model);
}
