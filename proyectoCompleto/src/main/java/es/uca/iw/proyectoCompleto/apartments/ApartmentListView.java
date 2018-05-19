/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

import java.io.ByteArrayInputStream;
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


@SpringView(name = ApartmentListView.VIEW_NAME)
public class ApartmentListView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "apartmentListView";

	private TextField filter;
	private Button addNewBtn;
	
	private Apartment ap;
	
	
	private Panel panel[];
	private ApartmentEditor editor;

	
	private final ApartmentService service;

	@Autowired
	public ApartmentListView(ApartmentService service, ApartmentEditor editor) {
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
			List<Apartment> aps=service.findAll();
			Button[] vermas=new Button[aps.size()];
			for(int i = 0;i<aps.size();i++)
			{
				VerticalLayout contenedor = new VerticalLayout();
				ap = aps.get(i);
				vermas[i] = new Button("Ver mas...");
				panel[i]=new Panel(aps.get(i).getName());
				panel[i].setHeight(300,Unit.PIXELS);
				panel[i].setWidth(300,Unit.PIXELS);
				VerticalLayout content = new VerticalLayout();
				if(aps.get(i).getImages().size()>0)
				{
					desplegarImagen(content, aps.get(i).getImages().get(0));
				}
				
				content.addComponent(new Label(aps.get(i).getDescription()));
				content.addComponent(vermas[i]);
				vermas[i].addClickListener(e->ultimo(ap));
				vermas[i].addClickListener(e->getUI().getNavigator().navigateTo("apartmentView"));
				content.setSizeUndefined(); // Shrink to fit
				contenedor.addComponent(content);
				contenedor.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
				panel[i].setContent(contenedor);
				lista.addComponents(panel[i]);
			}
			
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
        Image im=new Image("",resource);
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
