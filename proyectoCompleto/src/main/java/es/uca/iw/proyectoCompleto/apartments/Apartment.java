package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import javax.persistence.Embedded;
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
	
	private Double price_per_day;

	private boolean book;
	
	private String apartment_type;
	
	@Embedded
	private Apartment_OfferedServices offered_services;
	
	
	@OneToMany(mappedBy="apartment",fetch=FetchType.EAGER)
	private List<ImageApartment> images;
	
	@OneToMany(mappedBy="apartment")
	private List<Booking> bookings;
	
	protected Apartment() {
	}
	
	public Apartment(String name, String description, Double price_per_day, boolean book, String type) {
		super();
		this.name = name;
		this.description = description;
		this.price_per_day = price_per_day;
		this.book = true;
		this.apartment_type = type;
		this.offered_services = new Apartment_OfferedServices();
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

	public Double getPrice_per_day() {
		return price_per_day;
	}

	public void setPrice_per_day(Double price_per_day) {
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

	public void setBooking(Booking booking) {
		if(booking != null)
			this.bookings.add(booking);
	}
	
	public Apartment_OfferedServices getServices()
	{
		return this.offered_services;
	}
	
	public void setServices(boolean wifi,
							boolean pets_allowed,
							boolean own_bathroom,
							boolean kids_allowed,
							boolean smoking_allowed,
							boolean crib,
							boolean parking,
							boolean kitchen,
							int rooms,
							int number_bathrooms,
							int max_hosts,
							int number_beds,
							int squared_meters)
	{
		offered_services.wifi = wifi;
		offered_services.pets_allowed = pets_allowed;
		offered_services.own_bathroom = own_bathroom;
		offered_services.kids_allowed = kids_allowed;
		offered_services.smoking_allowed = smoking_allowed;
		offered_services.crib = crib;
		offered_services.parking = parking;
		offered_services.kitchen = kitchen;
		offered_services.rooms = rooms;
		offered_services.number_bathrooms = number_bathrooms;
		offered_services.max_hosts = max_hosts;
		offered_services.number_beds = number_beds;
		offered_services.squared_meters = squared_meters;
	}
	
	public boolean isWifi() {
		return offered_services.wifi;
	}
	public void setWifi(boolean wifi) {
		this.offered_services.wifi = wifi;
	}
	public boolean isPets_allowed() {
		return offered_services.pets_allowed;
	}
	public void setPets_allowed(boolean pets_allowed) {
		this.offered_services.pets_allowed = pets_allowed;
	}
	public boolean isOwn_bathroom() {
		return offered_services.own_bathroom;
	}
	public void setOwn_bathroom(boolean own_bathroom) {
		this.offered_services.own_bathroom = own_bathroom;
	}
	public boolean isKids_allowed() {
		return offered_services.kids_allowed;
	}
	public void setKids_allowed(boolean kids_allowed) {
		this.offered_services.kids_allowed = kids_allowed;
	}
	public boolean isSmoking_allowed() {
		return offered_services.smoking_allowed;
	}
	public void setSmoking_allowed(boolean smoking_allowed) {
		this.offered_services.smoking_allowed = smoking_allowed;
	}
	public boolean isCrib() {
		return offered_services.crib;
	}
	public void setCrib(boolean crib) {
		this.offered_services.crib = crib;
	}
	public boolean isParking() {
		return offered_services.parking;
	}
	public void setParking(boolean parking) {
		this.offered_services.parking = parking;
	}
	public boolean isKitchen() {
		return offered_services.kitchen;
	}
	public void setKitchen(boolean kitchen) {
		this.offered_services.kitchen = kitchen;
	}
	public int getRooms() {
		return offered_services.rooms;
	}
	public void setRooms(int rooms) {
		this.offered_services.rooms = rooms;
	}
	public int getNumber_bathrooms() {
		return offered_services.number_bathrooms;
	}
	public void setNumber_bathrooms(int number_bathrooms) {
		this.offered_services.number_bathrooms = number_bathrooms;
	}
	public int getMax_hosts() {
		return offered_services.max_hosts;
	}
	public void setMax_hosts(int max_hosts) {
		this.offered_services.max_hosts = max_hosts;
	}
	public int getNumber_beds() {
		return offered_services.number_beds;
	}
	public void setNumber_beds(int number_beds) {
		this.offered_services.number_beds = number_beds;
	}
	public int getSquared_meters() {
		return offered_services.squared_meters;
	}
	public void setSquared_meters(int squared_meters) {
		this.offered_services.squared_meters = squared_meters;
	}

}