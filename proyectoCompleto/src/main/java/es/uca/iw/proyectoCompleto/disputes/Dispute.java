package es.uca.iw.proyectoCompleto.disputes;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.users.User;

@Entity
public class Dispute{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate opening_date;
	
	private LocalDate closing_date;

	private String description;
	
	private boolean open;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Apartment apartment;
	
	@ManyToOne
	private User user;
	
	protected Dispute() {
	}

	public Dispute(LocalDate date_opening, LocalDate date_closing, String description,Apartment apartamento,User user) {
		super();
		this.opening_date = date_opening;
		this.closing_date = date_closing;
		this.description = description;
		this.apartment=apartamento;
		this.user=user;
		open=true;
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getOpening_date() {
		return opening_date;
	}


	public void setOpening_date(LocalDate opening_date) {
		this.opening_date = opening_date;
	}


	public LocalDate getClosing_date() {
		return closing_date;
	}


	public void setClosing_date(LocalDate closing_date) {
		this.closing_date = closing_date;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	
	public String getUsername(){
		
		return user.getUsername();
	}
	
	public Long getApartmentID(){
		
		return apartment.getId();
	}

	@Override
	public String toString() {
		return description;
	}



}