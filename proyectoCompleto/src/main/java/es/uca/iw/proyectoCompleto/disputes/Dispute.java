package es.uca.iw.proyectoCompleto.disputes;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dispute{
	
	@Id
	@GeneratedValue
	private Long id;

	private Long id_user;
	
	private Long id_host;
	
	private LocalDate opening_date;
	
	private LocalDate closing_date;

	private String description;
	
	protected Dispute() {
	}

	public Dispute(LocalDate date_opening, LocalDate date_closing, String description) {
		super();
		this.opening_date = date_opening;
		this.closing_date = date_closing;
		this.description = description;
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

	@Override
	public String toString() {
		return description;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public Long getId_host() {
		return id_host;
	}

	public void setId_host(Long id_host) {
		this.id_host = id_host;
	}

}