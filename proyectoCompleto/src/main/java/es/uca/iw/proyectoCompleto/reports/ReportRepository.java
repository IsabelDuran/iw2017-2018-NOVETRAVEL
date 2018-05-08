package es.uca.iw.proyectoCompleto.reports;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>{
	
	public Report findById(int Id);
}