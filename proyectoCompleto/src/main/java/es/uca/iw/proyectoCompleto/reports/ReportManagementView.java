package es.uca.iw.proyectoCompleto.reports;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
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

	public static final String VIEW_NAME = "ReportManagementView";

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
		this.addNewBtn = new Button("New report");
	    
	}

	
	@PostConstruct
	void init() {
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		
		addComponents(actions, grid, editor);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(900, Unit.PIXELS);
		grid.setColumns("id", "apartment_type", "book","description", "name","price_per_day");

		filter.setPlaceholder("Filter by last name");
		
		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listReports(e.getValue()));

		// Connect selected Apartment to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editReport(e.getValue());
		});

		// Instantiate and edit new Apartment the new button is clicked
		//addNewBtn.addClickListener(e -> editor.editApartment(new Apartment("", "", "", 1231)));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listReports(filter.getValue());
		});

		// Initialize listing
		listReports(null);
		
	}

	private void listReports(String filterText) {
		/*
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(service.findAll());
		} else {
			grid.setItems(service.findByLastNameStartsWithIgnoreCase(filterText));
		}
		*/
		grid.setItems(service.findAll());
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
