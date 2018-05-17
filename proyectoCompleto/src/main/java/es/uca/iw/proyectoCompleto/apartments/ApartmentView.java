/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.ProyectoCompletoApplication;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.reports.Report;


@SpringView(name = ApartmentView.VIEW_NAME)
public class ApartmentView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "apartmentView";

	
	private Panel panel[];
	private ApartmentEditor editor;

	
	private final ApartmentService service;
	private ApartmentRepository repo;

	@Autowired
	public ApartmentView(ApartmentService service, ApartmentEditor editor) {
		this.service = service;
		this.editor = editor;
		panel = new Panel[10];
	    
	}

	
	@PostConstruct
	void init() {
		
		// Hook logic to components
	
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		});

		// Initialize listing
		listApartments(null);
		
	}

	private void listApartments(String filterText) {
		
		if (StringUtils.isEmpty(filterText)) {
			HorizontalLayout lista = new HorizontalLayout();
			addComponents(lista);
			Apartment apartment = (Apartment) MainScreen.getUltimoPinchado();
			
			//PONER IMAGEN AQUI
			lista.addComponent(new Label(apartment.getName()));
			VerticalLayout abajo = new VerticalLayout();
			addComponents(abajo);
			abajo.addComponent(new Label(apartment.getDescription()));
			abajo.addComponent(new Label(Long.toString(apartment.getImages().get(0).getId())));
			HorizontalLayout imagenes=new HorizontalLayout();
			for(int i=0;i<apartment.getImages().size();i++)
			{
				desplegarImagen(imagenes, apartment.getImages().get(i));
			}
			abajo.addComponent(imagenes);
			abajo.addComponent(new Label("Precio por día: " + String.valueOf(apartment.getPrice_per_day() + "€")));
			//
			//
		} 
	}
	
	public void desplegarImagen(Layout l,ImageApartment A) {
		
		final String name="";
		//final ByteArrayOutputStream bas=new ByteArrayOutputStream(A.getFile());
	
		final StreamResource.StreamSource streamSource = () -> {
			
            if (A.getFile() != null) {
                final byte[] byteArray = A.getFile();

                return new ByteArrayInputStream(byteArray);
            }
            return null;
        };
        final StreamResource resource = new StreamResource(streamSource, name);
        Image im=new Image("name",resource);
        im.setWidth(100,Unit.PIXELS);
        im.setHeight(100,Unit.PIXELS);
        l.addComponent(im);
        
        
	}
	
	
	public void ultimo(Apartment pinchado) {
		MainScreen.setUltimoPinchado(pinchado);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
