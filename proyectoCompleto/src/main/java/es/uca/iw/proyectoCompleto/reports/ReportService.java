package es.uca.iw.proyectoCompleto.reports;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.proyectoCompleto.users.User;

@Service 
public class ReportService {

	@Autowired
	private ReportRepository repo;

	//throws ApartmentnameNotFoundException 
	public Report loadReportbyId(int Id) {

		Report report = repo.findById(Id);
		if (report == null) {
			//throw new ReportnameNotFoundException(reportname);
		}
		return report;
	}
	
	public List<Report> findByDateStartsWithIgnoreCase(String date) {
		return repo.findByDateStartsWithIgnoreCase(date);
	}

	public Report save(Report report) {

		return repo.save(report);
	}

	public Report findOne(Long arg0) {
		return repo.findOne(arg0);
	}

	public void delete(Report arg0) {
		repo.delete(arg0);
	}
	
	public List<Report> findAll() {
		return repo.findAll();
	}


}
