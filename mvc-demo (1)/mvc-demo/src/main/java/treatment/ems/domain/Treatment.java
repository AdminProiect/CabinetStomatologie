package treatment.ems.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


public class Treatment extends AbstractModel {
	
	private String treatmentName;
	private String observation;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date treatmentDate;
	private String radiography;
	
	public Treatment(String treatmentName, String observation, String description, Date treatmentDate,
			String radiography) {
		super();

		this.treatmentName = treatmentName;
		this.observation = observation;
		this.description = description;
		this.treatmentDate = treatmentDate;
		this.radiography = radiography;
	}
     
	public Treatment(){
		
	}
	public String getTreatmentName() {
		return treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getRadiography() {
		return radiography;
	}

	public void setRadiography(String radiography) {
		this.radiography = radiography;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((radiography == null) ? 0 : radiography.hashCode());
		result = prime * result + ((treatmentDate == null) ? 0 : treatmentDate.hashCode());
		result = prime * result + ((treatmentName == null) ? 0 : treatmentName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Treatment other = (Treatment) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (radiography == null) {
			if (other.radiography != null)
				return false;
		} else if (!radiography.equals(other.radiography))
			return false;
		if (treatmentDate == null) {
			if (other.treatmentDate != null)
				return false;
		} else if (!treatmentDate.equals(other.treatmentDate))
			return false;
		if (treatmentName == null) {
			if (other.treatmentName != null)
				return false;
		} else if (!treatmentName.equals(other.treatmentName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Treatment [treatmentName=" + treatmentName + ", observation=" + observation + ", description="
				+ description + ", treatmentDate=" + treatmentDate + ", radiography=" + radiography + "]";
	}
	
	
}