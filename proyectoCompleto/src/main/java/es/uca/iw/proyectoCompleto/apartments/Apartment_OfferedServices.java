package es.uca.iw.proyectoCompleto.apartments;

import javax.persistence.Embeddable;

@Embeddable
public class Apartment_OfferedServices {
	
	boolean wifi;
	boolean pets_allowed;
	boolean own_bathroom;
	boolean kids_allowed;
	boolean smoking_allowed;
	boolean crib;
	boolean parking;
	boolean kitchen;
	int rooms;
	int number_bathrooms;
	int max_hosts;
	int number_beds;
	int squared_meters;
	
	public boolean isWifi() {
		return wifi;
	}
	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}
	public boolean isPets_allowed() {
		return pets_allowed;
	}
	public void setPets_allowed(boolean pets_allowed) {
		this.pets_allowed = pets_allowed;
	}
	public boolean isOwn_bathroom() {
		return own_bathroom;
	}
	public void setOwn_bathroom(boolean own_bathroom) {
		this.own_bathroom = own_bathroom;
	}
	public boolean isKids_allowed() {
		return kids_allowed;
	}
	public void setKids_allowed(boolean kids_allowed) {
		this.kids_allowed = kids_allowed;
	}
	public boolean isSmoking_allowed() {
		return smoking_allowed;
	}
	public void setSmoking_allowed(boolean smoking_allowed) {
		this.smoking_allowed = smoking_allowed;
	}
	public boolean isCrib() {
		return crib;
	}
	public void setCrib(boolean crib) {
		this.crib = crib;
	}
	public boolean isParking() {
		return parking;
	}
	public void setParking(boolean parking) {
		this.parking = parking;
	}
	public boolean isKitchen() {
		return kitchen;
	}
	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public int getNumber_bathrooms() {
		return number_bathrooms;
	}
	public void setNumber_bathrooms(int number_bathrooms) {
		this.number_bathrooms = number_bathrooms;
	}
	public int getMax_hosts() {
		return max_hosts;
	}
	public void setMax_hosts(int max_hosts) {
		this.max_hosts = max_hosts;
	}
	public int getNumber_beds() {
		return number_beds;
	}
	public void setNumber_beds(int number_beds) {
		this.number_beds = number_beds;
	}
	public int getSquared_meters() {
		return squared_meters;
	}
	public void setSquared_meters(int squared_meters) {
		this.squared_meters = squared_meters;
	}
	
	
}
