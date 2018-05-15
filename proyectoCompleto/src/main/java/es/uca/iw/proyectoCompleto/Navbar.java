package es.uca.iw.proyectoCompleto;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class Navbar extends HorizontalLayout
{
	/**
	 * 
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
		            // Color the boxes with random colors
		            int rgb = (int) (Math.random()*(1<<24));
		            return "background: #" + Integer.toHexString(rgb);
		        }
		        return null;
		    }
		};
		//layout.setWidth("900px"); // Causes to wrap the contents

		// Add boxes of various sizes
		for (int i=0; i<40; i++) {
		    Label box = new Label("&nbsp;", ContentMode.HTML);
		    box.addStyleName("flowbox");
		    box.setWidth((float) Math.random()*50,
		                	Unit.PIXELS);
		    box.setHeight((float) Math.random()*50,
		    			Unit.PIXELS);
		    layout.addComponent(box);
		}
		addComponent(layout);
	}
}
