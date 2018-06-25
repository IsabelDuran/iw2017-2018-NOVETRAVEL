/**
 * 
 */
package es.uca.iw.proyectoCompleto.users;


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
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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
	@Autowired
	private UserRepository userRepository;

	private PaginationResource paginationResource;
	private Pagination pagination;
	public UserManagementView()
	{
		this.grid = new Grid<>(User.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo usuario");
	}

	@PostConstruct
	void init() {

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

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
			    updateUsersGrid();
		    }
		});	
		
		
		addComponents(actions, grid, pagination);
		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setColumns("firstName", "lastName", "username", "email");

		filter.setPlaceholder("Filtrar por apellido");

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> {
		    updateUsersGrid();
		});

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

	}

	

	@Override
	public void enter(ViewChangeEvent event)
	{	
	    updateUsersGrid();
	}

	private void updateUsersGrid() {
		Pageable pageable = new PageRequest(this.paginationResource.pageIndex(), this.paginationResource.limit());
    	Page<User> users = userRepository.findByLastNameContaining(filter.getValue(), pageable);
		pagination.setTotalCount(users.getTotalElements());
		grid.setItems(users.getContent());
	}
	
	
	


}
