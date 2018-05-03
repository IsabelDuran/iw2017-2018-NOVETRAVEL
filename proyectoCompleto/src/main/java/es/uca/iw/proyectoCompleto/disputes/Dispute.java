package es.uca.iw.proyectoCompleto.disputes;

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
	
	
	
	private String date_opening;
	
	private String date_closing;

	private String description;
	
	protected Dispute() {
	}

	public Dispute(String date_opening, String date_closing, String description) {
		super();
		this.date_opening = date_opening;
		this.date_closing = date_closing;
		this.description = description;
	}

	public String getString_opening() {
		return date_opening;
	}


	public void setString_opening(String date_opening) {
		this.date_opening = date_opening;
	}


	public String getString_closing() {
		return date_closing;
	}


	public void setString_closing(String date_closing) {
		this.date_closing = date_closing;
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