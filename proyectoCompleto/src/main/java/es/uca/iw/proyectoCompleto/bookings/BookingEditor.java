package es.uca.iw.proyectoCompleto.bookings;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
//import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;

@SpringComponent
@UIScope
public class BookingEditor extends VerticalLayout  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private BookingService service;
	
	@Autowired
	private ApartmentService serviceAp;

	private Booking booking_;
	
	/**
	 * The currently edited booking
	 */
	

	private Binder<Booking> binder = new Binder<>(Booking.class);
		
	/* Fields to edit properties in Booking entity */
	TextField totalPrice = new TextField("Precio total");
	// Create a DateField with the default style	
	DateField entryDate = new DateField();
	DateField departureDate = new DateField();
	
	
	/* Action buttons */
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	
	public BookingEditor() {

        
		editDate();
		addComponents(entryDate,departureDate,totalPrice,actions);
 
		/// bind using naming convention 
		binder.forField(totalPrice).withConverter(new StringToDoubleConverter("")).bind(Booking::getTotalPrice, Booking::setTotalPrice);
		binder.setReadOnly(true);
		binder.forField(entryDate).bind(Booking::getEntryDate, Booking::setEntryDate);
		binder.forField(departureDate).bind(Booking::getDepartureDate, Booking::setDepartureDate);		
	
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(booking_));
		delete.addClickListener(e -> service.delete(booking_));
		cancel.addClickListener(e -> editBooking(booking_));
		setVisible(false);
		
		// Solo borra el admin
		//delete.setEnabled(SecurityUtils.hasRole("ADMIN"));
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
			binder.setBean(booking_);
		}
		else
		{
			this.booking_ = booking;
			binder.setBean(booking_);
		}
		
		setVisible(true);
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
		cancel.addClickListener(e-> h.onChange());
	}

}