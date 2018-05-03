package es.uca.iw.proyectoCompleto.bookings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Booking{
	@Id
	@GeneratedValue
	private Long id_;

	private Date entryDate_;

	private Date departureDate_;
	
	private int totalPrice_;
	
	public Booking(Long id_, Date entryDate_, Date departureDate_, int totalPrice_) {
		super();
		this.id_ = id_;
		this.entryDate_ = entryDate_;
		this.departureDate_ = departureDate_;
		this.totalPrice_ = totalPrice_;
	}

	public Long getId_() {
		return id_;
	}

	public void setId_(Long id_) {
		this.id_ = id_;
	}

	public Date getEntryDate_() {
		return entryDate_;
	}

	public void setEntryDate_(Date entryDate_) {
		this.entryDate_ = entryDate_;
	}

	public Date getDepartureDate_() {
		return departureDate_;
	}

	public void setDepartureDate_(Date departureDate_) {
		this.departureDate_ = departureDate_;
	}

	public int getTotalPrice_() {
		return totalPrice_;
	}

	public void setTotalPrice_(int totalPrice_) {
		this.totalPrice_ = totalPrice_;
	}	
	
}