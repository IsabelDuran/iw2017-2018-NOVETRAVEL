/**
 * 
 */
package es.uca.iw.proyectoCompleto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;

/**
 * @author ruizrube
 *
 */
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";

	@Autowired
	private ApartmentService apartmentService;

	@Autowired
	private ApartmentListView apartmentListView;
	
	private Panel springViewDisplay;
	
	@PostConstruct
	void init() {

		this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		Label searchtitle_ = new Label("Buscar: ");
		searchtitle_.setStyleName("title-text");
		addComponent(searchtitle_);

		TextField searchbar = new TextField();
		searchbar.setWidth("90%");
		searchbar.setStyleName("box-padding");

		Button searchbutton = new Button("¡Busca mi apartamento!");
		searchbutton.setStyleName("box-padding");
		addComponents(searchbar, searchbutton);

		Label title_ = new Label("Pisos destacados: ");
		title_.setStyleName("title-text");
		addComponent(title_);
		
		searchbutton.addClickListener(
				e -> apartmentListView.listApartments(apartmentService.loadApartmentByLocation(searchbar.getValue())));
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		addComponent(springViewDisplay);
		setExpandRatio(springViewDisplay, 1.0f);

		// navbar_.setDisplay(springViewDisplay);
		addComponent(springViewDisplay);
		apartmentListView.setPanel(springViewDisplay);
		springViewDisplay.setContent(apartmentListView);
		apartmentListView.listApartments(apartmentService.findAll());

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
