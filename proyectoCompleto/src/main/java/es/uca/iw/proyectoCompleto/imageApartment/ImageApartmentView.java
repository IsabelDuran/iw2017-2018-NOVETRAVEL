/**
 * 
 */
package es.uca.iw.proyectoCompleto.imageApartment;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.ProyectoCompletoApplication;
import es.uca.iw.proyectoCompleto.reports.Report;


@SpringView(name = ImageApartmentView.VIEW_NAME)
public class ImageApartmentView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "imageApartmentView";

	
	private Panel panel[];
	private ImageApartmentEditor editor;

	
	private final ImageApartmentService service;
	private ImageApartmentRepository repo;

	@Autowired
	public ImageApartmentView(ImageApartmentService service, ImageApartmentEditor editor) {
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
		listImageApartments(null);
		
	}

	private void listImageApartments(String filterText) {
		
		if (StringUtils.isEmpty(filterText)) {
			HorizontalLayout lista = new HorizontalLayout();
			addComponents(lista);
			lista.addComponent(new TextField("Hola"));
			//
			//
		} 
	}
	
	
	public void ultimo(ImageApartment pinchado) {
		MainScreen.setUltimoPinchado(pinchado);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
