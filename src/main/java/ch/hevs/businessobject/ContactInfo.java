package ch.hevs.businessobject;

import javax.persistence.Embeddable;

@Embeddable
public class ContactInfo {
	
	private String street;
	private String postcode;
	private String locality;
	private String phone;
	private String email;

	public ContactInfo()
	{
		
	}
	
	
	public ContactInfo(String street, String postcode, String locality, String phone, String email)
	{
		this.street = street;
		this.postcode = postcode;
		this.locality = locality;
		this.phone = phone;
		this.email = email;
	}


	public String getLocality() {
		return locality;
	}
	
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
