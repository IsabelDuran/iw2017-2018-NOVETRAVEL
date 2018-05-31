package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
	
	public Apartment findByDescription(String name);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%")
	public List<Apartment> findByLocation(@Param(value = "textoBuscado") String textoBuscado);
	public Apartment findById(Long id);
	
}

