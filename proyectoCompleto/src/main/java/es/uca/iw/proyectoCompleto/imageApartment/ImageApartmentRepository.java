package es.uca.iw.proyectoCompleto.imageApartment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageApartmentRepository extends JpaRepository<ImageApartment, Long>{
	public ImageApartment findById(Long Id);
}
