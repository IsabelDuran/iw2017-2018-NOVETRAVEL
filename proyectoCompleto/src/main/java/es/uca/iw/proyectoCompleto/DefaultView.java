/**
 * 
 */
package es.uca.iw.proyectoCompleto;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.facturas.Factura;
import es.uca.iw.proyectoCompleto.facturas.FacturaService;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "";

	@Autowired
	private ApartmentService apartmentService;

	@Autowired
	private ApartmentListView apartmentListView;

	private Panel springViewDisplay;
	
	@Autowired 
	private FacturaService fs;

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

		Button filterButton = new Button("¡Filtar!");
		filterButton.setStyleName("box-padding");

		HorizontalLayout panelButtons = new HorizontalLayout();
		VerticalLayout filters = new VerticalLayout();

		Button closeButton = new Button("Cerrar");
		closeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

		Button searchWithFilter = new Button("¡Buscar!");
		searchWithFilter.setStyleName("box-padding");

		panelButtons.addComponents(searchWithFilter, closeButton);
		Label sliderLabel = new Label("Precio máximo: ");
		Slider priceSlider = new Slider(0, 1000);
		priceSlider.setWidth(100, Unit.PERCENTAGE);

		Label entryDateLabel = new Label("Fecha entrada: ");

		Label departureDateLabel = new Label("Fecha entrada: ");

		DateField entryDateField = new DateField();
		DateField departureDateField = new DateField();

		filters.addComponents(sliderLabel, priceSlider, entryDateLabel, entryDateField, departureDateLabel,
				departureDateField, panelButtons);

		Panel filterPanel = new Panel("Filtrar por precio y disponibilidad");
		filterPanel.setContent(filters);
		filterPanel.setWidth(300, Unit.PIXELS);
		filterPanel.setVisible(false);

		addComponents(searchbar, searchbutton, filterButton, filterPanel);

		Label title_ = new Label("Pisos destacados: ");
		title_.setStyleName("title-text");
		addComponent(title_);

		searchbutton.addClickListener(
				e -> apartmentListView.listApartments(apartmentService.loadApartmentByLocation(searchbar.getValue())));

		filterButton.addClickListener(e -> {
			filterPanel.setVisible(true);
		});

		closeButton.addClickListener(e -> {
			filterPanel.setVisible(false);
		});

		searchWithFilter.addClickListener(e -> {
			String location = searchbar.getValue();
			LocalDate entryDate = entryDateField.getValue();
			LocalDate departureDate = departureDateField.getValue();
			Double price = priceSlider.getValue();
			apartmentListView.listApartments(
					apartmentService.loadApartmentByLocationDateAndPrice(location, entryDate, departureDate, price));
		});

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		addComponent(springViewDisplay);

		addComponent(springViewDisplay);
		apartmentListView.setPanel(springViewDisplay);
		springViewDisplay.setContent(apartmentListView);
		this.setComponentAlignment(filterPanel, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		apartmentListView.listApartments(apartmentService.findAll());

	}

}
