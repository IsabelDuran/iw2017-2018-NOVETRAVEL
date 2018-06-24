package facturas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {
	private FacturaRepository respository_;
	
	public Factura findById(Long idFactura)
	{
		return respository_.findOne(idFactura);
	}
}
