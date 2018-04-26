package es.uca.iw.proyectoCompleto.host_ratings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Host_Rating{
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Host_Rating(int value) {
		super();
		this.value = value;
	}
	
	
}