package es.uca.iw.proyectoCompleto.apartments;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApartmentService {

	@Autowired
	private ApartmentRepository repo;

	//throws ApartmentnameNotFoundException 
	public Apartment loadApartmentByApartmentname(String apartamentoname) {

		Apartment apartamento = repo.findByDescription(apartamentoname);
		if (apartamento == null) {
			//throw new ApartmentnameNotFoundException(apartamentoname);
		}
		return apartamento;
	}
	
	public Apartment loadApartmentById(Long id) {

		Apartment apartamento = repo.findById(id);
		
		if (apartamento == null) {
			System.out.println("ENTRA EN NULL");
			//throw new ApartmentnameNotFoundException(apartamento);
		}
		return apartamento;
		//
		//
	} 

	public Apartment save(Apartment apartamento) {

		return repo.save(apartamento);
	}

	public Apartment findOne(Long arg0) {
		return repo.findOne(arg0);
	}

	public void delete(Apartment arg0) {
		repo.delete(arg0);
	}

	public List<Apartment> findAll() {
		return repo.findAll();
	}
	
	public List<Apartment> loadApartmentByLocation(String city)
	{
		List<Apartment> apartamentos = repo.findByLocation(city);
		if (apartamentos == null)
			apartamentos = new ArrayList<>();
		return apartamentos;
		
	}
	
	public List<Apartment> loadByPrice(String city, Double price)
	{
		List<Apartment> apartamentos = repo.findByPrice(city, price);
		if (apartamentos == null)
			apartamentos = new ArrayList<>();
		return apartamentos;
	}
	
	public List<Apartment> loadApartmentByLocationDateAndPrice(String city, 
							LocalDate entryDate, 
							LocalDate departureDate, 
							Double maxPrice)
	{
		List<Apartment> apartamentos = repo.filterByPriceAndDate(city, entryDate, departureDate, maxPrice);
		if (apartamentos == null)
				apartamentos = new ArrayList<>();
		return apartamentos;
	}
}