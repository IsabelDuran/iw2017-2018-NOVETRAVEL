package es.uca.iw.proyectoCompleto.bookings;

import  java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.facturas.Factura;
import es.uca.iw.proyectoCompleto.users.User;

@Entity
public class Booking{

	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate entryDate_;

	private LocalDate departureDate_;
	
	private Double totalPrice_ = 0.0;
	
	private boolean confirmation = false;

	@ManyToOne(fetch=FetchType.EAGER)
	private Apartment apartment;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;
	
	@OneToOne(fetch=FetchType.EAGER)
	// @JoinColumn(name="FACTURA_ID")
	private Factura factura;

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Booking() {
		super();
	}
			
	public Booking(LocalDate entryDate_, LocalDate departureDate_, Double totalPrice, boolean confirmation, Apartment apartment, User user) {
		super();
		this.entryDate_ = entryDate_;
		this.departureDate_ = departureDate_;
		this.totalPrice_ = totalPrice;
		this.apartment = apartment;
		this.user = user;
		this.confirmation = confirmation;
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

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}
	
}