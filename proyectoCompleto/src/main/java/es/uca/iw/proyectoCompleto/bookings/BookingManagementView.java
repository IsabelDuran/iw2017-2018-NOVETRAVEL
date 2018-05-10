package es.uca.iw.proyectoCompleto.bookings;

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

import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.bookings.BookingEditor;
import es.uca.iw.proyectoCompleto.bookings.BookingService;

@SpringView(name = BookingManagementView.VIEW_NAME)
public class BookingManagementView extends VerticalLayout implements View{

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "bookingManagementView";

	private Grid<Booking> grid;
	private TextField filter;
	private Button addNewBtn;

	private BookingEditor editor;

	
	private final BookingService service;

	@Autowired
	public BookingManagementView(BookingService service,BookingEditor editor) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(Booking.class);
		this.filter = new TextField();
	    
	}

	@PostConstruct
	void init() {
		
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		
		addComponents(actions, grid, editor);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(900, Unit.PIXELS);
		grid.setColumns("Id","Fecha de entrada", "Fecha de Salida", "Precio total");

		filter.setPlaceholder("Filtrar por fecha de entrada");
		
		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listBooking(e.getValue()));

		// Connect selected Report to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
	//		editor.editReport(e.getValue());
		});

		
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listBooking(filter.getValue());
		});

		// Initialize listing
		listBooking(null);
		
	}

	private void listBooking(String filterText) {
		
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(service.findAll());
		} 
		
		//grid.setItems(service.findAll());
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
