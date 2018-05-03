package es.uca.iw.proyectoCompleto.reports;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Report{
{
	@Id
	@GeneratedValue
	private String date;

	private String title;
	
	private String reasons;

}
	private String report_description;

	private Long id;
	

	public Report(String date, String title, String reasons, String report_description, Long id) {
		super();
		this.date = date;
		this.title = title;
		this.reasons = reasons;
		this.report_description = report_description;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getString() {
		return date;
	}

	public void setString(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public String getReport_description() {
		return report_description;
	}

	public void setReport_description(String report_description) {
		this.report_description = report_description;
	}
	
}
