package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chorbi extends Actor{

	//----------------------Attributes-------------------------
	private String picture;
	private KindRelationship kindRelationship;
	private Date birthDate;
	private Genre genre;
	private CreditCard creditCard;
	private Coordinates coordinates;
	
	@NotBlank
	@SafeHtml
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Valid
	@NotNull
	public KindRelationship getKindRelationship() {
		return kindRelationship;
	}
	public void setKindRelationship(KindRelationship kindRelationship) {
		this.kindRelationship = kindRelationship;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyy HH:mm")
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Valid
	@NotNull
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotNull
	@Valid
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}




	//---------------------Relationships--------------------------
	private Collection<Chirp> sendChirps;
	private Collection<Chirp> receivedChirps;
	private Collection<Likes> makeLikes;
	private Collection<Likes> receivedLikes;
	private SearchTemplate searchTemplate;
	
	
	@Valid
	@OneToOne(optional=true)
	public SearchTemplate getSearchTemplate() {
		return searchTemplate;
	}
	public void setSearchTemplate(SearchTemplate searchTemplate) {
		this.searchTemplate = searchTemplate;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="sender")
	public Collection<Chirp> getSendChirps() {
		return sendChirps;
	}
	public void setSendChirps(Collection<Chirp> sendChirps) {
		this.sendChirps = sendChirps;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="recipient")
	public Collection<Chirp> getReceivedChirps() {
		return receivedChirps;
	}
	public void setReceivedChirps(Collection<Chirp> receivedChirps) {
		this.receivedChirps = receivedChirps;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="liker")
	public Collection<Likes> getMakeLikes() {
		return makeLikes;
	}
	public void setMakeLikes(Collection<Likes> makeLikes) {
		this.makeLikes = makeLikes;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="liked")
	public Collection<Likes> getReceivedLikes() {
		return receivedLikes;
	}
	public void setReceivedLikes(Collection<Likes> receivedLikes) {
		this.receivedLikes = receivedLikes;
	}
	
	
	
	
	
}
