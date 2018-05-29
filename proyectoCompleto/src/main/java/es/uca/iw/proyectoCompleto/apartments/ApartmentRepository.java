package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
	
	public Apartment findByDescription(String name);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.location.city_ = ?1")
	public List<Apartment> findByLocation(String city);
	public Apartment findById(Long id);
	
}

