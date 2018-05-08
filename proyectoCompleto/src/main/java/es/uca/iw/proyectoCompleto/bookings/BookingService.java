package es.uca.iw.proyectoCompleto.bookings;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class BookingService {

	@Autowired
	private BookingRepository repository_;

	//throws ApartmentnameNotFoundException 
	public Booking loadBookingByBookingId(String bookingId) {

		Booking booking_ = repository_.findById(bookingId);
		if (booking_ == null) {
			//throw new BookingnameNotFoundException(bookingId);
		}
		return booking_;
	}

	public Booking save(Booking apartamento) {

		return repository_.save(apartamento);
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

}
