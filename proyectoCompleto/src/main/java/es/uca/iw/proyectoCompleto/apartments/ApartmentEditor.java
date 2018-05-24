package es.uca.iw.proyectoCompleto.apartments;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
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

import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringComponent
@UIScope
public class ApartmentEditor extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ApartmentService service;
	private Apartment apartamento;
	private Apartment_OfferedServices offered_Services;
	
	/**
	 * The currently edited apartment
	 */
	

	private Binder<Apartment> binder = new Binder<>(Apartment.class);
	private Binder<Apartment_OfferedServices> services_binder = new Binder<>(Apartment_OfferedServices.class);

	
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
	
	Label description_label = new Label("Descripción del apartamento:");
	Label service_label = new Label("Selecciona los servicios que posee tu apartamento:");


	/* Action buttons */
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	@Autowired
	public ApartmentEditor(ApartmentService service) {
		this.service = service;
		
		description_label.setStyleName(ValoTheme.LABEL_H2);
		service_label.setStyleName(ValoTheme.LABEL_H2);
		addComponents(description_label, name, description, price_per_day, apartment_type, max_hosts, number_beds, number_rooms, number_bathrooms,
				squared_meters, service_label, crib, parking, wifi, own_bathroom, own_kitchen, pets_allowed, kids_allowed, smoking_allowed, actions);

		// bind using naming convention
		binder.forField(name).bind(Apartment::getName,Apartment::setName);
		binder.forField(description).bind(Apartment::getDescription,Apartment::setDescription);
		binder.forField(price_per_day).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment::getPrice_per_day,Apartment::setPrice_per_day);
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

//		services_binder.forField(max_hosts).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment_OfferedServices::getMax_hosts, 
//																														Apartment_OfferedServices::setMax_hosts);
//		
//		services_binder.forField(number_beds).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment_OfferedServices::getNumber_beds, 
//																													Apartment_OfferedServices::setNumber_beds);
//		
//		services_binder.forField(number_rooms).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment_OfferedServices::getRooms, 
//																														Apartment_OfferedServices::setRooms);
//		
//		services_binder.forField(number_bathrooms).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment_OfferedServices::getNumber_bathrooms, 
//																													Apartment_OfferedServices::setNumber_bathrooms);
//		
//		services_binder.forField(squared_meters).withConverter(new StringToIntegerConverter("Introducir un número")).bind(Apartment_OfferedServices::getSquared_meters, 
//																						Apartment_OfferedServices::setSquared_meters);
//		
//		
//		services_binder.forField(crib).bind(Apartment_OfferedServices::isCrib, Apartment_OfferedServices::setCrib);
//		services_binder.forField(parking).bind(Apartment_OfferedServices::isParking, Apartment_OfferedServices::setParking);
//		services_binder.forField(wifi).bind(Apartment_OfferedServices::isWifi, Apartment_OfferedServices::setWifi);
//		services_binder.forField(own_bathroom).bind(Apartment_OfferedServices::isOwn_bathroom, Apartment_OfferedServices::setOwn_bathroom);
//		services_binder.forField(own_kitchen).bind(Apartment_OfferedServices::isKitchen, Apartment_OfferedServices::setKitchen);
//		services_binder.forField(pets_allowed).bind(Apartment_OfferedServices::isPets_allowed, Apartment_OfferedServices::setPets_allowed);
//		services_binder.forField(kids_allowed).bind(Apartment_OfferedServices::isKids_allowed, Apartment_OfferedServices::setKids_allowed);
//		services_binder.forField(smoking_allowed).bind(Apartment_OfferedServices::isSmoking_allowed, Apartment_OfferedServices::setSmoking_allowed);
//	
		
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(apartamento));
		delete.addClickListener(e -> service.delete(apartamento));
		cancel.addClickListener(e -> editApartment(apartamento));
		setVisible(false);
		
		// Solo borra el admin
		delete.setEnabled(SecurityUtils.hasRole("ROLE_ADMIN"));
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editApartment(Apartment c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			apartamento = service.findOne(c.getId());
		}
		else {
			apartamento = c;
		}
		cancel.setVisible(persisted);

		// Bind apartment properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(apartamento);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}