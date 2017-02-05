package treatment.ems.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import treatment.ems.dao.TreatmentDAO;
import treatment.ems.domain.Treatment;

@Service
public class TreatmentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TreatmentService.class);

	@Autowired
	private TreatmentDAO dao;

	@Transactional
	public Collection<Treatment> listAll() {
		return dao.getAll();
	}

	public Collection<Treatment> search(String query) {
		LOGGER.debug("Searching for " + query);
		return dao.searchByTreatmentName(query);
	}

	public boolean delete(Treatment model) {
		LOGGER.debug("Deleting treatment for id: " + model.getId());
		Treatment tr = dao.findById(model.getId());
		if (tr != null) {
			dao.delete(model);
			return true;
		}

		return false;
	}

	public Treatment get(int id) {
		LOGGER.debug("Getting treatment for id: " + id);
		return dao.findById(id);

	}

	public void save(Treatment treatment, int treatmentId) throws ValidationException {
		LOGGER.debug("Saving: " + treatment);
		validate(treatment);

		dao.update(treatment, treatmentId);
	}

	private void validate(Treatment treatment) throws ValidationException {
		//Date currentDate = new Date();
		List<String> errors = new LinkedList<String>();
		if (StringUtils.isEmpty(treatment.getTreatmentName())) {
			errors.add("Treatment Name is Empty");
		}

		if (StringUtils.isEmpty(treatment.getObservation())) {
			errors.add("Observation Name is Empty");
		}

		if (treatment.getDescription() == null) {
			errors.add("Descriptiom is Empty");
		}

		if (StringUtils.isEmpty(treatment.getTreatmentDate())) {
			errors.add("Date is Empty");
		}

		if (treatment.getRadiography() == null) {
			errors.add("Adress is Empty");
		}

		
		if (!errors.isEmpty()) {
			throw new ValidationException(errors.toArray(new String[] {}));
		}
	}

	public TreatmentDAO getDao() {
		return dao;
	}

	public void setDao(TreatmentDAO dao) {
		this.dao = dao;
	}

}

