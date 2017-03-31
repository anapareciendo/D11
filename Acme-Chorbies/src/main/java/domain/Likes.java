package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Likes extends DomainEntity{

	//----------------------Attributes-------------------------
	private Date moment;
	private String comment;
		
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotNull
	@SafeHtml
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
		
	//---------------------Relationships--------------------------
	private Chorbi liker;
	private Chorbi liked;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getLiker() {
		return liker;
	}
	public void setLiker(Chorbi liker) {
		this.liker = liker;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getLiked() {
		return liked;
	}
	public void setLiked(Chorbi liked) {
		this.liked = liked;
	}
	
	
}
