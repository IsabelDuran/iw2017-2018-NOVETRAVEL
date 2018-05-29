package es.uca.iw.proyectoCompleto.location;

import org.springframework.data.jpa.repository.JpaRepository;
public interface LocationRepository extends JpaRepository<Location, Long>{
	
	public Location findById(Long id);
	
}
