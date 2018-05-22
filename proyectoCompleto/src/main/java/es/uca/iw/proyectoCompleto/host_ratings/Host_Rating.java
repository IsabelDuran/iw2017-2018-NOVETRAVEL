package es.uca.iw.proyectoCompleto.host_ratings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Host_Rating extends Rating{
	@Id
	@GeneratedValue
	private Long id;
	
	

	public Host_Rating(int value,String descripcion) {
		super(value,descripcion);
	}
	
	
}