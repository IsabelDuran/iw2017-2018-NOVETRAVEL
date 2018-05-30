package es.uca.iw.proyectoCompleto.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.disputes.Dispute;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository repo;
	
	public Location loadLocationByLocationId(Long locationID)
	{
		Location location_ = repo.findById(locationID);
		if(locationID == null)
		{
			location_ = new Location();
		}
		return location_;
	}
	
	public Location save(Location location_)
	{
		return repo.save(location_);
	}
	
	public Location findOne(Long arg0) {
		
		return repo.findOne(arg0);
	}
	
	public void delete(Location arg0)
	{
		repo.delete(arg0);
	}
	
}
