package es.uca.iw.proyectoCompleto.bookings;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

import org.joda.time.*;

@SpringComponent
@UIScope
public class BookingEditor extends VerticalLayout  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private BookingService service;
	
	private Booking booking_;
	
	
	/**
	 * The currently edited booking
	 */
	

	private Binder<Booking> binder = new Binder<>(Booking.class);
		
	/* Fields to edit properties in Booking entity */
	//TextField totalPrice = new TextField("Precio total");
	// Create a DateField with the default style	
	DateField entryDate = new DateField();
	DateField departureDate = new DateField();
	
	List<Booking> reservasPorApartamento;
	
	boolean fechaValida;
	
	/* Action buttons */
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	
	public BookingEditor() {

		editDate();
		addComponents(entryDate,departureDate,actions);
 
		/// bind using naming convention 
		//binder.forField(totalPrice).withConverter(new StringToDoubleConverter("")).bind(Booking::getTotalPrice, Booking::setTotalPrice);
		//binder.setReadOnly(true);
		
		Binder.BindingBuilder<Booking, LocalDate> returnBB = binder.forField(entryDate).withValidator(departureDate_ -> !departureDate_.isBefore(LocalDate.now()), "Departure date should be after local date");	
		Binder.Binding<Booking, LocalDate> returnB = returnBB.bind(Booking::getEntryDate, Booking::setEntryDate);
		departureDate.addValueChangeListener(event -> returnB.validate());
		
		Binder.BindingBuilder<Booking, LocalDate> returnBindingBuilder = binder.forField(departureDate).withValidator(departureDate_ -> !departureDate_.isBefore(entryDate.getValue()),	 "Cannot return before departing").withValidator(departureDate_ -> !departureDate_.isBefore(LocalDate.now()), "Departure date should be after local date");		
		Binder.Binding<Booking, LocalDate> returnBinder = returnBindingBuilder.bind(Booking::getDepartureDate, Booking::setDepartureDate);
		departureDate.addValueChangeListener(event -> returnBinder.validate());
		
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
	
		save.addClickListener(e -> {
			try {

				fechaValida = comprobarFecha();
				
				if(!fechaValida)
					throw new Exception ("Lo sentimos, ya existe una reserva en esa fecha");
				
				binder.writeBean(booking_);
				
				service.save(booking_);
				
				Notification.show("Reserva realizada con éxito.\n Se ha enviado un correo para \nconfirmar la reserva.");
				getUI().getNavigator().navigateTo(ApartmentListView.VIEW_NAME);
				
			} catch(ValidationException ex) {
				ValidationResult validationResult = ex.getValidationErrors().iterator().next();
				Notification.show(validationResult.getErrorMessage());
		    } catch (Exception exc) {
		    	Notification.show(exc.getMessage());
		    }
			 
		});
		
		
		delete.addClickListener(e -> service.delete(booking_));
		cancel.addClickListener(e -> {
			getUI().getNavigator().navigateTo(ApartmentListView.VIEW_NAME);
		});
		setVisible(false);
		
		// Solo borra el admin
		delete.setVisible(SecurityUtils.hasRole("ROLE_ADMIN"));
	}
	
	private boolean comprobarFecha()
	{
		boolean valido = true;
		Long apId = booking_.getApartment().getId();
		
		Booking aux;
		
		DateTime f1,f2,f3,f4;
		
		f1 = new DateTime(entryDate.getValue().toString());
		f2 = new DateTime(departureDate.getValue().toString());
		
		Interval intervalo2,intervalo1 = new Interval(f1,f2);
		
		reservasPorApartamento = service.loadBookingByApartmentId(apId);
		
		if(reservasPorApartamento.size() != 0)
		{
			Iterator<Booking> it = reservasPorApartamento.iterator();
			
			while(it.hasNext() && valido)
			{	
				aux = it.next();
				
				f3 = new DateTime(aux.getEntryDate().toString());
				f4 = new DateTime(aux.getDepartureDate().toString());
				
				intervalo2 = new Interval(f3,f4);
				
				if(intervalo1.overlaps(intervalo2))
					valido = false;
			}
		}
		

		
		return valido;
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
	}
	

}