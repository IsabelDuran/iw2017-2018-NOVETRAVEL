package es.uca.iw.proyectoCompleto.facturas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>{
	
	Factura findById(Long id);

}
