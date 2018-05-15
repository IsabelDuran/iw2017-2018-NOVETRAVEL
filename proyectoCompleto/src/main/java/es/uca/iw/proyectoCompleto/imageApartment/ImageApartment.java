package es.uca.iw.proyectoCompleto.imageApartment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.mysql.jdbc.Blob;

import es.uca.iw.proyectoCompleto.apartments.Apartment;

@Entity
public class ImageApartment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	//private Blob file;
	@ManyToOne(fetch=FetchType.LAZY)
	private Apartment apartment;
	
	
	public ImageApartment(Blob file, Apartment apartment) {
		super();
		//this.file = file;
		this.apartment = apartment;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



/*
	public Blob getFile() {
		return file;
	}


	public void setFile(Blob file) {
		this.file = file;
	}
*/

	public Apartment getApartment() {
		return apartment;
	}


	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
}
	
}
