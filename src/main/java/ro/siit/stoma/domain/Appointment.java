package ro.siit.stoma.appointment.domain;



import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class Appointment extends AbstractModel{
	
	
	//@NotEmpty
	private String chair; 
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date submitDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date endDate;
	
	@NotEmpty
	private String pacient;
	private String comments;

	public String getChair() {
		return chair;
	}
	
	
	public void setChair(String ch) {
		this.chair = ch;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPacient() {
		return pacient;
	}

	public void setPacient(String pacient) {
		this.pacient = pacient;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Appointment [chair=" + chair + ", submitDate=" + submitDate + ", startDate=" + startDate + ", endDate="
				+ endDate + ", pacient=" + pacient + ", comments=" + comments + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chair == null) ? 0 : chair.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((pacient == null) ? 0 : pacient.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((submitDate == null) ? 0 : submitDate.hashCode());
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
		Appointment other = (Appointment) obj;
		if (chair != other.chair)
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (pacient == null) {
			if (other.pacient != null)
				return false;
		} else if (!pacient.equals(other.pacient))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (submitDate == null) {
			if (other.submitDate != null)
				return false;
		} else if (!submitDate.equals(other.submitDate))
			return false;
		return true;
	}

}
