/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

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


@SpringView(name = ApartmentView.VIEW_NAME)
public class ApartmentView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "apartmentView";

	
	private Panel panel[];
	private ApartmentEditor editor;

	
	private final ApartmentService service;

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
			Long id=MainScreen.getUltimoPinchado();
			
			lista.addComponent(new Label(new Long(id).toString()));
			
		} 
	}
	
	
	public void ultimo(Long pinchado) {
		MainScreen.setUltimoPinchado(pinchado);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
