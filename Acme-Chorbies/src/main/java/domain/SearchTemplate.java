package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity{
	
	//----------------------Attributes-------------------------
	private KindRelationship kindRelationship;
	private Integer aproximateAge;
	private Genre genre;
	private String keyword;
	private String country;
	private String city;
	
	@Valid
	@NotNull
	public KindRelationship getKindRelationship() {
		return kindRelationship;
	}
	public void setKindRelationship(KindRelationship kindRelationship) {
		this.kindRelationship = kindRelationship;
	}
	
	@Min(18)
	public Integer getAproximateAge() {
		return aproximateAge;
	}
	public void setAproximateAge(Integer aproximateAge) {
		this.aproximateAge = aproximateAge;
	}
	
	@Valid
	@NotNull
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	@SafeHtml
	@NotNull
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@SafeHtml
	@NotNull
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@SafeHtml
	@NotNull
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	

}
