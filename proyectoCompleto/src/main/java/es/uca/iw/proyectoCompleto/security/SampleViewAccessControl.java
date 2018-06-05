package es.uca.iw.proyectoCompleto.security;

import org.springframework.stereotype.Component;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;

import es.uca.iw.proyectoCompleto.LoginView;

/**
 * This demonstrates how you can control access to views.
 */
@Component
public class SampleViewAccessControl implements ViewAccessControl {

	@Override
	public boolean isAccessGranted(UI ui, String beanName) {

		System.out.println("COMPROBANDO " + beanName + " PARA USUARIO CON ROLES: " + SecurityUtils.roles());

		if (SecurityUtils.hasRole("ROLE_ADMIN")) {
			return true;
		}
		else if (beanName.equals("defaultView")) {
			return true;
		} else if (beanName.equals("loginView")) {
			return SecurityUtils.roles() == null;
		} else if(beanName.equals("registerScreen"))
		{
			return SecurityUtils.roles() == null;
		}
		else if(beanName.equals("mainScreen"))
		{
			return  SecurityUtils.hasRole("ROLE_MANAGER") || SecurityUtils.hasRole("ROLE_ADMIN")
					|| SecurityUtils.hasRole("ROLE_USER");
		}
		else if (beanName.equals("userView")) {
			return SecurityUtils.hasRole("ROLE_ADMIN") || SecurityUtils.hasRole("ROLE_MANAGER");
		} else if (beanName.equals("userManagementView")) {
			return SecurityUtils.hasRole("ROLE_MANAGER");
		} else if (beanName.equals("apartmentManagementView")) {
			return SecurityUtils.hasRole("ROLE_MANAGER") || SecurityUtils.hasRole("ROLE_ADMIN")
					|| SecurityUtils.hasRole("ROLE_USER");
		} else if (beanName.equals("apartmentListView")) {
			return SecurityUtils.hasRole("ROLE_MANAGER") || SecurityUtils.hasRole("ROLE_ADMIN")
					|| SecurityUtils.hasRole("ROLE_USER") || SecurityUtils.roles() == null;
		} else if (beanName.equals("apartmentView")) {
			return SecurityUtils.hasRole("ROLE_MANAGER") || SecurityUtils.hasRole("ROLE_ADMIN")
					|| SecurityUtils.hasRole("ROLE_USER");
		} else if (beanName.equals("reportManagementView")) {
			return SecurityUtils.hasRole("ROLE_MANAGER");
		} else if (beanName.equals("bookingManagementView")) {
			return SecurityUtils.hasRole("ROLE_MANAGER") || SecurityUtils.hasRole("ROLE_USER");
		} else if (beanName.equals("imageApartmentView")) {
			return SecurityUtils.hasRole("ROLE_MANAGER");
		} 
		  else if(beanName.equals("userProfileView")) {
			return SecurityUtils.hasRole("ROLE_USER");
		} else if (beanName.equals("bookingView")) {
			return SecurityUtils.hasRole("ROLE_USER");
		}
		
		else {
			return false;
		}
	}
}
