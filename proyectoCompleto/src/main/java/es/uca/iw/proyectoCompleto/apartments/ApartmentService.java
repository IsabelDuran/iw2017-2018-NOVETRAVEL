package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
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

}
