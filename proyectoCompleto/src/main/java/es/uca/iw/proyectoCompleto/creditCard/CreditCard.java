package es.uca.iw.proyectoCompleto.creditCard;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;

@Entity
public class CreditCard {
	@Id
	@GeneratedValue
	private Long id;
	
	private String card_number;
	private int CVC_CVV;
	private LocalDate expiration_date;
	private String name;
	private String lastname;
	

	public CreditCard(String card_number, int cVC_CVV, LocalDate expiration_date, String name, String lastname) {
		super();
		this.card_number = card_number;
		CVC_CVV = cVC_CVV;
		this.expiration_date = expiration_date;
		this.name = name;
		this.lastname = lastname;
	}
	
	/* USUARIO QUE POSEE LA TARJETA*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public int getCVC_CVV() {
		return CVC_CVV;
	}
	public void setCVC_CVV(int cVC_CVV) {
		CVC_CVV = cVC_CVV;
	}
	public LocalDate getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(LocalDate expiration_date) {
		this.expiration_date = expiration_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	

}
