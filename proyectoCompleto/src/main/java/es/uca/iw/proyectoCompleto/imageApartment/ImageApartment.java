package es.uca.iw.proyectoCompleto.imageApartment;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.mysql.jdbc.Blob;

import es.uca.iw.proyectoCompleto.apartments.Apartment;

@Entity
public class ImageApartment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	
	@Basic(fetch=FetchType.LAZY)
	@Lob
	private byte[] file;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Apartment apartment;
	
	public ImageApartment() {
	
	}
	
	public ImageApartment(byte[] file, Apartment apartment) {
		super();
		this.file = file;
		this.apartment = apartment;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getFile() {
		return file;
	}
	
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Apartment getApartment() {
		return apartment;
	}


	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
}
	
}
