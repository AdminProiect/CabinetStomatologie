package patient.ems.dao.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import patient.ems.dao.BaseDAO;
import patient.ems.domain.AbstractModel;

public abstract class IMBaseDAO<T extends AbstractModel> implements BaseDAO<T> {
	private Map<Integer, T> models = new HashMap<Integer, T>();

	private static AtomicInteger ID = new AtomicInteger();

	@Override
	public Collection<T> getAll() {

		return models.values();
	}

	@Override
	public T findById(int id) {

		return models.get(id);
	}

	@Override
	public T update(T model, int patientId) {
		if (model.getId() <= 0) {
			model.setId(ID.getAndIncrement());
		}

		models.put(patientId, model);
		return model;
	}

	@Override
	public boolean delete(T model) {
		boolean result = models.containsKey(model.getId());

		if (result)
			models.remove(model.getId());
		return result;
	}

}
