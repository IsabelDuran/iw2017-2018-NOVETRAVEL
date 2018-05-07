package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
	
	public List<Apartment> findByLastNameStartsWithIgnoreCase(String lastName);
	
	public Apartment findByApartmentname(String username);
}

