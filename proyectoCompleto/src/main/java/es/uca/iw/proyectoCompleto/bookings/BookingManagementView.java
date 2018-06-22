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
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.users.User;
@SpringView(name = BookingManagementView.VIEW_NAME)
public class BookingManagementView extends VerticalLayout implements View {
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "bookingManagementView";

	private Grid<Booking> grid,grid2;
	
	private Grid<Apartment> apartmentNameGrid, apartmentNameGrid2;

	private BookingEditor editor;
	
	@Autowired
	private final ApartmentService serviceApartment;
	
	@Autowired
	private final BookingService bookingService;

	@Autowired
	public BookingManagementView(BookingEditor editor, ApartmentService serviceApartment, BookingService bookingService) {
		this.editor = editor;
		this.grid = new Grid<>();
		this.grid2 = new Grid<>();
		this.apartmentNameGrid = new Grid<>();
		this.apartmentNameGrid2 = new Grid<>();
		this.serviceApartment = serviceApartment;
		this.bookingService = bookingService;
		    
	}

	
	@PostConstruct
	void init() {
		/// build layout
		HorizontalLayout h = new HorizontalLayout();
		Button goBack = new Button("Volver", e -> getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME));
		Label titulo = new Label("MIS RESERVAS");
		addComponents(goBack, titulo);
		
		editor.setVisible(false);
		h.addComponents(apartmentNameGrid, grid,editor);
		
		addComponents(h);
		
		apartmentNameGrid.setHeight(300, Unit.PIXELS);
		apartmentNameGrid.setWidth(250, Unit.PIXELS);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(100, Unit.PERCENTAGE);
			
		apartmentNameGrid.addColumn(Apartment::getName).setCaption("Nombre del apartamento").setResizable(false);
		grid.addColumn(Booking::isConfirmation).setCaption("Confirmación").setResizable(false);
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
		
		//PARA LAS RESERVAS DE TU APARTAMENTO
		HorizontalLayout h2 = new HorizontalLayout();
		Label titulo2 = new Label("RESERVAS EN MIS APARTAMENTOS");
		addComponent(titulo2);
		
		h2.addComponents(apartmentNameGrid2, grid2);
		
		addComponents(h2);
		
		apartmentNameGrid2.setHeight(300, Unit.PIXELS);
		apartmentNameGrid2.setWidth(250, Unit.PIXELS);
		

		
		grid2.setHeight(300, Unit.PIXELS);
		grid2.setWidth(100, Unit.PERCENTAGE);
			
		apartmentNameGrid2.addColumn(Apartment::getName).setCaption("Nombre del apartamento").setResizable(false);
		grid2.addColumn(Booking::isConfirmation).setCaption("Confirmación").setResizable(false);
		grid2.addColumn(Booking::getEntryDate).setCaption("Fecha de entrada" ).setResizable(false);
		grid2.addColumn(Booking::getDepartureDate).setCaption("Fecha de salida").setResizable(false);
		grid2.addColumn(Booking::getTotalPrice).setCaption("Precio total").setResizable(false);
		
		
		// Hook logic to components
		// Connect selected Booking to editor or hide if none is selected
		grid2.asSingleSelect().addValueChangeListener( ex -> {
			editor.editBooking(ex.getValue());
		});
	
		
		listBookingsAp(null);
		
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
	
	private void listBookingsAp(String filterText)
	{
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Apartment> a = new ArrayList<Apartment>();
		List<Booking> b = new ArrayList<Booking>();
		
		for(Booking book: bookingService.findAll())
		{
			if(book.getApartment().getUser().getId() == user_.getId())
			{
				a.add(book.getApartment());
				b.add(book);
			}
			
		}
		
		grid2.setItems(b);
		apartmentNameGrid2.setItems(a);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
