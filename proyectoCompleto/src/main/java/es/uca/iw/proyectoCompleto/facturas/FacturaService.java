package es.uca.iw.proyectoCompleto.facturas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.facturas.Factura;

@Service
public class FacturaService {
	
	@Autowired
	FacturaRepository repository;
	
	public Factura loadFacturaByFacturaId(Long facturaId)
	{
		return repository.findById(facturaId);

	}
	
	public Factura save(Factura factura) {
		System.out.println("ENTRA EN FACTURA GUARDAR");
		return repository.save(factura);
	}

	public Factura findOne(Long arg0) {
		return repository.findOne(arg0);
	}

	public void delete(Factura arg0) {
		repository.delete(arg0);
	}

	public List<Factura> findAll() {
		return repository.findAll();
	}
}
