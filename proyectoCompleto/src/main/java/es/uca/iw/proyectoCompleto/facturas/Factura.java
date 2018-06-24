package es.uca.iw.proyectoCompleto.facturas;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.itextpdf.text.Document;

import es.uca.iw.proyectoCompleto.bookings.Booking;

public class Factura {

	@Id
	@GeneratedValue
	private Long facturaId;

	private String details;

	private LocalDate fechaFactura;

	private Booking booking;

	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Long getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(Long facturaId) {
		this.facturaId = facturaId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void generarPdf() {
		Document document = new Document();
	}

}
