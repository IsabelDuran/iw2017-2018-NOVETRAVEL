package es.uca.iw.proyectoCompleto.apartments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
	
	public Apartment findByDescription(String username);
}

