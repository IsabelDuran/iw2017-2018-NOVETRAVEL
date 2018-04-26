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
	
	private Date date;

	private String title;
	
	private String reason;
	
	private String description;
	
	protected Dispute() {
	}

	public Dispute(Date date, String title, String reason, String description) {
		this.date = date;
		this.title = title;
		this.reason = reason;
		this.description = description;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "a";
	}

}