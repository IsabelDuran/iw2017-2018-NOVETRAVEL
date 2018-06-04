/**
 * 
 */
package es.uca.iw.proyectoCompleto.imageApartment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamVariable;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.FileDropTarget;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.ProyectoCompletoApplication;
import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.reports.Report;


@SpringView(name = ImageApartmentView.VIEW_NAME)
public class ImageApartmentView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "imageApartmentView";

	
	private Panel panel[];
	private ImageApartmentEditor editor;

	
	private final ImageApartmentService service;
	private final ApartmentService apservice;
	private ImageApartmentRepository repo;
	

	@Autowired
	public ImageApartmentView(ImageApartmentService service,ApartmentService ap,ImageApartmentEditor editor) {
		this.service = service;
		apservice=ap;
		this.editor = editor;
		panel = new Panel[10];
	    
	}

	
	@PostConstruct
	void init() {
		
	    final Label infoLabel = new Label("Arrastre los ficheros implicados");
        infoLabel.setWidth(240.0f, Unit.PIXELS);
		final VerticalLayout dropPane = new VerticalLayout(infoLabel);
        dropPane.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
        dropPane.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        dropPane.setSizeUndefined();
 
		ProgressBar progress = new ProgressBar();
        progress.setIndeterminate(true);
        progress.setVisible(false);
        dropPane.addComponent(progress);
        dropPane.addComponent(infoLabel);
 
        FileDropTarget f=new FileDropTarget<>(dropPane, fileDropEvent -> {
            final int fileSizeLimit = 2 * 1024 * 1024; // 2MB
 
            fileDropEvent.getFiles().forEach(html5File -> {
                final String fileName = html5File.getFileName();
 
                if (html5File.getFileSize() > fileSizeLimit) {
                    Notification.show(
                            "File rejected. Max 2MB files are accepted by Sampler",
                            Notification.Type.WARNING_MESSAGE);
                } else {
                    final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    final StreamVariable streamVariable = new StreamVariable() {
 
                        @Override
                        public boolean listenProgress() {
                            return false;
                        }
 
                        @Override
                        public void onProgress(
                                final StreamingProgressEvent event) {
                        }
 
                        @Override
                        public void streamingStarted(
                                final StreamingStartEvent event) {
                        }
 
                        @Override
                        public void streamingFinished(
                                final StreamingEndEvent event) {
                            progress.setVisible(false);
                            showFile(fileName, bas);
                            ImageApartment im=new ImageApartment();
                
                            im.setApartment(apservice.findOne(1L));
                            im.setFile(bas.toByteArray());
                            service.save(im);
                        }
 
                        @Override
                        public void streamingFailed(
                                final StreamingErrorEvent event) {
                            progress.setVisible(false);
                        }
 
                        @Override
                        public boolean isInterrupted() {
                            return false;
                        }

                        @Override
                        public OutputStream getOutputStream() {
                            return bas;
                        }
                    };
                    html5File.setStreamVariable(streamVariable);
                    progress.setVisible(true);
                }
            });
        });
        
        addComponent(dropPane);
		
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		});

		// Initialize listing
		listImageApartments(null);
		
	}

	private void listImageApartments(String filterText) {
		
		if (StringUtils.isEmpty(filterText)) {
			HorizontalLayout lista = new HorizontalLayout();
			addComponents(lista);
			//
			//
		} 
	}
	
	private void showFile(final String name, final ByteArrayOutputStream bas) {
        // resource for serving the file contents
        final StreamResource.StreamSource streamSource = () -> {
            if (bas != null) {
                final byte[] byteArray = bas.toByteArray();
                return new ByteArrayInputStream(byteArray);
            }
            return null;
        };
        final StreamResource resource = new StreamResource(streamSource, name);
 
        // show the file contents - images only for now
        final Embedded embedded = new Embedded(name, resource);
        showComponent(embedded, name);
    }
	
	 private void showComponent(final Component c, final String name) {
	        final VerticalLayout layout = new VerticalLayout();
	        layout.setSizeUndefined();
	        layout.setMargin(true);
	        final Window w = new Window(name, layout);
	        w.addStyleName("dropdisplaywindow");
	        w.setSizeUndefined();
	        w.setResizable(false);
	        c.setSizeUndefined();
	        layout.addComponent(c);
	        UI.getCurrent().addWindow(w);
	    }
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
