package imageApartment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.mysql.jdbc.Blob;

@Entity
public class ImageApartment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Blob file;
	private Apartment ap;
	
}
