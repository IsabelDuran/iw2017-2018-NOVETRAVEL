/**
 * 
 */
package es.uca.iw.proyectoCompleto;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
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

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentListView;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;

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

	private Pagination pagination;
	private PaginationResource paginationResource;

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

		this.paginationResource = PaginationResource.newBuilder().setTotal(1).setPage(1).setLimit(9).build();

		pagination = new Pagination(paginationResource);
		pagination.setItemsPerPage(3, 6, 9, 30, 60, 90);
		addComponent(pagination);

		pagination.addPageChangeListener(new PaginationChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void changed(PaginationResource event) {
				Pageable pageable = new PageRequest(event.pageIndex(), event.limit());
				String location = searchbar.getValue();
				LocalDate entryDate = entryDateField.getValue();
				LocalDate departureDate = departureDateField.getValue();
				Double price = priceSlider.getValue();

				Page<Apartment> apartments = null;
				if (filterPanel.isVisible())
					apartments = apartmentService.loadApartmentByLocationDateAndPrice(location, entryDate, departureDate, price,
							pageable);
				else
					apartments = apartmentService.loadApartmentByLocation(location, pageable);

				pagination.setTotalCount(apartments.getTotalElements());
				apartmentListView.listApartments(apartments.getContent());
			}
		});

		addComponents(searchbar, searchbutton, filterButton, filterPanel, pagination);

		Label title_ = new Label("Pisos destacados: ");
		title_.setStyleName("title-text");
		addComponent(title_);

		searchbutton.addClickListener(e -> {
			String location = searchbar.getValue();
			Pageable pageable = new PageRequest(this.paginationResource.pageIndex(), this.paginationResource.limit());
	    	Page<Apartment> apartments = apartmentService.loadApartmentByLocation(location, pageable);
			pagination.setTotalCount(apartments.getTotalElements());
			apartmentListView.listApartments(apartments.getContent());
		});

		filterButton.addClickListener(e -> {
			filterPanel.setVisible(true);
			searchbutton.setVisible(false);
		});

		closeButton.addClickListener(e -> {
			filterPanel.setVisible(false);
			searchbutton.setVisible(true);
		});

		searchWithFilter.addClickListener(e -> {
			String location = searchbar.getValue();
			LocalDate entryDate = entryDateField.getValue();
			LocalDate departureDate = departureDateField.getValue();
			Double price = priceSlider.getValue();

			Pageable pageable = new PageRequest(this.paginationResource.pageIndex(), this.paginationResource.limit());
			Page<Apartment> apartments = apartmentService.loadApartmentByLocationDateAndPrice(location, entryDate,
					departureDate, price, pageable);
			pagination.setTotalCount(apartments.getTotalElements());
			apartmentListView.listApartments(apartments.getContent());
		});

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		addComponent(springViewDisplay);
		apartmentListView.setPanel(springViewDisplay);
		springViewDisplay.setContent(apartmentListView);
		this.setComponentAlignment(filterPanel, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		updateApartments();
	}

	private void updateApartments() {
		Pageable pageable = new PageRequest(this.paginationResource.pageIndex(), this.paginationResource.limit());
		Page<Apartment> apartments = apartmentService.findAllWithPagination(pageable);
		pagination.setTotalCount(apartments.getTotalElements());
		apartmentListView.listApartments(apartments.getContent());
	}

}
