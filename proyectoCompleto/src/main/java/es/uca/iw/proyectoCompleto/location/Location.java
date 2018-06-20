package es.uca.iw.proyectoCompleto.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import es.uca.iw.proyectoCompleto.apartments.Apartment;

@Entity
public class Location {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String city_;
	
	private String postalCode_;
	
	private String street_;
	
	private Integer number_;
	
	private Integer floor_;
	
	private String letter_;
	

	@OneToOne
	private Apartment apartment;
	
	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Location() {
		
	}

	public Location(String city_, String postalCode_, String street_, int number_, int floor_, String letter_) {
		super();
		this.city_ = city_;
		this.postalCode_ = postalCode_;
		this.street_ = street_;
		this.number_ = number_;
		this.floor_ = floor_;
		this.letter_ = letter_;
	}

	public String getCity_() {
		return city_;
	}

	public void setCity_(String city_) {
		this.city_ = city_;
	}

	public String getPostalCode_() {
		return postalCode_;
	}

	public void setPostalCode_(String postalCode_) {
		this.postalCode_ = postalCode_;
	}

	public String getStreet_() {
		return street_;
	}

	public void setStreet_(String street_) {
		this.street_ = street_;
	}

	public Integer getNumber_() {
		return number_;
	}

	public void setNumber_(Integer number_) {
		this.number_ = number_;
	}

	public Integer getFloor_() {
		return floor_;
	}

	public void setFloor_(Integer floor_) {
		this.floor_ = floor_;
	}

	public String getLetter_() {
		return letter_;
	}

	public void setLetter_(String letter_) {
		this.letter_ = letter_;
	}

}
