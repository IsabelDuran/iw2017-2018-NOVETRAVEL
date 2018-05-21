package es.uca.iw.proyectoCompleto.creditCard;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditCardRepository extends JpaRepository<CreditCard, Long>{
	
	public CreditCard findById(String bookingId);

}
