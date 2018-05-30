/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.bookings.BookingEditor;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentService;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.User;

@Controller
@SpringView(name = ApartmentListView.VIEW_NAME)
public class ApartmentListView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "apartmentListView";



	private BookingEditor editor;

	Panel p;

	private final ApartmentService service;
	private final ImageApartmentService image;

	@Autowired
	public ApartmentListView(ApartmentService service, BookingEditor editor,ImageApartmentService imap) {
		this.editor = editor;
		this.service = service;
		this.image=imap;
	}

	@PostConstruct
	void init() {
		// Hook logic to components

		// Initialize listing
		listApartments(service.findAll());

	}
	
	public void setPanel(Panel p)
	{
		this.p=p;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void listApartments(List<Apartment> aps) {
		if (!aps.isEmpty()) {
			HorizontalLayout lista = new HorizontalLayout();
			addComponents(lista);
	
			for (Apartment i : aps) {
				VerticalLayout contenedor = new VerticalLayout();
				Panel panelPiso = crearPanelPiso(i);
				
				contenedor.addComponent(editor);
				contenedor.addComponent(panelPiso);
				contenedor.setSizeUndefined(); // Shrink to fit
				lista.addComponents(contenedor);
			}
		}
	}

	public void desplegarImagen(Layout l, ImageApartment A) {

		final String name = "";
		// final ByteArrayOutputStream bas=new ByteArrayOutputStream(A.getFile());

		final StreamResource.StreamSource streamSource = () -> {

			if (A.getFile() != null) {
				final byte[] byteArray = A.getFile();

				return new ByteArrayInputStream(byteArray);
			}
			return null;
		};
		final StreamResource resource = new StreamResource(streamSource, name);
		Image im = new Image("", resource);
		im.setWidth(100, Unit.PIXELS);
		im.setHeight(100, Unit.PIXELS);
		l.addComponent(im);

	}

	public void ultimo(Apartment pinchado) {
		if(getUI()==null)
		{
			MainScreen.setUltimoPinchado(pinchado);
			getUI().getNavigator().navigateTo("apartmentView");
		}
		else {
			ApartmentView ap=new ApartmentView(service,image);
			this.p.setContent((Component)ap);
			ap.setApartamento(pinchado);
			ap.mostrarApartamento();
		}

	}

	private Button crearBotonReservar(Apartment ap) {

		Button reservarBtn = new Button("Reservar");
		reservarBtn.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		reservarBtn.addClickListener(e -> {
			User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LocalDate f1 = LocalDate.now();
			LocalDate f2 = LocalDate.now();

			editor.editBooking(new Booking(f1, f2, 0.0, ap, currentUser));
		});
		return reservarBtn;
	}
	
	private Button crearBotonVermas(Apartment ap) {
		Button btnVermas = new Button("Ver mas...");
		btnVermas.addClickListener(e -> {
			ultimo(ap);
		});
		
		return btnVermas;
	}
	
	private Panel crearPanelPiso(Apartment ap) {
		Panel panel = new Panel(ap.getName());
		panel.setHeight(300, Unit.PIXELS);
		panel.setWidth(300, Unit.PIXELS);
		
		VerticalLayout content = new VerticalLayout();
		if (ap.getImages().size() > 0) {
			desplegarImagen(content, ap.getImages().get(0));
		}
		content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		content.addComponent(new Label(ap.getDescription()));
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
