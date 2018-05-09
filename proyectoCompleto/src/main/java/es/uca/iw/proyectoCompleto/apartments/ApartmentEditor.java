package es.uca.iw.proyectoCompleto.apartments;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
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
	
	/**
	 * The currently edited apartment
	 */
	

	private Binder<Apartment> binder = new Binder<>(Apartment.class);

	
	/* Fields to edit properties in Apartment entity */
	TextField name = new TextField("Name");
	TextField description = new TextField("Description");
	TextField price_per_day = new TextField("price_per_day");
	TextField book = new TextField("book");
	TextField apartment_type = new TextField("apartment_type");

	/* Action buttons */
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	@Autowired
	public ApartmentEditor(ApartmentService service) {
		this.service = service;
		
		addComponents(name, description, price_per_day, book, apartment_type,actions);

		// bind using naming convention
		binder.forField(name).bind(Apartment::getName,Apartment::setName);
		binder.forField(description).bind(Apartment::getDescription,Apartment::setDescription);
		binder.forField(price_per_day).withConverter(new StringToIntegerConverter("Introducir un numero")).bind(Apartment::getPrice_per_day,Apartment::setPrice_per_day);
		binder.forField(book).withConverter(new StringToBooleanConverter("Introducir un numero")).bind(Apartment::isBook,Apartment::setBook);
		binder.forField(apartment_type).bind(Apartment::getApartment_type,Apartment::setApartment_type);
		

		
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
		delete.setEnabled(SecurityUtils.hasRole("ADMIN"));
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