package es.uca.iw.proyectoCompleto.creditCard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class CreditCardService {

	@Autowired
	private CreditCardRepository repository_;
	
	public CreditCard loadDisputeByDisputeId(String creditcardId)
	{
		CreditCard creditcard_ = repository_.findById(creditcardId);
		if(creditcard_ == null)
		{
			//throw new DisputenameNotFoundException(bookingId);
		}
		return creditcard_;
	}
	
	public CreditCard save(CreditCard dispute_)
	{
		return repository_.save(dispute_);
	}
	
	public CreditCard findOne(Long arg0) {
		
		return repository_.findOne(arg0);
	}
	
	public void delete(CreditCard arg0)
	{
		repository_.delete(arg0);
	}
	
	public List<CreditCard> findAll()
	{
		return repository_.findAll();
	}
	
}
