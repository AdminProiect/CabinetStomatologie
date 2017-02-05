package treatment.ems.dao;

import java.util.Collection;

import treatment.ems.domain.Treatment;

public interface TreatmentDAO extends BaseDAO<Treatment>{

	Collection<Treatment> searchByTreatmentName(String query);
}
