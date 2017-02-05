package treatment.ems.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import treatment.ems.dao.TreatmentDAO;
import treatment.ems.domain.Treatment;


public class IMTreatmentDAO extends IMBaseDAO<Treatment> 
	implements TreatmentDAO {

	@Override
	public Collection<Treatment> searchByTreatmentName(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		
		Collection<Treatment> all = new LinkedList<Treatment>(getAll());
		for (Iterator<Treatment> it = all.iterator(); it.hasNext();) {
			Treatment tr = it.next();
			String ss = tr.getTreatmentName();
			if (!ss.toLowerCase().contains(query.toLowerCase())) {
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Treatment findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
