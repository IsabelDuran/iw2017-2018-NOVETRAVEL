/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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


@SpringView(name = ApartmentListView.VIEW_NAME)
public class ApartmentListView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "apartmentListView";

	private TextField filter;
	private Button addNewBtn;
	
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
			List<Apartment> aps=service.findAll();
			for(int i=0;i<aps.size();i++)
			{
				panel[i]=new Panel(aps.get(i).getName());
		
				addComponents(panel[i]);
			}
		} 
		
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
