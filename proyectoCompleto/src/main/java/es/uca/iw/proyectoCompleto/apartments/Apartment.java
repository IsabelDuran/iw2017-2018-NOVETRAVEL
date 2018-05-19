package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;

@Entity
public class Apartment{
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String description;
	
	private int price_per_day;

	private boolean book;
	
	private String apartment_type;
	
	
	@OneToMany(mappedBy="apartment",fetch=FetchType.EAGER)
	private List<ImageApartment> images;
	
	@OneToMany(mappedBy="apartment")
	private List<Booking> bookings;
	
	protected Apartment() {
	}
	
	public Apartment(String name, String description, int price_per_day, boolean book, String type) {
		super();
		this.name = name;
		this.description = description;
		this.price_per_day = price_per_day;
		this.book = book;
		this.apartment_type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<ImageApartment> getImages() {
		return images;
	}

	public void setImages(List<ImageApartment> images) {
		this.images = images;
	}
	
	public void addImage(ImageApartment image)
    {
		this.images.add(image);
        if (image.getApartment() != this) {
            image.setApartment(this);
        }
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice_per_day() {
		return price_per_day;
	}

	public void setPrice_per_day(int price_per_day) {
		this.price_per_day = price_per_day;
	}

	public boolean isBook() {
		return book;
	}

	public void setBook(boolean book) {
		this.book = book;
	}

	public String getApartment_type() {
		return apartment_type;
	}

	public void setApartment_type(String apartment_type) {
		this.apartment_type = apartment_type;
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public void addBooking(Booking book)
	{
		this.bookings.add(book);
        if (book.getApartment() != this) {
            book.setApartment(this);
        }
	}

}