package es.uca.iw.proyectoCompleto.facturas;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import es.uca.iw.proyectoCompleto.bookings.Booking;

@Entity
public class Factura {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDate fechaFactura;
	
	@Lob
	private String detalles;
	
	private double precioSinIva;
	
	private double iva;

	@OneToOne(fetch = FetchType.EAGER, mappedBy ="factura", cascade=CascadeType.ALL)
	private Booking booking;
	
	public Factura() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	} 
	
	public double getPrecioSinIva() {
		return precioSinIva;
	}

	public void setPrecioSinIva(double precioSinIva) {
		this.precioSinIva = precioSinIva;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}
	
}
