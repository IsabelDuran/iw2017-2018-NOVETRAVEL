package es.uca.iw.proyectoCompleto.bookings;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;
import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.bookings.BookingEditor;
import es.uca.iw.proyectoCompleto.bookings.BookingService;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentEditor;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
@SpringView(name = BookingManagementView.VIEW_NAME)
public class BookingManagementView extends HorizontalLayout implements View {
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "bookingManagementView";

	private Grid<Booking> grid;
	
	private Grid<Apartment> grid2;
	
	//private TextField filter;

	private BookingEditor editor;

	private final BookingService service;
	
	private final ApartmentService service2;

	@Autowired
	public BookingManagementView(BookingService service, BookingEditor editor, ApartmentService service2 ) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>();
		this.grid2 = new Grid<>();
		this.service2 = service2;
		    
	}

	
	@PostConstruct
	void init() {
		
		/// build layout
		addComponents(grid2,grid,editor);
		
		grid2.setHeight(300, Unit.PIXELS);
		grid2.setWidth(200, Unit.PIXELS);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(1000, Unit.PIXELS);
			
		grid2.addColumn(Apartment::getName).setCaption("Nombre del apartamento").setResizable(false);
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
			//Bookings(editor.getValue());
		
		}); 
			
		
		// Initialize listing
		listBookings(null);

	}

	private void listBookings(String filterText) {
		grid.setItems(service.findAll());
		
		List<Apartment> a = new ArrayList<Apartment>();
		List<Booking> b = new ArrayList<Booking>();
		
		LocalDate entryDate;
		LocalDate departureDate;
		
		Double price;
		
		for(Booking book: service.findAll())
		{
			for(Apartment apart: service2.findAll())
			{
				if(book.getApartment().getId() == apart.getId())
				{
					entryDate = book.getEntryDate();
					departureDate = book.getDepartureDate();
					
					price = ((double)DAYS.between(entryDate, departureDate)+1) * apart.getPrice_per_day();
					book.setTotalPrice(price);
					
					a.add(apart);
					b.add(book);
				}
			}
			
		}
		
		grid.setItems(b);
		grid2.setItems(a);
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
