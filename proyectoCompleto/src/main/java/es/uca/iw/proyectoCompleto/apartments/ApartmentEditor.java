package es.uca.iw.proyectoCompleto.apartments;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.location.Location;
import es.uca.iw.proyectoCompleto.security.MailService;
import es.uca.iw.proyectoCompleto.users.User;

@SpringView(name = ApartmentEditor.VIEW_NAME)
public class ApartmentEditor extends VerticalLayout implements View {

	/**
	 * 
	 */

	public static final String VIEW_NAME = "apartmentEditorView";
	private static final long serialVersionUID = 1L;

	@Autowired
	private ApartmentService service;
	private Apartment apartment;
	private Location location;

	private Binder<Apartment> binder;
	private Binder<Location> loc_binder;
	@Autowired
	private MailService mailService;
	
	@PostConstruct
	public void init() {
		
		/* Fields to edit properties in Apartment entity */
		TextField name = new TextField("Nombre del apartamento:");
		TextArea description = new TextArea("Descripción");
		TextField price_per_day = new TextField("Precio por día");
		TextField max_hosts = new TextField("Número máximo de huespedes: ");
		TextField number_beds = new TextField("Número de camas: ");
		TextField number_rooms = new TextField("Número de habitaciones: ");
		TextField number_bathrooms = new TextField("Número de baños: ");
		TextField squared_meters = new TextField("Metros cuadrados: ");
		CheckBox crib = new CheckBox("Cuna");
		CheckBox parking = new CheckBox("Aparcamiento");
		CheckBox wifi = new CheckBox("Wifi disponible");
		CheckBox own_bathroom = new CheckBox("Baño propio");
		CheckBox own_kitchen = new CheckBox("Cocina propia");
		CheckBox pets_allowed = new CheckBox("Se permiten mascotas");
		CheckBox kids_allowed = new CheckBox("Se permiten niños");
		CheckBox smoking_allowed = new CheckBox("Se permite fumar");

		TextField city_ = new TextField("Ciudad:");
		TextField street_ = new TextField("Calle:");
		TextField number_ = new TextField("Numero:");
		TextField postalcode_ = new TextField("Código postal:");
		TextField floor_ = new TextField("Piso:");
		TextField letter_ = new TextField("Letra:");

		Label description_label = new Label("Descripción del apartamento:");
		Label service_label = new Label("Selecciona los servicios que posee tu apartamento:");
		Label location_label = new Label("¿Dónde está tu apartamento?");

		/* Action buttons */
		Button save = new Button("Guardar");
		Button cancel = new Button("Cancelar");
		Button delete = new Button("Borrar");

		/* Layout for buttons */
		CssLayout actions = new CssLayout(save, cancel, delete);

		
		List<String> apartmentType = new ArrayList<>();
		apartmentType.add("Villa");
		apartmentType.add("Casa unifamiliar");
		apartmentType.add("Habitación privada");
		apartmentType.add("Piso");
		apartmentType.add("Albergue");
		apartmentType.add("Suit");
		apartmentType.add("Mansión");
		apartmentType.add("Casa rural");
		apartmentType.add("Balcón Ibizeño");
		
		ComboBox<String> selectApartmentType =
			    new ComboBox<>("Selecciona el tipo de alojamiento");
		selectApartmentType.setItems(apartmentType);
		
		this.binder = new Binder<>();
		this.loc_binder = new Binder<>();
		description_label.setStyleName(ValoTheme.LABEL_H2);
		service_label.setStyleName(ValoTheme.LABEL_H2);
		location_label.setStyleName(ValoTheme.LABEL_H2);
		addComponents(description_label, name, description, price_per_day, selectApartmentType, max_hosts, number_beds,
				number_rooms, number_bathrooms, squared_meters, service_label, crib, parking, wifi, own_bathroom,
				own_kitchen, pets_allowed, kids_allowed, smoking_allowed, location_label, city_, street_, number_,
				postalcode_, floor_, letter_, actions);

		// bind using naming convention
		binder.forField(name).asRequired("Introduce el nombre del apartamento").bind(Apartment::getName,
				Apartment::setName);
		binder.forField(description).asRequired("Introduce una descripción").bind(Apartment::getDescription,
				Apartment::setDescription);
		binder.forField(price_per_day).asRequired("Introduce un precio")
				.withConverter(new StringToDoubleConverter("Introducir un número"))
				.bind(Apartment::getPricePerDay, Apartment::setPricePerDay);
		binder.forField(selectApartmentType).asRequired("Introduce el tipo de apartamento").bind(Apartment::getApartmentType, Apartment::setApartmentType);
		binder.forField(max_hosts).asRequired("Introduce un máximo de huespedes")
				.withConverter(new StringToIntegerConverter("Introducir un número"))
				.bind(Apartment::getMaxHosts, Apartment::setMaxHosts);
		binder.forField(number_beds).asRequired("Introduce el número de camas")
				.withConverter(new StringToIntegerConverter("Introducir un número"))
				.bind(Apartment::getNumberBeds, Apartment::setNumberBeds);
		binder.forField(number_rooms).withNullRepresentation("").withConverter(new StringToIntegerConverter(0, "Introducir un número"))
				.bind(Apartment::getRooms, Apartment::setRooms);
		binder.forField(number_bathrooms).withNullRepresentation("").withConverter(new StringToIntegerConverter("Introducir un número"))
				.bind(Apartment::getNumberBathrooms, Apartment::setNumberBathrooms);

		binder.forField(squared_meters).asRequired("Introduce los metros cuadrados")
				.withConverter(new StringToIntegerConverter("Introducir un número"))
				.bind(Apartment::getSquaredMeters, Apartment::setSquaredMeters);
		binder.forField(crib).bind(Apartment::getCrib, Apartment::setCrib);
		binder.forField(parking).bind(Apartment::getParking, Apartment::setParking);
		binder.forField(wifi).bind(Apartment::getWifi, Apartment::setWifi);
		binder.forField(own_bathroom).bind(Apartment::getOwnBathroom, Apartment::setOwnBathroom);
		binder.forField(own_kitchen).bind(Apartment::getKitchen, Apartment::setKitchen);
		binder.forField(pets_allowed).bind(Apartment::getPetsAllowed, Apartment::setPetsAllowed);
		binder.forField(kids_allowed).bind(Apartment::getKidsAllowed, Apartment::setKidsAllowed);
		binder.forField(smoking_allowed).bind(Apartment::getSmokingAllowed, Apartment::setSmokingAllowed);

		loc_binder.forField(city_).asRequired("Introduce la ciudad").bind(Location::getCity_, Location::setCity_);
		loc_binder.forField(street_).asRequired("Introduce la calle").bind(Location::getStreet_, Location::setStreet_);
		loc_binder.forField(number_).asRequired("Introduce el número")
				.withConverter(new StringToIntegerConverter("Introducir un número"))
				.bind(Location::getNumber_, Location::setNumber_);
		loc_binder.forField(postalcode_).asRequired("Introduce el códido postal").bind(Location::getPostalCode_,
				Location::setPostalCode_);
		loc_binder.forField(floor_).withNullRepresentation("").withConverter(new StringToIntegerConverter("Introducir un número"))
				.bind(Location::getFloor_, Location::setFloor_);
		loc_binder.forField(letter_).withNullRepresentation("").bind(Location::getLetter_, Location::setLetter_);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {


			try {
				loc_binder.writeBean(this.location);
				binder.writeBean(this.apartment);
				
				this.location.setApartment(apartment);
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				user.getApartments().add(apartment);
				apartment.setUser(user);
				apartment.setLocation(this.location);
				service.save(this.apartment);
				
				Notification.show("Apartamento guardado con éxito");
				
				String mensaje = "Estimado/a " + user.getFirstName() + " " + user.getLastName()
				+ "Ha registrado el siguiente apartamento:" + "\n\n"
				+ "\tNombre del apartamento: " + apartment.getName() + "\n"
				+ "\tDescripción:" + apartment.getDescription() + "\n\n"
				+ "Con las siguientes características \n"
				+ "\t" + "Número máximo de huéspedes:\t" + apartment.getMaxHosts() + "\n"
				+ "\t" + "Número de metros cuadrados:\t" + apartment.getSquaredMeters() + "\n"
				+ "\t" + "Tipo:\t" + apartment.getApartmentType() + "\n"
				+ "\t" + "Número de camas:\t" + apartment.getNumberBeds() + "\n"
				+ "\t" + "Precio por día:\t" + apartment.getPricePerDay() + "\n\n"

				+ "¡Y mucho más! \n\n"

				+ "Además ha registrado su apartamento en la siguiente ubicación:\n"
				+ "\tCiudad:\t" + location.getCity_() + "\n"
				+ "\tCalle:\t" + location.getStreet_() + "\n"
				+ "\tNumero:\t" + location.getNumber_() + "\n"
				+ "\tNumero:\t" + location.getPostalCode_()
				+ "\n\n" + "Gracias por confiar en nuestros servicios, \n\n Atte: El equipo de Novetravel. ";

				mailService.enviarCorreo("Reserva pendiente de confirmación", mensaje, user.getEmail());

				
				
				getUI().getNavigator().navigateTo(ApartmentManagementView.VIEW_NAME);

			} catch (ValidationException e1) {
				ValidationResult validationResult = e1.getValidationErrors().iterator().next();
				Notification.show(validationResult.getErrorMessage());

			}

		});

		delete.addClickListener(e -> {
			service.delete(apartment);
			getUI().getNavigator().navigateTo(ApartmentManagementView.VIEW_NAME);
		});
		cancel.addClickListener(e -> getUI().getNavigator().navigateTo(ApartmentManagementView.VIEW_NAME));
	
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Apartment editedApartment = (Apartment) VaadinSession.getCurrent().getAttribute("apartamentoEditado");
		if (editedApartment == null) {
			this.apartment = new Apartment();
			this.location = new Location();

		} else {
			this.apartment = editedApartment;
			this.location = this.apartment.getLocation();
			binder.setBean(this.apartment);
			loc_binder.setBean(this.location);
			
		}

	}

}