package es.uca.iw.proyectoCompleto.apartments;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentEditor;
import es.uca.iw.proyectoCompleto.apartments.ApartmentManagementView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;

@SpringView(name = ApartmentManagementView.VIEW_NAME)
public class ApartmentManagementView extends VerticalLayout implements View{
	public static final String VIEW_NAME = "apartmentManagementView";

	private Grid<Apartment> grid;
	private TextField filter;
	private Button addNewBtn;

	private ApartmentEditor editor;

	
	private final ApartmentService service;

	@Autowired
	public ApartmentManagementView(ApartmentService service, ApartmentEditor editor) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Apartment.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New apartment", FontAwesome.PLUS);
	    
	}

	
	@PostConstruct
	void init() {
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		
		addComponents(actions, grid, editor);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "apartment_type", "book","description", "name","price_per_day");

		filter.setPlaceholder("Filter by last name");
		
		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listApartments(e.getValue()));

		// Connect selected Apartment to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editApartment(e.getValue());
		});

		// Instantiate and edit new Apartment the new button is clicked
		//addNewBtn.addClickListener(e -> editor.editApartment(new Apartment("", "", "", 1231)));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listApartments(filter.getValue());
		});

		// Initialize listing
		listApartments(null);
		
	}

	private void listApartments(String filterText) {
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
