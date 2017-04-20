package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.core.convert.Property;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity{

	//----------------------Attributes-------------------------
	private String title;
	private Date moment;
	private String description;
	private String picture;
	private int seatsffered;
	
	@NotBlank
	@SafeHtml
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	@SafeHtml
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotBlank
	@URL
	@SafeHtml
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Min(value=0)
	public int getSeatsffered() {
		return seatsffered;
	}
	public void setSeatsffered(int seatsffered) {
		this.seatsffered = seatsffered;
	}


	//---------------------Relationships--------------------------
	private Manager manager;
	private Collection<Chorbi> chorbies;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Manager getManager() {
		return manager;
	}
	public void setManger(Manager manager) {
		this.manager = manager;
	}
	
	@Valid
	@NotNull
	@ManyToMany(
			cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE},
			mappedBy="events",
			targetEntity=Property.class
			)
	public Collection<Chorbi> getChorbies() {
		return chorbies;
	}
	public void setChorbies(Collection<Chorbi> chorbies) {
		this.chorbies = chorbies;
	}
	
	
}
