package es.uca.iw.proyectoCompleto.bookings;

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

import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.bookings.BookingEditor;
import es.uca.iw.proyectoCompleto.bookings.BookingService;

@SpringView(name = BookingManagementView.VIEW_NAME)
public class BookingManagementView extends VerticalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "bookingManagementView";

	private Grid<Booking> grid;
	//private TextField filter;

	private BookingEditor editor;

	private final BookingService service;

	@Autowired
	public BookingManagementView(BookingService service, BookingEditor editor) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Booking.class);
	//	this.filter = new TextField();
		    
	}

	
	@PostConstruct
	void init() {
		
		// build layout
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(900, Unit.PIXELS);
		grid.setColumns("id", "entryDate_", "departureDate_","totalPrice_");	
		
		// Hook logic to components

		// Connect selected Booking to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editBooking(e.getValue());
		});
		
		// Listen changes made by the editor, refresh data from backend
	/*	editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listBookings(filter.getValue());
		}); */

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
