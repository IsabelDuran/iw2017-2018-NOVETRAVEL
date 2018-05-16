package es.uca.iw.proyectoCompleto;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class Navbar extends HorizontalLayout
{
	/**
	 * @author isa
	 */
	private static final long serialVersionUID = 1L;

	public Navbar()
	{
        setMargin(false);
        setSpacing(false);
        setSizeFull();
		CssLayout layout = new CssLayout() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    protected String getCss(Component c) {
		        if (c instanceof Label) {
		            // Color the box with 16059007 (#
		            int rgb = 16059007;
		            return "background: #" + Integer.toHexString(rgb);
		        }
		        return null;
		    }
		};
		layout.setWidth("100%"); // Causes to wrap the contents

		// Add boxes of various sizes
		    Label box = new Label("&nbsp;", ContentMode.HTML);
		    box.addStyleName("flowbox");
		    box.setWidth("100%");
		    box.setHeight(70,
		    			Unit.PIXELS);
		    layout.addComponent(box);
		addComponent(layout);
	}
}
