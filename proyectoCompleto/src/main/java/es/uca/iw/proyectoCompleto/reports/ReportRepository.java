package es.uca.iw.proyectoCompleto.reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportRepository extends JpaRepository<Report, Long>{
	
	public List<Report> findByDateStartsWithIgnoreCase(String date);
	
	public Report findById(int Id);
}