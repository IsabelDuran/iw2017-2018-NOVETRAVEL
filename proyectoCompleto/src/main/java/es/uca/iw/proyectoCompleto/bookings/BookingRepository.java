package es.uca.iw.proyectoCompleto.bookings;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
	
	public Booking findById(Long bookingId);
	
	@Query("SELECT book FROM Booking book WHERE book.apartment.id = :apartmentId")
	public List<Booking> findByApartmentId(@Param("apartmentId") Long apartmentId);
}

