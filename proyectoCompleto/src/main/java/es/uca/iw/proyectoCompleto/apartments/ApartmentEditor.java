package es.uca.iw.proyectoCompleto.apartments;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.location.Location;
import es.uca.iw.proyectoCompleto.location.LocationService;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.User;

@SpringComponent
@UIScope
public class ApartmentEditor extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The currently edited apartment
	 */
	
	@Autowired
	private ApartmentService service;
	@Autowired
	private LocationService ls;
	
	private Apartment apartment;
	private Location location;
	
	private Binder<Apartment> binder = new Binder<>();
	private Binder<Location> loc_binder = new Binder<>();

	/* Fields to edit properties in Apartment entity */
	TextField name = new TextField("Nombre del apartamento:");
	TextField description = new TextField("Descripcción");
	TextField price_per_day = new TextField("Precio por día");
	TextField apartment_type = new TextField("Tipo de alojamiento");
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
	TextField postalcode_ = new TextField("Código postal:");
	TextField floor_ = new TextField("Piso:");
	TextField letter_ = new TextField("Letra:");
    TextField number_ = new TextField("Numero:");
    	
	Label description_label = new Label("Descripción del apartamento:");
	Label service_label = new Label("Selecciona los servicios que posee tu apartamento:");
	Label location_label = new Label("¿Dónde está tu apartamento?");


	/* Action buttons */
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);

	public ApartmentEditor() {

		
		description_label.setStyleName(ValoTheme.LABEL_H2);
		service_label.setStyleName(ValoTheme.LABEL_H2);
		location_label.setStyleName(ValoTheme.LABEL_H2);
		addComponents(description_label, name, description, price_per_day, apartment_type, max_hosts, number_beds, number_rooms, number_bathrooms,
				squared_meters, service_label, crib, parking, wifi, own_bathroom, own_kitchen, pets_allowed, kids_allowed, smoking_allowed, location_label, 
				city_, street_, postalcode_, floor_, letter_, number_, actions);

		// bind using naming convention
		binder.forField(name).bind(Apartment::getName,Apartment::setName);
		binder.forField(description).bind(Apartment::getDescription,Apartment::setDescription);
		binder.forField(price_per_day).withConverter(new StringToDoubleConverter("Introducir un número")).bind(Apartment::getPrice_per_day,Apartment::setPrice_per_day);
		binder.forField(apartment_type).bind(Apartment::getApartment_type,Apartment::setApartment_type);
		binder.forField(max_hosts).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment::getMax_hosts, Apartment::setMax_hosts);
		binder.forField(number_beds).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment::getNumber_beds, Apartment::setNumber_beds);
		binder.forField(number_rooms).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment::getRooms, Apartment::setRooms);
		binder.forField(number_bathrooms).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment::getNumber_bathrooms, Apartment::setNumber_bathrooms);

		binder.forField(squared_meters).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment::getSquared_meters, 
				Apartment::setSquared_meters);
		binder.forField(crib).bind(Apartment::isCrib, Apartment::setCrib);
		binder.forField(parking).bind(Apartment::isParking, Apartment::setParking);
		binder.forField(wifi).bind(Apartment::isWifi, Apartment::setWifi);
		binder.forField(own_bathroom).bind(Apartment::isOwn_bathroom, Apartment::setOwn_bathroom);
		binder.forField(own_kitchen).bind(Apartment::isKitchen, Apartment::setKitchen);
		binder.forField(pets_allowed).bind(Apartment::isPets_allowed, Apartment::setPets_allowed);
		binder.forField(kids_allowed).bind(Apartment::isKids_allowed, Apartment::setKids_allowed);
		binder.forField(smoking_allowed).bind(Apartment::isSmoking_allowed, Apartment::setSmoking_allowed);

		
		// Configure and style components
				
		
		loc_binder.forField(city_).bind(Location::getCity_, Location::setCity_);
		loc_binder.forField(street_).bind(Location::getStreet_, Location::setStreet_);
		loc_binder.forField(postalcode_).bind(Location::getPostalCode_, Location::setPostalCode_);
		loc_binder.forField(floor_).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Location::getFloor_, Location::setFloor_);
		loc_binder.forField(letter_).bind(Location::getLetter_, Location::setLetter_);
		loc_binder.forField(number_).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Location::getNumber_, Location::setNumber_);
		
		// 		Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset

		save.addClickListener(e -> {service.save(apartment);
		User user_ = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user_.addApartments(apartment);
		});
		delete.addClickListener(e -> service.delete(apartment));
		cancel.addClickListener(e -> editApartment(apartment));
		save.addClickListener(e -> 
		{
			if(loc_binder == null
			   || binder == null
			   || this.location == null
			   || this.apartment == null)
				System.out.println("ALGO ES NULO");
			
			try {
				loc_binder.writeBean(this.location);
				binder.writeBean(this.apartment);
				 
			} catch (ValidationException e1) {
				// TODO Auto-generated catch block

			}
			ls.save(this.location);
			
			apartment.setLocation(this.location);
			service.save(this.apartment);
		});
		
		delete.addClickListener(e -> service.delete(apartment));
		cancel.addClickListener(e -> editApartment(apartment));
		setVisible(false);
		
		// Solo borra el admin
		delete.setEnabled(SecurityUtils.hasRole("ROLE_ADMIN"));
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editApartment(Apartment apartment) {
		if(apartment == null)
		{
			this.apartment = new Apartment();
			this.apartment.setOffered_services(new Apartment_OfferedServices());
			binder.setBean(apartment);
			this.location = new Location();
			loc_binder.setBean(location);
		}
		else
		{
			this.apartment = apartment;
			this.location = apartment.getLocation();
			binder.setBean(apartment);
			loc_binder.setBean(this.location);
		}
		
		setVisible(true);
		save.focus();
		name.selectAll();
	}
	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}