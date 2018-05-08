package es.uca.iw.proyectoCompleto.bookings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	
	public Booking findById(String bookingId);
}

