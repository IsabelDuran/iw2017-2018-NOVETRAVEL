package es.uca.iw.proyectoCompleto.bookings;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.facturas.Factura;
import es.uca.iw.proyectoCompleto.facturas.FacturaService;
import es.uca.iw.proyectoCompleto.security.MailService;
import es.uca.iw.proyectoCompleto.users.User;

@SpringComponent
@UIScope
public class BookingEditor extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private BookingService bookingService;

	private Booking booking_;

	@Autowired
	private ApartmentService service2;

	@Autowired
	private FacturaService serviceFact;

	private User user_, userAnfitrion;
	private Long apId;
	private Apartment apartment;
	private Factura factura;

	@Autowired
	private MailService mailService;
	
	/**
	 * The currently edited booking
	 */

	private Binder<Booking> binder = new Binder<>(Booking.class);

	/* Fields to edit properties in Booking entity */
	// Create a DateField with the default style
	DateField entryDate = new DateField();
	DateField departureDate = new DateField();

	List<Booking> reservasPorApartamento;

	boolean fechaValida;

	/* Action buttons */
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	Button confirm = new Button("Confirmar");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel);
	CssLayout actions2 = new CssLayout(delete, confirm);

	public BookingEditor() {

		editDate();
		addComponents(entryDate, departureDate, actions, actions2);

		confirm.setVisible(false);
		delete.setVisible(false);
		/// bind using naming convention

		Binder.BindingBuilder<Booking, LocalDate> returnBB = binder.forField(entryDate).withValidator(
				departureDate_ -> !departureDate_.isBefore(LocalDate.now()),
				"La fecha de entrada debe ser posterior a la fecha de hoy");
		Binder.Binding<Booking, LocalDate> returnB = returnBB.bind(Booking::getEntryDate, Booking::setEntryDate);
		departureDate.addValueChangeListener(event -> returnB.validate());

		Binder.BindingBuilder<Booking, LocalDate> returnBindingBuilder = binder.forField(departureDate)
				.withValidator(departureDate_ -> !departureDate_.isBefore(entryDate.getValue()),
						"La fecha de salida no puede ser anterior a la fecha de entrada")
				.withValidator(departureDate_ -> !departureDate_.isBefore(LocalDate.now()),
						"La fecha de entrada debe ser posterior a la fecha de hoy");
		Binder.Binding<Booking, LocalDate> returnBinder = returnBindingBuilder.bind(Booking::getDepartureDate,
				Booking::setDepartureDate);
		departureDate.addValueChangeListener(event -> returnBinder.validate());

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset

		save.addClickListener(e -> {
			try {

				if (!bookingService.isApartmentFreeBetweenDates(apartment, entryDate.getValue(),
						departureDate.getValue()))
					throw new Exception("Lo sentimos, ya existe una reserva en esa fecha");

				user_.addBooking(booking_);

				double price = ((int) DAYS.between(entryDate.getValue(), departureDate.getValue()) + 1)
						* apartment.getPricePerDay();
				booking_.setTotalPrice(price);

				binder.writeBean(booking_);

				String detalles = " Nombre del apartamento: " + apartment.getName() + "\n "
						+ "Descripción del apartamento: " + apartment.getDescription() + "\n " + "Fecha de entrada: "
						+ booking_.getEntryDate().toString() + "\n " + "Fecha de salida: "
						+ booking_.getDepartureDate().toString() + "\n " + "Precio total: " + booking_.getTotalPrice()
						+ " euros.\n\n\n";

				factura.setDetalles(detalles);
				factura.setFechaFactura(LocalDate.now());

				this.factura.setBooking(booking_);
				booking_.setFactura(this.factura);
				serviceFact.save(factura);

				bookingService.save(booking_);


				String mensaje = "Estimado/a " + userAnfitrion.getFirstName() + " " + userAnfitrion.getLastName()
						+ ",\n\n " + "El usuario " + user_.getUsername()
						+ " ha realizado la siguiente reserva del apartamento que se detalla a continuación:" + "\n\n"
						+ " Nombre del apartamento: " + apartment.getName() + "\n " + "Descripción del apartamento: "
						+ apartment.getDescription() + "\n " + "Fecha de entrada: " + booking_.getEntryDate().toString()
						+ "\n " + "Fecha de salida: " + booking_.getDepartureDate().toString() + "\n "
						+ "Precio total: " + booking_.getTotalPrice() + " euros.\n\n"
						+ "Si está de acuerdo con la reserva, acceda a la gestión de sus reservas para confirmarla."
						+ "\n\n" + "Gracias por confiar en nuestros servicios, \n\n Atte: El equipo de Novetravel. ";

				mailService.enviarCorreo("Reserva pendiente de confirmación", mensaje, userAnfitrion.getEmail());
				
				mensaje = "Reserva realizada con éxito.\n En breve recibirá un correo con los datos de la reserva y cuando el anfitrión"
						+ " la confirme, recibirá un correo indicándolo." 
						+ "\n\nGracias por confiar en nuestros servicios, \n\n Atte: El equipo de Novetravel. ";

				
				mailService.enviarCorreo("Reserva realizada con éxito" , mensaje, user_.getEmail());

				Notification.show(
						"Reserva realizada con éxito.\n En breve recibirá un correo \ncon los datos de la reserva \ny la confirmación de la misma");
				getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);

			} catch (ValidationException ex) {
				ValidationResult validationResult = ex.getValidationErrors().iterator().next();
				Notification.show(validationResult.getErrorMessage());
			} catch (Exception exc) {
				Notification.show(exc.getMessage());
			}

		});

		delete.addClickListener(e -> bookingService.delete(booking_));
		cancel.addClickListener(e -> {
			getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);
		});

		confirm.addClickListener(e -> {

			String detalles = " Nombre del apartamento: " + apartment.getName() + "\n "
					+ "Descripción del apartamento: " + apartment.getDescription() + "\n " + "Fecha de entrada: "
					+ booking_.getEntryDate().toString() + "\n " + "Fecha de salida: "
					+ booking_.getDepartureDate().toString() + "\n " + "Precio total: " + booking_.getTotalPrice()
					+ " euros.\n\n\n";

			this.factura.generarPdf();
			/// GENERAR FACTURA///
			String mensaje = "Estimado/a " + user_.getFirstName() + " " + user_.getLastName() + ",\n\n " + detalles
					+ "Gracias por confiar en nuestros servicios, \n\n El equipo de Novetravel. ";

			// correo.enviarCorreo("Confirmación de la reserva", mensaje, user_.getEmail());
			mailService.enviarCorreoAttachment("Reserva pendiente de confirmación", mensaje, user_.getEmail());
			booking_.setConfirmation(true);
			bookingService.delete(booking_);
			bookingService.save(booking_);

			Notification.show("Reserva confirmada");

		});

		// setVisible(false);

	}

	// Set the date to present
	public void editDate() {
		entryDate.setValue(LocalDate.now());
		departureDate.setValue(LocalDate.now());

		entryDate.setDateFormat("dd-MM-yyy");
		entryDate.setPlaceholder("dd-MM-yyy");
		departureDate.setDateFormat("dd-MM-yyy");
		departureDate.setPlaceholder("dd-MM-yyy");
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editBooking(Booking booking) {

		if (booking == null) {
			this.booking_ = new Booking();
			this.factura = new Factura();
			binder.setBean(booking_);
		} else {
			this.booking_ = booking;

			if (booking.getFactura() == null)
				this.factura = new Factura();
			else
				this.factura = booking.getFactura();

			binder.setBean(booking_);

			user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			apId = booking_.getApartment().getId();
			apartment = service2.loadApartmentById(apId);
			userAnfitrion = apartment.getUser();

			if (user_.getId() == userAnfitrion.getId() && apartment.getUser().getId() == userAnfitrion.getId()
					&& !booking_.isConfirmation()) // Solo el anfitrion puede confirmar una reserva o eliminarla y solo
													// de sus pisos
				confirm.setVisible(true);
			else
				confirm.setVisible(false);

			if (user_.getId() == userAnfitrion.getId() && apartment.getUser().getId() == userAnfitrion.getId()) // Solo
																												// el
																												// anfitrion
																												// puede
																												// confirmar
																												// una
																												// reserva
																												// o
																												// eliminarla
																												// y
																												// solo
																												// de
																												// sus
																												// pisos
				delete.setVisible(true);
			else
				delete.setVisible(false);
		}

		setVisible(true);
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
		confirm.addClickListener(e -> h.onChange());
		cancel.addClickListener(e -> h.onChange());
	}

}
