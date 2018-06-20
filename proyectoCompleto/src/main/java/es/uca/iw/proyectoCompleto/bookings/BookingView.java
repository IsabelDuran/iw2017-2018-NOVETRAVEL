package es.uca.iw.proyectoCompleto.bookings;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringView(name = BookingView.VIEW_NAME)
public class BookingView extends VerticalLayout implements View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "bookingView";
	
	@Autowired
	private ApartmentService aps;
	
	@Autowired
	private UserService userService;
	
	private Apartment apartment;
	
	@Autowired
	private BookingEditor editor;
	
	@PostConstruct
	void init()
	{
		
	}
	
	public void mostrarApartamento() {
		
		User currentUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		LocalDate f1 = LocalDate.now();
		LocalDate f2 = LocalDate.now();
		
		HorizontalLayout h = new HorizontalLayout();
		HorizontalLayout imagenes = new HorizontalLayout();
		VerticalLayout v = new VerticalLayout();
		
		addComponents(h);
		
		h.addComponent(v);
		h.addComponent(editor);
		editor.editBooking(new Booking(f1, f2, apartment.getPrice_per_day(), false, apartment, currentUser));
		
		v.addComponent(new Label(apartment.getName()));
		Label description = new Label(apartment.getDescription());
		description.setWidth("300px");
		v.addComponent(description);
		desplegarImagenes(imagenes); 
		v.addComponent(imagenes);
		
		v.addComponent(new Label("Precio por día: " + String.valueOf(apartment.getPrice_per_day() + "€")));
		
	
		 	
    }
	
	public void desplegarImagenes(Layout contenedor) {
		
		contenedor.removeAllComponents();
		
		for(int i=0;i<apartment.getImages().size();i++)
		{
			desplegarImagen(contenedor, apartment.getImages().get(i));
		}

	
	}
	
	public void desplegarImagen(Layout l,ImageApartment A) {

		final StreamResource.StreamSource streamSource = () -> {
			
            if (A.getFile() != null) {
                final byte[] byteArray = A.getFile();

                return new ByteArrayInputStream(byteArray);
            }
            return null;
        };
        final StreamResource resource = new StreamResource(streamSource, "");
        Image im=new Image("name",resource);
        im.setWidth(100,Unit.PIXELS);
        im.setHeight(100,Unit.PIXELS);
        l.addComponent(im);
        
        
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null){
	           // split at "/", add each part as a label
	           String[] msgs = event.getParameters().split("/");
	           long id=Long.valueOf(msgs[0]);
	           
	           apartment= aps.loadApartmentById(id);
	           mostrarApartamento();
	           
	    }
		
	}
	
	
}
