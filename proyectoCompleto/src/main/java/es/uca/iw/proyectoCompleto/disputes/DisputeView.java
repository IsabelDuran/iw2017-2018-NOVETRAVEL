/**
 * 
 */
package es.uca.iw.proyectoCompleto.disputes;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.bookings.BookingView;
import es.uca.iw.proyectoCompleto.disputes.DisputeService;
import es.uca.iw.proyectoCompleto.users.UserService;


@SpringView(name = DisputeView.VIEW_NAME)
public class DisputeView extends VerticalLayout implements View
{

	private static final long serialVersionUID = 1L;


	public static final String VIEW_NAME = "disputeView";

	
	
	private Apartment apartment;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApartmentService service;

	@Autowired
	private DisputeService disputas;

	@PostConstruct
	void init() {
		
		// Hook logic to components
	
		// Listen changes made by the editor, refresh data from backend
		// Initialize listing
		
		
	}
	

	public void mostrarQueja() {
		
	
		VerticalLayout v = new VerticalLayout();
		

		Label titulo = new Label("Disputa");
		Label por = new Label("¿Por qué vas a denunciar este anuncio?");
		
		Label noc = new Label("No compartiremos esto con el anfitrión");
		v.addComponents(titulo,por,noc);
		
		
		List<String> disputeType = new ArrayList<>();
		disputeType.add("Es incorrecto o poco preciso");
		disputeType.add("No es un alojamiento real");
		disputeType.add("Es una estafa");
		disputeType.add("Es ofensivo");
		disputeType.add("Es por otro motivo");
		
		ComboBox<String> selectDisputeType =
			    new ComboBox<>("Selecciona una causa");
		selectDisputeType.setItems(disputeType);
		
		TextArea description = new TextArea("Descripción");
		Button confirmar = new Button("Confirmar", e -> ponerQueja()) ;
		
		
		v.addComponents(selectDisputeType,description,confirmar);
		
	

		
		
	}
	
	
	public void ponerQueja() {
		System.out.println("boa");
	}
	

	
	@Override
	public void enter(ViewChangeEvent event) {
		
		if(event.getParameters() != null){
	           // split at "/", add each part as a label
	           String[] msgs = event.getParameters().split("/");
	           long id=Long.valueOf(msgs[0]);
	           
	           apartment=service.loadApartmentById(id);
	           mostrarQueja();
	    
	           
	    }
		
	}

}
