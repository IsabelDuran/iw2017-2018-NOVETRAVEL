package es.uca.iw.proyectoCompleto.apartments;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
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

	@Autowired
	public ApartmentManagementView(ApartmentService service, ApartmentEditor editor) {
		this.grid = new Grid<>(Apartment.class);
		this.addNewBtn = new Button("Nuevo apartamento");
	    
	}

	
	@PostConstruct
	void init() {
		
		// build layout
		Button goBack = new Button("Volver", e -> getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME));
		
		HorizontalLayout actions = new HorizontalLayout(addNewBtn, goBack);
		
	
		
		addComponents(actions, grid);
		
		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setColumns("apartmentType","description", "name","pricePerDay");

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
		
		
	}


	
	
	@Override
	public void enter(ViewChangeEvent event) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		grid.setItems(user.getApartments());
	}

}
