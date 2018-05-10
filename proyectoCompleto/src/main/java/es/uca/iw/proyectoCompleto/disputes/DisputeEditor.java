package es.uca.iw.proyectoCompleto.disputes;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.reports.Report;
import es.uca.iw.proyectoCompleto.reports.ReportEditor.ChangeHandler;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@SpringComponent
@UIScope
public class DisputeEditor extends VerticalLayout
{
	private static final long serialVersionUID = 1L;
	private DisputeService service;
	private Dispute dispute_;
	
	private Binder<Dispute> binder = new Binder<>(Dispute.class);
	
	TextField date =  new TextField("Fecha");
	TextField dispute_description =  new TextField("Motivo de la disputa");
	
	Button save = new Button("Save");
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete");
			
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	@Autowired
	public DisputeEditor(DisputeService service)
	{
		this.service = service;
		
		addComponents(date, dispute_description, actions);
		
		binder.forField(date).bind(Dispute::getOpening_date, Dispute::setOpening_date);
		binder.setReadOnly(true);
		binder.forField(dispute_description).bind(Dispute::getDescription, Dispute::setDescription);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		save.addClickListener(e -> service.save(dispute_));
		delete.addClickListener(e -> service.delete(dispute_));
		cancel.addClickListener(e -> editDispute(dispute_));
		setVisible(false);
		
		delete.setEnabled(SecurityUtils.hasRole("ADMIN"));

	}
	
	public interface ChangeHandler {

		void onChange();
	}
	
	public final void editDispute(Dispute dispute)
	{
		if(dispute ==  null)
		{
			setVisible(false);
			return;
		}
		final boolean persisted =  dispute.getId() != null;
		if(persisted)
		{
			dispute_ = service.findOne(dispute.getId());
		}
		else
			dispute_ = dispute;
		cancel.setVisible(persisted);
		

		// Bind report properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(dispute_);

		setVisible(true);
		

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		dispute_description.selectAll();
		
	}
	
	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
	
}


