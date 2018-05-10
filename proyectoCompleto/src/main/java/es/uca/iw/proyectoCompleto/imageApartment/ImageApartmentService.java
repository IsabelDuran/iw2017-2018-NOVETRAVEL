package es.uca.iw.proyectoCompleto.imageApartment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentRepository;

@Service 
public class ImageApartmentService {
	
	@Autowired
	private ImageApartmentRepository repo;

	//throws ApartmentnameNotFoundException 
	public ImageApartment loadImageApartmentbyId(Long Id) {

		ImageApartment report = repo.findById(Id);
		if (report == null) {
			//throw new ImageApartmentnameNotFoundException(reportname);
		}
		return report;
	}

	public ImageApartment save(ImageApartment report) {

		return repo.save(report);
	}

	public ImageApartment findOne(Long arg0) {
		return repo.findOne(arg0);
	}

	public void delete(ImageApartment arg0) {
		repo.delete(arg0);
	}
	
	public List<ImageApartment> findAll() {
		return repo.findAll();
	}


}