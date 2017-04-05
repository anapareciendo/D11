package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

@Access(AccessType.PROPERTY)
public class TemplateForm{
	
	//----------------------Attributes-------------------------
	private int kindRelationship;
	private int aproximateAge;
	private int genre;
	private String keyword;
	
	private String country;
	private String city;
	private String state;
	private String province;
	
	public int getKindRelationship() {
		return kindRelationship;
	}
	public void setKindRelationship(int kindRelationship) {
		this.kindRelationship = kindRelationship;
	}
	public int getAproximateAge() {
		return aproximateAge;
	}
	public void setAproximateAge(int aproximateAge) {
		this.aproximateAge = aproximateAge;
	}
	public int getGenre() {
		return genre;
	}
	public void setGenre(int genre) {
		this.genre = genre;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	
}
