package es.uca.iw.proyectoCompleto.bookings;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.users.User;

import  java.time.LocalDate;

@Entity
public class Booking{

	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate entryDate_;

	private LocalDate departureDate_;
	
	private Double totalPrice_ = 0.0;
	
	@OneToOne (fetch=FetchType.LAZY)
	 @JoinColumn(name="apartment_id")
	private Apartment apartment;
	
	//private User user;
	
	
	public Booking(LocalDate entryDate_, LocalDate departureDate_, Double totalPrice) {
		super();
		this.entryDate_ = entryDate_;
		this.departureDate_ = departureDate_;
		this.totalPrice_ = totalPrice;
	//	this.apartment = apartment;
	//	this.user = user;
	}

	/*public Apartment getApartment() {
		return apartment;segfsgsd
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	protected Booking() {
		super();
	}

	public Long getId() {
		return id ;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getEntryDate() {
		return entryDate_;
	}

	public void setEntryDate(LocalDate entryDate_) {
		this.entryDate_ = entryDate_;
	}

	public LocalDate getDepartureDate() {
		return departureDate_;
	}

	public void setDepartureDate(LocalDate departureDate_) {
		this.departureDate_ = departureDate_;
	}

	public Double getTotalPrice() {
		return totalPrice_;
	}

	public void setTotalPrice(Double totalPrice_) {
		this.totalPrice_ = totalPrice_;
	
}
	
}