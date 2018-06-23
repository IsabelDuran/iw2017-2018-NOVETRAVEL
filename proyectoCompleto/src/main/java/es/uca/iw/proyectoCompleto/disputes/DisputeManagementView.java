/**
 * 
 */
package es.uca.iw.proyectoCompleto.disputes;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.DefaultView;
import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.reports.Report;
import es.uca.iw.proyectoCompleto.users.User;

@SpringView(name = DisputeManagementView.VIEW_NAME)
public class DisputeManagementView extends VerticalLayout implements View
{

	private static final long serialVersionUID = 1L;


	public static final String VIEW_NAME = "disputeManagementView";

	private Grid<Dispute> grid;

	
	@Autowired
	private DisputeService service;
	
	@PostConstruct
	void init() {
		
		grid= new Grid<>(Dispute.class);
		addComponents(grid);
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(900, Unit.PIXELS);
		//grid.setColumns("id","opening_date", "closing_date","description", "apartment", "user");

		//filter.setPlaceholder("Filter by date");
		
		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		/*
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listReports(e.getValue()));
		*/
		// Connect selected Report to editor or hide if none is selected
		/*
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editReport(e.getValue());
		});
		 	*/

		
		//
		
		listDispute(null);
		
	}
	

	
	private void listDispute(String filterText) {
			/*
			if (StringUtils.isEmpty(filterText)) {
				grid.setItems(service.findAll());
			} else {
				grid.setItems(service.findByDateStartsWithIgnoreCase(filterText));
			}
			*/
			
			grid.setItems(service.findAll());
		}

}
