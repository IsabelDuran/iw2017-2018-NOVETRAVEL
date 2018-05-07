package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
	
	public Apartment findByName(String username);
}

