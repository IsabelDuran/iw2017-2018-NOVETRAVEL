package es.uca.iw.proyectoCompleto.bookings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Booking{

	@Id
	@GeneratedValue
	private Long id;

	private String entryDate_;

	private String departureDate_;
	
	private int totalPrice_;
	
	private Long idApartamento;
	
	private Long idUserReg;
	
	public Booking(String entryDate_, String departureDate_, int totalPrice) {
		super();
		this.entryDate_ = entryDate_;
		this.departureDate_ = departureDate_;
		this.totalPrice_ = totalPrice_;
	}

	public Long getId() {
		return id ;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntryDate_() {
		return entryDate_;
	}

	public void setEntryDate_(String entryDate_) {
		this.entryDate_ = entryDate_;
	}

	public String getDepartureDate_() {
		return departureDate_;
	}

	public void setDepartureDate_(String departureDate_) {
		this.departureDate_ = departureDate_;
	}

	public int getTotalPrice_() {
		return totalPrice_;
	}

	public void setTotalPrice_(int totalPrice_) {
		this.totalPrice_ = totalPrice_;
	}	
	
}