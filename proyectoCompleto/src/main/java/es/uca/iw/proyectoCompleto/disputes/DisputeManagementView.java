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

@SpringView(name = DisputeManagementView.VIEW_NAME)
public class DisputeManagementView extends VerticalLayout implements View
{

	private static final long serialVersionUID = 1L;


	public static final String VIEW_NAME = "disputeManagementView";

	private Grid<Dispute> grid;
	VerticalLayout editor;

	
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
	
	
	private void evaluar(Dispute d) {
		
		editor.removeAllComponents();
		HorizontalLayout h=new HorizontalLayout();
		HorizontalLayout botones=new HorizontalLayout();
		Button anuncioCasa=new Button("Ir al anuncio de la casa",e -> getUI().getNavigator().navigateTo(ApartmentView.VIEW_NAME + "/" + d.getApartmentID()));
		Label descripcion=new Label(d.getDescription());
		
		Button cerrarAnuncio=new Button("Eliminar el apartamento denunciado",e->
		{
			apartamentos.delete(d.getApartment());
			Notification.show("Se ha borrardo el anuncio correctamente");
			getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);	
		});
		
		Button denunciaInvalida=new Button("Denuncia erronea",e->
		{
			
			d.setOpen(false);
			service.save(d);
			
			listDispute(null);
			grid.clearSortOrder();
			 
		});
		denunciaInvalida.setVisible(d.isOpen() == true);
		//cerrarAnuncio.addClickListener(clickEvent ->
	    //Notification.show("Se ha borrardo el anuncio correctamente"));
		cerrarAnuncio.addStyleName("danger");
		denunciaInvalida.addStyleName("primary");
		h.addComponents(descripcion,anuncioCasa);
		botones.addComponents(cerrarAnuncio,denunciaInvalida);
		editor.addComponents(h,botones);
		
	}
	
	


	private void listDispute(String filterText) {
			/*
			if (StringUtils.isEmpty(filterText)) {
				grid.setItems(service.findAll());
			} else {
				grid.setItems(service.findByDateStartsWithIgnoreCase(filterText));
			}
			*/	
			grid.setItems(service.findAll());
			
		}

}
