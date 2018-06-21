package es.uca.iw.proyectoCompleto.imageApartment;


import org.springframework.beans.factory.annotation.Autowired;

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
public class ImageApartmentEditor extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The currently edited image
	 */
	

	//private Binder<ImageApartment> binder = new Binder<>(ImageApartment.class);

	
	/* Fields to edit properties in ImageApartment entity */
	TextField title = new TextField("Titulo");
	TextField date = new TextField("Fecha");
	TextField reasons = new TextField("Motivo");
	TextField image_description = new TextField("Descripcion");
	
	
	/* Action buttons */
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	@Autowired
	public ImageApartmentEditor(ImageApartmentService service) {
		addComponents(title, date, reasons, image_description ,actions);
		

		// bind using naming convention
		/*
		binder.forField(date).bind(ImageApartment::getDate, ImageApartment::setDate);
		binder.setReadOnly(true);
		binder.forField(title).bind(ImageApartment::getTitle,ImageApartment::setTitle);
		binder.forField(reasons).bind(ImageApartment::getReasons,ImageApartment::setReasons);
		binder.forField(image_description).bind(ImageApartment::getImageApartment_description, ImageApartment::setImageApartment_description);
		*/
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		/*
		save.addClickListener(e -> service.save(image));
		delete.addClickListener(e -> service.delete(image));
		*/
		//cancel.addClickListener(e -> editImageApartment(image));
		setVisible(false);
		
		// Solo borra el admin
		delete.setEnabled(SecurityUtils.hasRole("ADMIN"));
	}

	public interface ChangeHandler {

		void onChange();
	}
/*
	public final void editImageApartment(ImageApartment c) {
		if (c == null) {
			setVisible(false);
			return;
		}

		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			image = service.findOne(c.getId());
		}
		else {
			image = c;
		}
		cancel.setVisible(persisted);
	

		// Bind image properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(image);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		title.selectAll();
	}
*/
	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
