/**
 * 
 */
package es.uca.iw.proyectoCompleto.users;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.uca.iw.proyectoCompleto.apartments.ApartmentEditor;

@SpringView(name = UserManagementView.VIEW_NAME)
public class UserManagementView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "userManagementView";

	private Grid<User> grid;
	private TextField filter;
	private Button addNewBtn;

	private UserEditor editor;

	private final UserService service;

	@Autowired
	public UserManagementView(UserService service, UserEditor editor) {
		this.service = service;
		this.editor = editor;
		this.grid = new Grid<>(User.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo usuario");
	}

	@PostConstruct
	void init() {

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

		addComponents(actions, grid, editor);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName");

		filter.setPlaceholder("Filtrar por apellido");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listUsers(e.getValue()));

		// Connect selected User to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			VaadinSession.getCurrent().setAttribute("usuarioEditado", e.getValue().getId());
			getUI().getNavigator().navigateTo(UserEditor.VIEW_NAME);

		});

		// Instantiate and edit new User the new button is clicked
		addNewBtn.addClickListener(e -> {
			VaadinSession.getCurrent().setAttribute("usuarioEditado", null);
			getUI().getNavigator().navigateTo(UserEditor.VIEW_NAME);
		});

//		// Listen changes made by the editor, refresh data from backend
//		editor.setChangeHandler(() -> {
//			editor.setVisible(false);
//			listUsers(filter.getValue());
//		});
//
//		// Initialize listing
//		listUsers(null);

	}

	private void listUsers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(service.findAll());
		} else {
			grid.setItems(service.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
