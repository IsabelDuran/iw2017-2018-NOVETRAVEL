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
	
	private Double totalPrice_ = 0.0;
	
//	private Long idApartamento;
	
	//private Long idUserReg;
	
	public Booking(String entryDate_, String departureDate_, Double totalPrice) {
		super();
		this.entryDate_ = entryDate_;
		this.departureDate_ = departureDate_;
		this.totalPrice_ = totalPrice;
	}

	public Booking() {
		super();
	}

	public Long getId() {
		return id ;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntryDate() {
		return entryDate_;
	}

	public void setEntryDate(String entryDate_) {
		this.entryDate_ = entryDate_;
	}

	public String getDepartureDate() {
		return departureDate_;
	}

	public void setDepartureDate(String departureDate_) {
		this.departureDate_ = departureDate_;
	}

	public Double getTotalPrice() {
		return totalPrice_;
	}

	public void setTotalPrice(Double totalPrice_) {
		this.totalPrice_ = totalPrice_;
	}	
	
}