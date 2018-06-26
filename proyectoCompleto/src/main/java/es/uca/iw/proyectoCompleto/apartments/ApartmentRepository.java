package es.uca.iw.proyectoCompleto.apartments;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
	
	public Apartment findByDescription(String name);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%")
	public List<Apartment> findByLocation(@Param(value = "textoBuscado") String textoBuscado);
	
	@Query("SELECT ap FROM Apartment ap WHERE (ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%) AND ap.pricePerDay < :precioMax  AND ap.id NOT IN (SELECT bo.apartment.id FROM Booking bo WHERE bo.entryDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.departureDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.confirmation = TRUE)")
	public List<Apartment> filterByPriceAndDate(@Param(value = "textoBuscado") String textoBuscado,
												@Param(value = "fechaInicio") LocalDate fechaInicio,
												@Param(value = "fechaFin") LocalDate fechaFin,
												@Param(value = "precioMax") Double precioMax);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.pricePerDay < :precioMax AND (ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%)")
	public List<Apartment> findByPrice(@Param(value = "textoBuscado") String textoBuscado,
										@Param(value = "precioMax") Double precioMax);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%  AND ap.id NOT IN (SELECT bo.apartment.id FROM Booking bo WHERE bo.entryDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.departureDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.confirmation = TRUE)")
	public List<Apartment> filterByDate(@Param(value = "textoBuscado") String textoBuscado,
												@Param(value = "fechaInicio") LocalDate fechaInicio,
												@Param(value = "fechaFin") LocalDate fechaFin);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%")
	public Page<Apartment> findByLocation(@Param(value = "textoBuscado") String textoBuscado, Pageable page);
	
	@Query("SELECT ap FROM Apartment ap WHERE (ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%) AND ap.pricePerDay < :precioMax  AND ap.id NOT IN (SELECT bo.apartment.id FROM Booking bo WHERE bo.entryDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.departureDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.confirmation = TRUE)")
	public Page<Apartment> filterByPriceAndDate(@Param(value = "textoBuscado") String textoBuscado,
												@Param(value = "fechaInicio") LocalDate fechaInicio,
												@Param(value = "fechaFin") LocalDate fechaFin,
												@Param(value = "precioMax") Double precioMax, Pageable page);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.pricePerDay < :precioMax AND (ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%)")
	public Page<Apartment> findByPrice(@Param(value = "textoBuscado") String textoBuscado,
										@Param(value = "precioMax") Double precioMax, Pageable page);
	
	@Query("SELECT ap FROM Apartment ap WHERE ap.location.city_ LIKE %:textoBuscado% OR ap.location.street_ LIKE %:textoBuscado%  AND ap.id NOT IN (SELECT bo.apartment.id FROM Booking bo WHERE bo.entryDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.departureDate_ BETWEEN :fechaInicio AND :fechaFin AND bo.confirmation = TRUE)")
	public Page<Apartment> filterByDate(@Param(value = "textoBuscado") String textoBuscado,
												@Param(value = "fechaInicio") LocalDate fechaInicio,
												@Param(value = "fechaFin") LocalDate fechaFin, Pageable page);
	
	@Query("SELECT ap FROM Apartment ap LEFT JOIN FETCH ap.user WHERE ap.id = :idApartment")
	public Apartment findByIdWithUser(@Param(value = "idApartment") Long idApartment);
	
	public Apartment findById(Long id);
	
}

