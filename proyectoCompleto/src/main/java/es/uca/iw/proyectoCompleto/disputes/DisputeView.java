/**
 * 
 */
package es.uca.iw.proyectoCompleto.disputes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.DefaultView;
import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.users.User;

@SpringView(name = DisputeView.VIEW_NAME)
public class DisputeView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "disputeView";

	private Apartment apartment;

	@Autowired
	private ApartmentService service;

	@Autowired
	private DisputeService disputeService;
	
	private Dispute dispute;

	@PostConstruct
	void init() {

		Binder<Dispute> binder = new Binder<>();
		VerticalLayout v = new VerticalLayout();
		this.addComponent(v);

		Label titulo = new Label("Disputa");
		Label por = new Label("¿Por qué vas a denunciar este anuncio?");

		Label noc = new Label("No compartiremos esto con el anfitrión");
		v.addComponents(titulo, por, noc);

		List<String> disputeType = new ArrayList<>();
		disputeType.add("Es incorrecto o poco preciso");
		disputeType.add("No es un alojamiento real");
		disputeType.add("Es una estafa");
		disputeType.add("Es ofensivo");
		disputeType.add("Es por otro motivo");

		ComboBox<String> selectDisputeType = new ComboBox<>("Selecciona una causa");
		selectDisputeType.setWidth(300,Unit.PIXELS);
		selectDisputeType.setItems(disputeType);
		selectDisputeType.setValue("Es por otro motivo");
		selectDisputeType.setEmptySelectionAllowed(false);

		TextArea description = new TextArea("Descripción");
		description.setWidth(300,Unit.PIXELS);
		
		binder.forField(selectDisputeType).asRequired("¡Debe introducir un motivo!").bind(Dispute::getReason, Dispute::setReason);
		binder.forField(description).asRequired("¡Debe introducir una descripción!").bind(Dispute::getDescription, Dispute::setDescription);
		
		Button confirmar = new Button("Confirmar",
				e -> {
					try
					{
						binder.writeBean(dispute);
						LocalDate systemDate = LocalDate.now();
						User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						dispute.setApartment(apartment);
						dispute.setUser(user);
						dispute.setOpening_date(systemDate);
						disputeService.save(dispute);
						Notification.show("Se ha guardado correctamente");
						getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME);
						
					}catch(ValidationException ex)
					{
						ValidationResult validationResult = ex.getValidationErrors().iterator().next();
						Notification.show(validationResult.getErrorMessage());
					}
					
				});

		v.addComponents(selectDisputeType, description, confirmar);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		Apartment apartamentoActual = (Apartment) VaadinSession.getCurrent().getAttribute("apartamentoActual");
		this.apartment = apartamentoActual;
		this.dispute = new Dispute();

	}

}
