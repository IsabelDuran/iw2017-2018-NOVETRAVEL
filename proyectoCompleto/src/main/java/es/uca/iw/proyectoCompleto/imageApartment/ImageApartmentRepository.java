package es.uca.iw.proyectoCompleto.imageApartment;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentRepository;

public interface ImageApartmentRepository extends JpaRepository<ImageApartment, Long>{
	public ImageApartment findById(Long Id);
}
