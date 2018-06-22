package es.uca.iw.proyectoCompleto.bookings;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.users.User;
@SpringView(name = BookingManagementView.VIEW_NAME)
public class BookingManagementView extends HorizontalLayout implements View {
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "bookingManagementView";

	private Grid<Booking> grid;
	
	private Grid<Apartment> apartmentNameGrid;
	
	//private TextField filter;

	private BookingEditor editor;
	
	private final ApartmentService serviceApartment;

	@Autowired
	public BookingManagementView(BookingEditor editor, ApartmentService serviceApartment ) {
		this.editor = editor;
		this.grid = new Grid<>();
		this.apartmentNameGrid = new Grid<>();
		this.serviceApartment = serviceApartment;
		    
	}

	
	@PostConstruct
	void init() {
		/// build layout
		
		Button goBack = new Button("Volver", e -> getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME));
		addComponent(goBack);
		addComponents(apartmentNameGrid, grid, editor);
		
		apartmentNameGrid.setHeight(300, Unit.PIXELS);
		apartmentNameGrid.setWidth(200, Unit.PIXELS);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(100, Unit.PERCENTAGE);
			
		apartmentNameGrid.addColumn(Apartment::getName).setCaption("Nombre del apartamento").setResizable(false);
		grid.addColumn(Booking::getEntryDate).setCaption("Fecha de entrada" ).setResizable(false);
		grid.addColumn(Booking::getDepartureDate).setCaption("Fecha de salida").setResizable(false);
		grid.addColumn(Booking::getTotalPrice).setCaption("Precio total").setResizable(false);
		
		
		// Hook logic to components
		// Connect selected Booking to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener( e -> {
			editor.editBooking(e.getValue());
		});
		
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		}); 

		
		// Initialize listing
		listBookings(null);

	}

	private void listBookings(String filterText) {
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Apartment> a = new ArrayList<Apartment>();
		List<Booking> b = new ArrayList<Booking>();
		
		b = user_.getBooking();
		
		if(b.size() != 0)
		{
			for(Booking book: b)
			{
				for(Apartment apart: serviceApartment.findAll())
				{
					if(book.getApartment().getId() == apart.getId())
						a.add(apart);
				}
			
			}
		
		}

		grid.setItems(b);
		apartmentNameGrid.setItems(a);

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
