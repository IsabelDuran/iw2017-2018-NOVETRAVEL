/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.ProyectoCompletoApplication;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.reports.Report;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.bookings.*;
import es.uca.iw.proyectoCompleto.users.*;

///////////PARA SABER EL USUARIO AUTENTICADO//////////////

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/////////////////////////////////////////////////////////

@Controller
@SpringView(name = ApartmentListView.VIEW_NAME)
public class ApartmentListView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "apartmentListView";

	// private TextField filter;
	// private Button addNewBtn;

	private Apartment ap;

	private Authentication auth;

	private BookingEditor editor;

	private UserRepository repository;

	private Panel panel[];
	Button[] vermas;
	Button[] reservar;

	private final ApartmentService service;

	@Autowired
	public ApartmentListView(ApartmentService service, BookingEditor editor) {
		this.editor = editor;
		this.service = service;
		panel = new Panel[10];

	}

	@PostConstruct
	void init() {
		// Hook logic to components

		// Initialize listing
		listApartments(service.findAll());

	}

	@RequestMapping(method = RequestMethod.GET)
	public void listApartments(List<Apartment> aps) {
		if (!aps.isEmpty()) {
			HorizontalLayout lista = new HorizontalLayout();
			addComponents(lista);
			vermas = new Button[aps.size()];
			reservar = new Button[aps.size()]; // Tantos botones como apartamentos haya
	
			for (int contador = 0; contador < aps.size(); contador++) {
				VerticalLayout contenedor = new VerticalLayout();
				ap = aps.get(contador);
				vermas[contador] = new Button("Ver mas...");
				reservar[contador] = new Button("Reservar");
				reservar[contador].setVisible(SecurityUtils.hasRole("ROLE_USER"));

				panel[contador] = new Panel(aps.get(contador).getName());
				panel[contador].setHeight(300, Unit.PIXELS);
				panel[contador].setWidth(300, Unit.PIXELS);
				VerticalLayout content = new VerticalLayout();
				if (aps.get(contador).getImages().size() > 0) {
					desplegarImagen(content, aps.get(contador).getImages().get(0));
				}

				content.addComponent(new Label(aps.get(contador).getDescription()));
				content.addComponent(vermas[contador]);
				content.addComponent(reservar[contador]);
				content.addComponent(editor);

				vermas[contador].addClickListener(new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						int contador = 0;
						for (Button b : vermas) {
							if (event.getButton() == b) {
								ultimo(aps.get(contador));
							}
							contador++;
						}
					}
				});

				reservar[contador].addClickListener(new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event2) {
						int contador = 0;
						for (Button b : reservar) {
							if (event2.getButton() == b) {
								Apartment a = aps.get(contador);
								User currentUser = (User) SecurityContextHolder.getContext().getAuthentication()
										.getPrincipal();
								LocalDate f1 = LocalDate.now();
								LocalDate f2 = LocalDate.now();

								editor.editBooking(new Booking(f1, f2, 0.0, a, currentUser));

							}
							contador++;
						}
					}
				});

				content.setSizeUndefined(); // Shrink to fit
				contenedor.addComponent(content);
				contenedor.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
				panel[contador].setContent(contenedor);
				lista.addComponents(panel[contador]);
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
		MainScreen.setUltimoPinchado(pinchado);
		getUI().getNavigator().navigateTo("apartmentView");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
