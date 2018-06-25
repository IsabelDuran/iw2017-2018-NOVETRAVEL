package es.uca.iw.proyectoCompleto.bookings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.apartments.Apartment;

@Service
public class BookingService {

	@Autowired
	private BookingRepository repository_;

	//throws ApartmentnameNotFoundException 
	public Booking loadBookingByBookingId(Long bookingId) {

		Booking booking_ = repository_.findById(bookingId);
		if (booking_ == null) {
			//throw new BookingnameNotFoundException(bookingId);
		}
		return booking_;
	}
	
	public List<Booking> loadBookingByApartmentId(Long apartmentId)
	{
		List<Booking> reservas = repository_.findByApartmentId(apartmentId);
		if (reservas == null)
			reservas = new ArrayList<>();
		
		
			
		return reservas;
	}
	

	public Booking save(Booking booking) {

		return repository_.save(booking);
	}

	public Booking findOne(Long arg0) {
		return repository_.findOne(arg0);
	}

	public void delete(Booking arg0) {
		repository_.delete(arg0);
	}

	public List<Booking> findAll() {
		return repository_.findAll();
	}
	
	public boolean isApartmentFreeBetweenDates(Apartment apartment, LocalDate start, LocalDate end)
	{
		List<Booking> bookings = repository_.findBookingsOfApartmentBetweenDates(apartment.getId(), start, end);
		for(Booking b : bookings)
		{
			System.out.println(b.getEntryDate());
			System.out.println(b.getDepartureDate());
		}
		return bookings.isEmpty();
	}

}
