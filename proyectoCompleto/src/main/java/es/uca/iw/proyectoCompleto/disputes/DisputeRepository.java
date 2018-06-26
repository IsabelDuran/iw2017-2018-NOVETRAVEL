package es.uca.iw.proyectoCompleto.disputes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisputeRepository extends JpaRepository<Dispute, Long>{
	
	public Dispute findById(String bookingId);
	
	public Page<Dispute> findByReason(String reason, Pageable page);
}
