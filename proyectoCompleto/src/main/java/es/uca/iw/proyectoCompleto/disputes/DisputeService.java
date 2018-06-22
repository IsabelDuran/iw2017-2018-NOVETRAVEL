package es.uca.iw.proyectoCompleto.disputes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisputeService
{	
	@Autowired
	private DisputeRepository repository_;
	
	public Dispute loadDisputeByDisputeId(String disputeId)
	{
		Dispute dispute_ = repository_.findById(disputeId);
		if(dispute_ == null)
		{
			//throw new DisputenameNotFoundException(bookingId);
		}
		return dispute_;
	}
	
	public Dispute save(Dispute dispute_)
	{
		return repository_.save(dispute_);
	}
	
	public Dispute findOne(Long arg0) {
		
		return repository_.findOne(arg0);
	}
	
	public void delete(Dispute arg0)
	{
		repository_.delete(arg0);
	}
	
	public List<Dispute> findAll()
	{
		return repository_.findAll();
	}
}
