/**
 * 
 */
package es.uca.iw.proyectoCompleto.disputes;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.apartments.ApartmentView;
import es.uca.iw.proyectoCompleto.security.MailService;

@SpringView(name = DisputeManagementView.VIEW_NAME)
public class DisputeManagementView extends VerticalLayout implements View
{

	private static final long serialVersionUID = 1L;


	public static final String VIEW_NAME = "disputeManagementView";

	private Grid<Dispute> grid;
	VerticalLayout editor;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private DisputeService service;
	
	@Autowired
	private ApartmentService apartamentos;
	
	@PostConstruct
	void init() {
		
		grid= new Grid<>();
		addComponents(grid);
		
		editor=new VerticalLayout();
		editor.setWidth(Page.getCurrent().getBrowserWindowWidth(),Unit.PIXELS);
		

		addComponent(editor);

		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setWidth(100, Unit.PERCENTAGE);
		
		grid.addColumn(Dispute::isOpen).setCaption("Abierta");
		grid.addColumn(Dispute::getOpening_date).setCaption("Fecha de apertura");
		grid.addColumn(Dispute::getUsername).setCaption("Usuario");
		grid.addColumn(Dispute::getApartmentID).setCaption("Apartamento");
		
		grid.addColumn(Dispute::getDescription).setCaption("Descripción");
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			evaluar(e.getValue());
		});
		 			
		listDispute(null);
		
	}
	
	
	private void evaluar(Dispute dispute) {
		
		editor.removeAllComponents();
		HorizontalLayout h=new HorizontalLayout();
		HorizontalLayout botones=new HorizontalLayout();
		Button anuncioCasa=new Button("Ir al anuncio de la casa",e -> getUI().getNavigator().navigateTo(ApartmentView.VIEW_NAME + "/" + dispute.getApartmentID()));
		Label descripcion=new Label(dispute.getDescription());
		
		Button cerrarAnuncio=new Button("Eliminar el apartamento denunciado",e->
		{

			String mensaje = "Estimado " + dispute.getUser().getFirstName() + " " + dispute.getUser().getFirstName()
					+ " nuestros gestores han considerado que su reserva realizada el " + dispute.getOpening_date()
					+ " incumple nuestras políticas y ha procedido a ser borrado.\n\n Gracias por ayudarnos a mejorar nuestros servicios";   
			
			mailService.enviarCorreo("Información sobre su reserva", mensaje, dispute.getUser().getEmail());
			apartamentos.delete(dispute.getApartment());
			Notification.show("Se ha borrardo el anuncio correctamente");
			getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);	
		});
		
		Button denunciaInvalida=new Button("Denuncia erronea",e->
		{
			
			String mensaje = "Estimado " + dispute.getUser().getFirstName() + " " + dispute.getUser().getFirstName()
					+ " nuestros gestores han considerado que su reserva realizada el " + dispute.getOpening_date()
					+ " no incumple ninguna de nuestras políticas.\n\n Gracias por ayudarnos a mejorar nuestros servicios";   
			
			mailService.enviarCorreo("Información sobre su denuncia", mensaje, dispute.getUser().getEmail());
			dispute.setOpen(false);
			service.save(dispute);
			
			listDispute(null);
			grid.clearSortOrder();
			 
		});
		denunciaInvalida.setVisible(dispute.isOpen() == true);
		cerrarAnuncio.addStyleName("danger");
		denunciaInvalida.addStyleName("primary");
		h.addComponents(descripcion,anuncioCasa);
		botones.addComponents(cerrarAnuncio,denunciaInvalida);
		editor.addComponents(h,botones);
		
	}
	
	


	private void listDispute(String filterText) {	
			grid.setItems(service.findAll());
			
		}

}
