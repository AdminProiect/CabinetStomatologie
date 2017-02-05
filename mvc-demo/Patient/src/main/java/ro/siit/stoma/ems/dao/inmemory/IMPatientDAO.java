package patient.ems.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import org.springframework.util.StringUtils;
import patient.ems.domain.Patient;


public class IMPatientDAO extends IMBaseDAO<Patient>{

	public Collection<Patient> searchByName(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}

		Collection<Patient> all = new LinkedList<Patient>(getAll());
		for (Iterator<Patient> it = all.iterator(); it.hasNext();) {
			Patient pat = it.next();
			String ss = pat.getFirstName() + " " + pat.getLastName();
			if (!ss.toLowerCase().contains(query.toLowerCase())) {
				it.remove();
			}
		}
		return all;
	}

}
