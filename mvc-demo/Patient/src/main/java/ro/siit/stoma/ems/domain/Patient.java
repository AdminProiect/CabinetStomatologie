package patient.ems.domain;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class Patient extends AbstractModel {
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotEmpty
	private String cnp;

	private String address;
	@NotNull
	private String gender;
	@NotEmpty
	private String telephone;

	private String email;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateRegistration;
	@NotEmpty
	private String doctorInCharge;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifiedAt;
	private String patientPathology;
	private String patientPhysiology;
	private int age;
	private String visitReason;

	public String getVisitReason() {
		return visitReason;
	}

	public void setVisitReason(String visitReason) {
		this.visitReason = visitReason;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(Date dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public String getDoctorInCharge() {
		return doctorInCharge;
	}

	public void setDoctorInCharge(String doctorInCharge) {
		this.doctorInCharge = doctorInCharge;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getPatientPathology() {
		return patientPathology;
	}

	public void setPatientPathology(String patientPathology) {
		this.patientPathology = patientPathology;
	}

	public String getPatientPhysiology() {
		return patientPhysiology;
	}

	public void setPatientPhysiology(String patientPhysiology) {
		this.patientPhysiology = patientPhysiology;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Patient [firstName=" + firstName + ", lastName=" + lastName + ", cnp=" + cnp + ", address=" + address
				+ ", gender=" + gender + ", telephone=" + telephone + ", email=" + email + ", dateRegistration="
				+ dateRegistration + ", doctorInCharge=" + doctorInCharge + ", modifiedAt=" + modifiedAt
				+ ", patientPathology=" + patientPathology + ", patientPhysiology=" + patientPhysiology + ", age=" + age
				+ ", visitReason=" + visitReason + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + age;
		result = prime * result + ((cnp == null) ? 0 : cnp.hashCode());
		result = prime * result + ((dateRegistration == null) ? 0 : dateRegistration.hashCode());
		result = prime * result + ((doctorInCharge == null) ? 0 : doctorInCharge.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((modifiedAt == null) ? 0 : modifiedAt.hashCode());
		result = prime * result + ((patientPathology == null) ? 0 : patientPathology.hashCode());
		result = prime * result + ((patientPhysiology == null) ? 0 : patientPhysiology.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((visitReason == null) ? 0 : visitReason.hashCode());
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
		Patient other = (Patient) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (age != other.age)
			return false;
		if (cnp == null) {
			if (other.cnp != null)
				return false;
		} else if (!cnp.equals(other.cnp))
			return false;
		if (dateRegistration == null) {
			if (other.dateRegistration != null)
				return false;
		} else if (!dateRegistration.equals(other.dateRegistration))
			return false;
		if (doctorInCharge == null) {
			if (other.doctorInCharge != null)
				return false;
		} else if (!doctorInCharge.equals(other.doctorInCharge))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (modifiedAt == null) {
			if (other.modifiedAt != null)
				return false;
		} else if (!modifiedAt.equals(other.modifiedAt))
			return false;
		if (patientPathology == null) {
			if (other.patientPathology != null)
				return false;
		} else if (!patientPathology.equals(other.patientPathology))
			return false;
		if (patientPhysiology == null) {
			if (other.patientPhysiology != null)
				return false;
		} else if (!patientPhysiology.equals(other.patientPhysiology))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (visitReason == null) {
			if (other.visitReason != null)
				return false;
		} else if (!visitReason.equals(other.visitReason))
			return false;
		return true;
	}

}