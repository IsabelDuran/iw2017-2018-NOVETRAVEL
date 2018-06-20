package es.uca.iw.proyectoCompleto.apartments;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

import es.uca.iw.proyectoCompleto.DefaultView;
import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentEditor;
import es.uca.iw.proyectoCompleto.apartments.ApartmentManagementView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.users.User;

@SpringView(name = ApartmentManagementView.VIEW_NAME)
public class ApartmentManagementView extends VerticalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "apartmentManagementView";

	private Grid<Apartment> grid;
	private Button addNewBtn;

	private ApartmentEditor editor;

	private final ApartmentService service;

	@Autowired
	public ApartmentManagementView(ApartmentService service, ApartmentEditor editor) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Apartment.class);
		this.addNewBtn = new Button("Nuevo apartamento");
	    
	}

	
	@PostConstruct
	void init() {
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(addNewBtn);
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		addComponents(actions, grid);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(900, Unit.PIXELS);
		grid.setColumns("id", "apartmentType", "book","description", "name","pricePerDay");

		// Connect selected Apartment to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			VaadinSession.getCurrent().setAttribute("apartamentoEditado", e.getValue());
			getUI().getNavigator().navigateTo(ApartmentEditor.VIEW_NAME);
		});


		addNewBtn.addClickListener(e -> 
		{
			VaadinSession.getCurrent().setAttribute("apartamentoEditado", null);
			getUI().getNavigator().navigateTo(ApartmentEditor.VIEW_NAME);
		});
		
		listApartments(null, user_.getApartments());
		
	}

	private void listApartments(String filterText, List<Apartment> aps) {
		grid.setItems(aps);
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
