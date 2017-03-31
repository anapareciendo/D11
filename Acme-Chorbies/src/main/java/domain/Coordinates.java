package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinates {
	
	private String country;
	private String city;
	private String state;
	private String province;
	
	@NotBlank
	@SafeHtml
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@NotBlank
	@SafeHtml
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@SafeHtml
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@SafeHtml
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	

}
