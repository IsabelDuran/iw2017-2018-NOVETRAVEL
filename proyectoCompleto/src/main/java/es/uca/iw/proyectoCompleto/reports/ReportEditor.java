package es.uca.iw.proyectoCompleto.reports;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
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
public class ReportEditor extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ReportService service;
	private Report report;
	
	/**
	 * The currently edited apartment
	 */
	

	private Binder<Report> binder = new Binder<>(Report.class);

	
	/* Fields to edit properties in Report entity */
	TextField title = new TextField("Titulo");
	TextField reason = new TextField("Motivo");
	TextField description = new TextField("Descripcion");

	/* Action buttons */
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete");

	/* Layout for buttons */
	CssLayout actions = new CssLayout(save, cancel, delete);


	@Autowired
	public ReportEditor(ReportService service) {
		this.service = service;

		addComponents(title, reason, description ,actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> service.save(report));
		delete.addClickListener(e -> service.delete(report));
		cancel.addClickListener(e -> editReport(report));
		setVisible(false);
		
		// Solo borra el admin
		delete.setEnabled(SecurityUtils.hasRole("ADMIN"));
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editReport(Report c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			report = service.findOne(c.getId());
		}
		else {
			report = c;
		}
		cancel.setVisible(persisted);

		// Bind apartment properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(report);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		title.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
