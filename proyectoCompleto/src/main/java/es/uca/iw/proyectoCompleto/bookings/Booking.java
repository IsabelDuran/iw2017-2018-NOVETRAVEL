package es.uca.iw.proyectoCompleto.bookings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Booking{
	@Id
	@GeneratedValue
	private Long id_;
	
	

	private String entryString_;

	private String departureString_;
	
	private int totalPrice_;
	
}

