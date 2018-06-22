package es.uca.iw.proyectoCompleto.users;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = UserProfileView.VIEW_NAME)
public class UserProfileView extends VerticalLayout implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "userProfileView";
	
	private UserEditor editor;

	@Autowired
	public UserProfileView(UserService service, UserEditor editor) {
		this.editor = editor;
	}
	
	@PostConstruct
	void init() {
		addComponent(editor);
	} 
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
