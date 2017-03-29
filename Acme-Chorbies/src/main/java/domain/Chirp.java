package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity {

	//----------------------Attributes-------------------------
	private Date moment;
	private String subject;
	private String text;
	private Collection<String> attachments;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment){
		this.moment=moment;
	}
	
	@NotBlank
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject){
		this.subject=subject;
	}
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text){
		this.text=text;
	}
	
	@ElementCollection
	@NotNull
	public Collection<String> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}


	//---------------------Relationships--------------------------
	
}
