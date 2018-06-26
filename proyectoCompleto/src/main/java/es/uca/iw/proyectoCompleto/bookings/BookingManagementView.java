package es.uca.iw.proyectoCompleto.bookings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import es.uca.iw.proyectoCompleto.users.User;

@SpringView(name = BookingManagementView.VIEW_NAME)
public class BookingManagementView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "bookingManagementView";

	private Grid<Booking> grid, receivedBookingsGrid;

	private Grid<Apartment> apartmentNameGrid, apartmentNamesGrid;

	private Grid<User> userNameGrid, usernamesGrid;

	@Autowired
	private BookingEditor editor;

	@Autowired
	private BookingService bookingService;


	@PostConstruct
	void init() {
		/// build layout
		this.grid = new Grid<>();
		this.receivedBookingsGrid = new Grid<>();
		this.apartmentNameGrid = new Grid<>();
		this.apartmentNamesGrid = new Grid<>();
		this.userNameGrid = new Grid<>();
		this.usernamesGrid = new Grid<>();
		
		HorizontalLayout h = new HorizontalLayout();
		Button goBack = new Button("Volver", e -> getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME));
		Label titulo = new Label("MIS RESERVAS");
		addComponents(goBack, titulo);

		editor.setVisible(false);
		h.addComponents(apartmentNameGrid, grid, userNameGrid, editor);

		addComponents(h);

		apartmentNameGrid.setHeight(300, Unit.PIXELS);
		apartmentNameGrid.setWidth(250, Unit.PIXELS);

		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(100, Unit.PERCENTAGE);

		userNameGrid.setHeight(300, Unit.PIXELS);
		userNameGrid.setWidth(250, Unit.PIXELS);

		apartmentNameGrid.addColumn(Apartment::getName).setCaption("Nombre del apartamento").setResizable(false);
		grid.addColumn(Booking::isConfirmation).setCaption("Confirmación").setResizable(false);
		grid.addColumn(Booking::getEntryDate).setCaption("Fecha de entrada").setResizable(false);
		grid.addColumn(Booking::getDepartureDate).setCaption("Fecha de salida").setResizable(false);
		grid.addColumn(Booking::getTotalPrice).setCaption("Precio total").setResizable(false);
		userNameGrid.addColumn(User::getEmail).setCaption("E-mail de contacto").setResizable(false);

		// Hook logic to components
		// Connect selected Booking to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editBooking(e.getValue());
		});

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		});

		// Initialize listing
		listMyBookings(null);

		// PARA LAS RESERVAS DE TU APARTAMENTO
		HorizontalLayout h2 = new HorizontalLayout();
		Label titulo2 = new Label("RESERVAS EN MIS APARTAMENTOS");
		addComponent(titulo2);

		h2.addComponents(apartmentNamesGrid, receivedBookingsGrid, usernamesGrid);

		addComponents(h2);

		apartmentNamesGrid.setHeight(300, Unit.PIXELS);
		apartmentNamesGrid.setWidth(250, Unit.PIXELS);

		receivedBookingsGrid.setHeight(300, Unit.PIXELS);
		receivedBookingsGrid.setWidth(100, Unit.PERCENTAGE);

		usernamesGrid.setHeight(300, Unit.PIXELS);
		usernamesGrid.setWidth(250, Unit.PIXELS);

		apartmentNamesGrid.addColumn(Apartment::getName).setCaption("Nombre del apartamento").setResizable(false);
		receivedBookingsGrid.addColumn(Booking::isConfirmation).setCaption("Confirmación").setResizable(false);
		receivedBookingsGrid.addColumn(Booking::getEntryDate).setCaption("Fecha de entrada").setResizable(false);
		receivedBookingsGrid.addColumn(Booking::getDepartureDate).setCaption("Fecha de salida").setResizable(false);
		receivedBookingsGrid.addColumn(Booking::getTotalPrice).setCaption("Precio total").setResizable(false);
		usernamesGrid.addColumn(User::getEmail).setCaption("E-mail de contacto").setResizable(false);

		// Hook logic to components
		// Connect selected Booking to editor or hide if none is selected
		receivedBookingsGrid.asSingleSelect().addValueChangeListener(ex -> {
			editor.editBooking(ex.getValue());
		});

		listBookingsOfMyApartments(null);

	}

	private void listMyBookings(String filterText) {
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Apartment> a = new ArrayList<Apartment>();
		Set<Booking> b = new HashSet<Booking>();
		List<User> u = new ArrayList<User>();

		b = bookingService.findUserBookings(user_);

		for (Booking book : b) {
			a.add(book.getApartment());
			u.add(book.getApartment().getUser());
		}

		grid.setItems(b);
		apartmentNameGrid.setItems(a);
		userNameGrid.setItems(u);

	}

	private void listBookingsOfMyApartments(String filterText) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Apartment> apartments = new ArrayList<Apartment>();
		List<Booking> receivedBookings = new ArrayList<Booking>();
		List<User> bookerUsers = new ArrayList<User>();

		for (Booking book : bookingService.findUserApartmentsBookings(user)) {
				apartments.add(book.getApartment());
				receivedBookings.add(book);
				bookerUsers.add(book.getUser());
		}

		receivedBookingsGrid.setItems(receivedBookings);
		apartmentNamesGrid.setItems(apartments);
		usernamesGrid.setItems(bookerUsers);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
