package es.uca.iw.proyectoCompleto.reports;

import java.util.Calendar;


import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import org.springframework.util.StringUtils;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.reports.Report;
import es.uca.iw.proyectoCompleto.reports.ReportEditor;
import es.uca.iw.proyectoCompleto.reports.ReportService;


@SpringView(name = ReportManagementView.VIEW_NAME)
public class ReportManagementView extends VerticalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "reportManagementView";

	private Grid<Report> grid;
	private TextField filter;
	private Button addNewBtn;

	private ReportEditor editor;

	
	private final ReportService service;

	@Autowired
	public ReportManagementView(ReportService service,ReportEditor editor) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Report.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nueva queja");
	    
	}

	
	@PostConstruct
	void init() {
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		
		addComponents(actions, grid, editor);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(900, Unit.PIXELS);
		grid.setColumns("id","title", "date", "reasons", "report_description");

		filter.setPlaceholder("Filter by date");
		
		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listReports(e.getValue()));

		// Connect selected Report to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editReport(e.getValue());
		});

		// Instantiate and edit new Report the new button is clicked
		Calendar fecha_ = Calendar.getInstance();
		
		//
		final String date = String.valueOf(fecha_.get(Calendar.DAY_OF_MONTH)) 
				+ "/" + String.valueOf(fecha_.get(Calendar.MONTH)) 
				+ "/" + String.valueOf(fecha_.get(Calendar.YEAR));
		addNewBtn.addClickListener(e -> editor.editReport(new Report(date, "", "", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listReports(filter.getValue());
		});

		// Initialize listing
		listReports(null);
		
	}

	private void listReports(String filterText) {
		
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(service.findAll());
		} else {
			grid.setItems(service.findByDateStartsWithIgnoreCase(filterText));
		}
		
		//grid.setItems(service.findAll());
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
