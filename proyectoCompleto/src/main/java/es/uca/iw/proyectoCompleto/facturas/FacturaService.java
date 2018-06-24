package es.uca.iw.proyectoCompleto.facturas;

import org.springframework.stereotype.Service;

@Service
public class FacturaService {
	private FacturaRepository respository_;
	
	public Factura findById(Long idFactura)
	{
		return respository_.findOne(idFactura);
	}
}
