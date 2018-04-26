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
}