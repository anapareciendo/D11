package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;


@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity{

	//----------------------Attributes-------------------------
	private String title;
	private int day;
	private int month;
	private int year;
	private int hour;
	private int minutes;
	private String description;
	private String picture;
	private int seatsOffered;
	
	@NotBlank
	@SafeHtml
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
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
	public int getSeatsOffered() {
		return seatsOffered;
	}
	public void setSeatsOffered(int seatsffered) {
		this.seatsOffered = seatsffered;
	}
	
	@Range(min=1, max=31)
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}

	@Range(min=1, max=12)
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	@Range(min=0, max=23)
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}

	@Range(min=0, max=59)
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
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
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	@Valid
	@NotNull
	@ManyToMany(
			cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE},
			mappedBy="events",
			targetEntity=Chorbi.class
			)
	public Collection<Chorbi> getChorbies() {
		return chorbies;
	}
	public void setChorbies(Collection<Chorbi> chorbies) {
		this.chorbies = chorbies;
	}
	
	
}
