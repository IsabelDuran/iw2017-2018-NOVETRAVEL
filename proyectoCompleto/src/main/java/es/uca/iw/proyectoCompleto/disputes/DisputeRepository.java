package es.uca.iw.proyectoCompleto.disputes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DisputeRepository extends JpaRepository<Dispute, Long>{
	
	public Dispute findById(String bookingId);
}
