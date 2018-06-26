/**
 * 
 */
package es.uca.iw.proyectoCompleto.disputes;

import java.util.ArrayList;
import java.util.List;

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
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.apartments.ApartmentView;
import es.uca.iw.proyectoCompleto.security.MailService;

@SpringView(name = DisputeManagementView.VIEW_NAME)
public class DisputeManagementView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "disputeManagementView";

	private Grid<Dispute> grid;
	VerticalLayout editor;

	@Autowired
	private MailService mailService;

	@Autowired
	private DisputeService disputeService;

	@Autowired
	private ApartmentService apartmentService;

	private PaginationResource paginationResource;
	private Pagination pagination;

	private ComboBox<String> selectDisputeType;

	@PostConstruct
	void init() {

		grid = new Grid<>();

		List<String> disputeType = new ArrayList<>();
		disputeType.add("Mostrar todos");
		disputeType.add("Es incorrecto o poco preciso");
		disputeType.add("No es un alojamiento real");
		disputeType.add("Es una estafa");
		disputeType.add("Es ofensivo");
		disputeType.add("Es por otro motivo");

		selectDisputeType = new ComboBox<>("Selecciona una causa");
		selectDisputeType.setItems(disputeType);
		selectDisputeType.setValue("Mostrar todos");
		selectDisputeType.setEmptySelectionAllowed(false);
		addComponent(selectDisputeType);
		selectDisputeType.addSelectionListener(e -> updateDisputesGrid());

		final int page = 1;
		final int limit = 10;

		this.paginationResource = PaginationResource.newBuilder().setTotal(1).setPage(page).setLimit(limit).build();
		pagination = new Pagination(paginationResource);
		pagination.setItemsPerPage(10, 20, 50, 100);

		pagination.addPageChangeListener(new PaginationChangeListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void changed(PaginationResource event) {
				updateDisputesGrid();
			}

		});

		editor = new VerticalLayout();
		editor.setWidth(100, Unit.PERCENTAGE);

		addComponents(pagination, editor, grid);

		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setWidth(100, Unit.PERCENTAGE);

		grid.addColumn(Dispute::isOpen).setCaption("Abierta");
		grid.addColumn(Dispute::getOpening_date).setCaption("Fecha de apertura");
		grid.addColumn(Dispute::getUsername).setCaption("Usuario");
		grid.addColumn(Dispute::getApartmentID).setCaption("Apartamento");
		grid.addColumn(Dispute::getReason).setCaption("Motivo");
		grid.addColumn(Dispute::getDescription).setCaption("Descripción");

		grid.asSingleSelect().addValueChangeListener(e -> {
			if (e.getValue() != null)
				evaluar(e.getValue());
		});

	}

	private void evaluar(Dispute dispute) {

		editor.removeAllComponents();
		HorizontalLayout h = new HorizontalLayout();
		HorizontalLayout botones = new HorizontalLayout();
		
		Button anuncioCasa = new Button("Ir al anuncio de la casa", e -> {
			VaadinSession.getCurrent().setAttribute("apartamentoActual", dispute.getApartment());
			getUI().getNavigator().navigateTo(ApartmentView.VIEW_NAME);
		});
		
		Label descripcion = new Label(dispute.getDescription());

		Button cerrarAnuncio = new Button("Eliminar el apartamento denunciado", e -> {

			String mensaje = "Estimado " + dispute.getUser().getFirstName() + " " + dispute.getUser().getFirstName()
					+ " nuestros gestores han considerado que su reserva realizada el " + dispute.getOpening_date()
					+ " incumple nuestras políticas y ha procedido a ser borrado.\n\n Gracias por ayudarnos a mejorar nuestros servicios";

			mailService.enviarCorreo("Información sobre su denuncia", mensaje, dispute.getUser().getEmail());
			apartmentService.delete(dispute.getApartment());
			Notification.show("Se ha borrardo el anuncio correctamente");
			getUI().getNavigator().navigateTo(MainScreen.VIEW_NAME);
		});

		Button denunciaInvalida = new Button("Denuncia erronea", e -> {

			String mensaje = "Estimado " + dispute.getUser().getFirstName() + " " + dispute.getUser().getFirstName()
					+ " nuestros gestores han considerado que su reserva realizada el " + dispute.getOpening_date()
					+ " no incumple ninguna de nuestras políticas.\n\n Gracias por ayudarnos a mejorar nuestros servicios";

			mailService.enviarCorreo("Información sobre su denuncia", mensaje, dispute.getUser().getEmail());
			dispute.setOpen(false);
			disputeService.save(dispute);

		});
		denunciaInvalida.setVisible(dispute.isOpen() == true);
		cerrarAnuncio.addStyleName("danger");
		denunciaInvalida.addStyleName("primary");
		h.addComponents(descripcion, anuncioCasa);
		botones.addComponents(cerrarAnuncio, denunciaInvalida);
		editor.addComponents(h, botones);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		updateDisputesGrid();
	}

	private void updateDisputesGrid() {
		Pageable pageable = new PageRequest(this.paginationResource.pageIndex(), this.paginationResource.limit());

		String value = selectDisputeType.getValue();

		Page<Dispute> disputes = null;

		if (value.equals("Mostrar todos"))
			disputes = disputeService.findAllPageable(pageable);
		else
			disputes = disputeService.findByReason(value, pageable);

		pagination.setTotalCount(disputes.getTotalElements());

		grid.setItems(disputes.getContent());

	}

}
