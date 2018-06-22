/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.bookings.BookingView;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;

@UIScope
@SpringView(name = ApartmentListView.VIEW_NAME)
public class ApartmentListView extends VerticalLayout implements View {

	
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "apartmentListView";

	Panel p;
	
	@Autowired
	private  ApartmentService service;
	
	@PostConstruct
	void init() {
		// Hook logic to components

		// Initialize listing
		if(SecurityUtils.hasRole("ROLE_MANAGEMENT") || SecurityUtils.hasRole("ROLE_ADMIN") || SecurityUtils.hasRole("ROLE_USER"))
			listApartments(service.findAll());

	}
	
	public void setPanel(Panel p)
	{
		this.p=p;
	}

	public void listApartments(List<Apartment> aps) {
		this.removeAllComponents();
		if (!aps.isEmpty()) {
			
			GridLayout lista = new GridLayout(3, 3);
			addComponents(lista);
			
	
			for (Apartment i : aps) {
				VerticalLayout contenedor = new VerticalLayout();
				Panel panelPiso = crearPanelPiso(i);
				contenedor.addComponent(panelPiso);
//				contenedor.setSizeUndefined(); // Shrink to fit
				lista.addComponents(contenedor);
			}
		}
	}

	public void desplegarImagen(Layout layout, ImageApartment imageApartment) {
		final StreamResource.StreamSource streamSource = () -> {
			
			if (imageApartment.getFile() != null) {
				final byte[] byteArray = imageApartment.getFile();

				return new ByteArrayInputStream(byteArray);
			}
			return null;
		};
		final StreamResource resource = new StreamResource(streamSource, "");
		Image im = new Image("", resource);
		im.setWidth(100, Unit.PIXELS);
		im.setHeight(100, Unit.PIXELS);
		layout.addComponent(im);

	}

	
	private Button crearBotonReservar(Apartment ap) {

		Button reservarBtn = new Button("Reservar", e -> getUI().getNavigator().navigateTo(BookingView.VIEW_NAME + "/" + ap.getId())) ;
		reservarBtn.setVisible(SecurityUtils.hasRole("ROLE_USER"));

		return reservarBtn;
	}
	
	private Button crearBotonVermas(Apartment ap) {
		
		Button btnVermas = new Button("Ver mas...",event -> getUI().getNavigator().navigateTo(ApartmentView.VIEW_NAME+"/"+ap.getId()));
		
		return btnVermas;
	}
	
	private Panel crearPanelPiso(Apartment ap) {
		Panel panel = new Panel(ap.getName());
		panel.setHeight(320, Unit.PIXELS);
		panel.setWidth(310, Unit.PIXELS);
		
		VerticalLayout content = new VerticalLayout();
		if (ap.getImages().size() > 0) {
			desplegarImagen(content, ap.getImages().get(0));
		}
		content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		Label des = new Label(ap.getDescription());
		des.setWidth("250px");
		content.addComponent(des);
		content.addComponent(crearBotonVermas(ap));
		content.addComponent(crearBotonReservar(ap));
		panel.setContent(content);
		return panel;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
