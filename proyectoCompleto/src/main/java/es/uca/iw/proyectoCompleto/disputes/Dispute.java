package es.uca.iw.proyectoCompleto.disputes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Dispute{
	
	private static final long serialVersionUID = -8883789651072229337L;

	@Id
	@GeneratedValue
	private Long id;
	
	private Long id_user;
	
	private Long id_host;
	
	private Date date_opening;
	
	private Date date_closing;

	private String description;
	
	protected Dispute() {
	}

	public Dispute(Date date_opening, Date date_closing, String description) {
		super();
		this.date_opening = date_opening;
		this.date_closing = date_closing;
		this.description = description;
	}

	public Date getDate_opening() {
		return date_opening;
	}


	public void setDate_opening(Date date_opening) {
		this.date_opening = date_opening;
	}


	public Date getDate_closing() {
		return date_closing;
	}


	public void setDate_closing(Date date_closing) {
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

}