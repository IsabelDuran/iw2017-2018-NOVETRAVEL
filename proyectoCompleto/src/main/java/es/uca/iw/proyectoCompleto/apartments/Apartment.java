package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.disputes.Dispute;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.location.Location;
import es.uca.iw.proyectoCompleto.users.User;

@Entity
public class Apartment{
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Lob
	private String description;
	
	@Min(value = 1L, message = "The value must be positive")
	private Double pricePerDay;
	
	private String apartmentType;
	
	private Boolean wifi;
	private Boolean petsAllowed;
	private Boolean ownBathroom;
	private Boolean kidsAllowed;
	private Boolean smokingAllowed;
	private Boolean crib;
	private Boolean parking;
	private Boolean kitchen;
	private Integer rooms;
	private Integer numberBathrooms;
	private Integer maxHosts;
	private Integer numberBeds;
	private Integer squaredMeters;
	
	@OneToMany(mappedBy="apartment",fetch=FetchType.EAGER)
	private List<ImageApartment> images;
	
	@OneToMany(mappedBy="apartment")
	private List<Booking> bookings;
	
	@OneToOne(mappedBy="apartment", cascade = {CascadeType.ALL})
	private Location location;
	
	@OneToMany(mappedBy="apartment", cascade = {CascadeType.ALL})
	private List<Dispute> disputas;

	@ManyToOne
	private User user;
	
	public Apartment() {
	}
	
	public Apartment(String name, String description, Double pricePerDay, String type) {
		super();
		this.name = name;
		this.description = description;
		this.pricePerDay = pricePerDay;
		this.apartmentType = type;

	}	
	
	public Apartment(String name, String description, Double pricePerDay, String type, User user) {
		super();
		this.name = name;
		this.description = description;
		this.pricePerDay = pricePerDay;
		this.apartmentType = type;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	
	public String getApartmentType() {
		return apartmentType;
	}

	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}

	public Boolean getWifi() {
		return wifi;
	}

	public void setWifi(Boolean wifi) {
		this.wifi = wifi;
	}

	public Boolean getPetsAllowed() {
		return petsAllowed;
	}

	public void setPetsAllowed(Boolean petsAllowed) {
		this.petsAllowed = petsAllowed;
	}

	public Boolean getOwnBathroom() {
		return ownBathroom;
	}

	public void setOwnBathroom(Boolean ownBathroom) {
		this.ownBathroom = ownBathroom;
	}

	public Boolean getKidsAllowed() {
		return kidsAllowed;
	}

	public void setKidsAllowed(Boolean kidsAllowed) {
		this.kidsAllowed = kidsAllowed;
	}

	public Boolean getSmokingAllowed() {
		return smokingAllowed;
	}

	public void setSmokingAllowed(Boolean smokingAllowed) {
		this.smokingAllowed = smokingAllowed;
	}

	public Boolean getCrib() {
		return crib;
	}

	public void setCrib(Boolean crib) {
		this.crib = crib;
	}

	public Boolean getParking() {
		return parking;
	}

	public void setParking(Boolean parking) {
		this.parking = parking;
	}

	public Boolean getKitchen() {
		return kitchen;
	}

	public void setKitchen(Boolean kitchen) {
		this.kitchen = kitchen;
	}

	public Integer getRooms() {
		return rooms;
	}

	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}

	public Integer getNumberBathrooms() {
		return numberBathrooms;
	}

	public void setNumberBathrooms(Integer numberBathrooms) {
		this.numberBathrooms = numberBathrooms;
	}

	public Integer getMaxHosts() {
		return maxHosts;
	}

	public void setMaxHosts(Integer maxHosts) {
		this.maxHosts = maxHosts;
	}

	public Integer getNumberBeds() {
		return numberBeds;
	}

	public void setNumberBeds(Integer numberBeds) {
		this.numberBeds = numberBeds;
	}

	public Integer getSquaredMeters() {
		return squaredMeters;
	}

	public void setSquaredMeters(Integer squaredMeters) {
		this.squaredMeters = squaredMeters;
	}

	public List<ImageApartment> getImages() {
		return images;
	}

	public void setImages(List<ImageApartment> images) {
		this.images = images;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

	public void addImage(ImageApartment image)
    {
		this.images.add(image);
        if (image.getApartment() != this) {
            image.setApartment(this);
        }
	}
	
	public List<Dispute> getDisputas() {
		return disputas;
	}

	public void setDisputas(List<Dispute> disputas) {
		this.disputas = disputas;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if(obj == null)
			return false;
		else if(obj instanceof Apartment)
		{
			isEqual = (this.id == ((Apartment) obj).id);
		}
		else 
			isEqual = super.equals(obj);
		return isEqual;
	}
	
	

}