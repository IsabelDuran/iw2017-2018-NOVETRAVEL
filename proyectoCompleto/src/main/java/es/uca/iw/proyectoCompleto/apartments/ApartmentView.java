/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

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
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamVariable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamVariable.StreamingEndEvent;
import com.vaadin.server.StreamVariable.StreamingErrorEvent;
import com.vaadin.server.StreamVariable.StreamingProgressEvent;
import com.vaadin.server.StreamVariable.StreamingStartEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.FileDropTarget;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.MainScreen;
import es.uca.iw.proyectoCompleto.ProyectoCompletoApplication;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentService;
import es.uca.iw.proyectoCompleto.reports.Report;


@SpringView(name = ApartmentView.VIEW_NAME)
public class ApartmentView extends VerticalLayout implements View
{
	public static final String VIEW_NAME = "apartmentView";

	
	private Panel panel[];
	private ApartmentEditor editor;

	private final ImageApartmentService imageService;
	
	private final ApartmentService service;
	private ApartmentRepository repo;
	
	private Apartment apartment;

	@Autowired
	public ApartmentView(ApartmentService service, ApartmentEditor editor,ImageApartmentService imageService) {
		this.service = service;
		this.editor = editor;
		this.imageService=imageService;
		panel = new Panel[10];
	    
	}

	
	@PostConstruct
	void init() {
		
		// Hook logic to components
	
		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		});

		// Initialize listing
		listApartments(null);
		
	}

	private void listApartments(String filterText) {
		
		if (StringUtils.isEmpty(filterText)) {
			HorizontalLayout lista = new HorizontalLayout();
			addComponents(lista);
			apartment = (Apartment) MainScreen.getUltimoPinchado();
			
			//PONER IMAGEN AQUI
			lista.addComponent(new Label(apartment.getName()));
			VerticalLayout abajo = new VerticalLayout();
			addComponents(abajo);
			abajo.addComponent(new Label(apartment.getDescription()));
			abajo.addComponent(new Label(Long.toString(apartment.getImages().get(0).getId())));
			HorizontalLayout imagenes=new HorizontalLayout();
			desplegarImagenes(imagenes);  	
			abajo.addComponent(imagenes);
			
			ponerContenedorImagenes(abajo,imagenes);
			abajo.addComponent(new Label("Precio por día: " + String.valueOf(apartment.getPrice_per_day() + "€")));
			//
			//
		} 
	}
	
	public void desplegarImagenes(Layout contenedor) {
		if(apartment.getImages().size()!=0)
		{
			for(int i=0;i<apartment.getImages().size();i++)
			{
				desplegarImagen(contenedor, apartment.getImages().get(i));
			}
		}
	}
	
	public void ponerContenedorImagenes(Layout l,Layout contenedorImagenes){
		
		final Label infoLabel = new Label("Para añadir imagenes arrastre los ficheros");
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
                            ImageApartment im=new ImageApartment();
                
                            im.setApartment(apartment);
                            im.setFile(bas.toByteArray());
                            imageService.save(im);
                            desplegarImagenes(contenedorImagenes);
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
        
        l.addComponent(dropPane);
		
	}
	
	public void desplegarImagen(Layout l,ImageApartment A) {
		
		final String name="";
		//final ByteArrayOutputStream bas=new ByteArrayOutputStream(A.getFile());
	
		final StreamResource.StreamSource streamSource = () -> {
			
            if (A.getFile() != null) {
                final byte[] byteArray = A.getFile();

                return new ByteArrayInputStream(byteArray);
            }
            return null;
        };
        final StreamResource resource = new StreamResource(streamSource, name);
        Image im=new Image("name",resource);
        im.setWidth(100,Unit.PIXELS);
        im.setHeight(100,Unit.PIXELS);
        l.addComponent(im);
        
        
	}
	
	
	public void ultimo(Apartment pinchado) {
		MainScreen.setUltimoPinchado(pinchado);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
