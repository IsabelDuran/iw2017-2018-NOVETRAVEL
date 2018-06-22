/**
 * 
 */
package es.uca.iw.proyectoCompleto.apartments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamVariable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.dnd.FileDropTarget;
import com.vaadin.ui.themes.ValoTheme;

import es.uca.iw.proyectoCompleto.bookings.BookingView;
import es.uca.iw.proyectoCompleto.disputes.DisputeView;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartment;
import es.uca.iw.proyectoCompleto.imageApartment.ImageApartmentService;
import es.uca.iw.proyectoCompleto.security.SecurityUtils;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;


@SpringView(name = ApartmentView.VIEW_NAME)
public class ApartmentView extends VerticalLayout implements View
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final String VIEW_NAME = "apartmentView";

	
	@Autowired
	private ImageApartmentService imageService;
	
	@Autowired
	private ApartmentService service;
	
	
	private Apartment apartment;
	
	@Autowired
	private UserService userService;


	@PostConstruct
	void init() {
		
		// Hook logic to components
	
		// Listen changes made by the editor, refresh data from backend
		// Initialize listing

		
	}
	

	public void mostrarApartamento() {
		
		VerticalLayout v = new VerticalLayout();
		HorizontalLayout imagenes = new HorizontalLayout();

		Label nombreAp = new Label(apartment.getName());
		Label description = new Label(apartment.getDescription());
		Label precio = new Label("Precio por día: " + String.valueOf(apartment.getPricePerDay() + "€"));
		Button denunciar=new Button("Denuncia este anuncio", e -> getUI().getNavigator().navigateTo(DisputeView.VIEW_NAME + "/" + apartment.getId()));
		
		Button reservar = new Button("Reservar", e -> getUI().getNavigator().navigateTo(BookingView.VIEW_NAME + "/" + apartment.getId())) ;
		reservar.setVisible(SecurityUtils.hasRole("ROLE_USER"));
		
		description.setWidth("1000px");
		
		desplegarImagenes(imagenes); 
		
		addComponent(v);
		
		v.addComponents(nombreAp, description, imagenes,reservar);

		User usuarioLogeado = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if(usuarioLogeado.getId() == apartment.getUser().getId())
		{
			ponerContenedorImagenes(v,imagenes);
		} 
		
		v.addComponents(precio, reservar,denunciar);

		
		
	}
	
	public void desplegarImagenes(Layout contenedor) {
	
			contenedor.removeAllComponents();
			
			for(int i=0;i<apartment.getImages().size();i++)
			{
				desplegarImagen(contenedor, apartment.getImages().get(i));
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
 
        new FileDropTarget<>(dropPane, fileDropEvent -> {
            final int fileSizeLimit = 2 * 1024 * 1024; // 2MB
 
            fileDropEvent.getFiles().forEach(html5File -> {
                html5File.getFileName();
 
                if (html5File.getFileSize() > fileSizeLimit) {
                    Notification.show(
                            "File rejected. Max 2MB files are accepted by Sampler",
                            Notification.Type.WARNING_MESSAGE);
                } else {
                    final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    final StreamVariable streamVariable = new StreamVariable() {
 
                        /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

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

		final StreamResource.StreamSource streamSource = () -> {
			
            if (A.getFile() != null) {
                final byte[] byteArray = A.getFile();

                return new ByteArrayInputStream(byteArray);
            }
            return null;
        };
        final StreamResource resource = new StreamResource(streamSource, "");
        Image im=new Image("name",resource);
        im.setWidth(100,Unit.PIXELS);
        im.setHeight(100,Unit.PIXELS);
        l.addComponent(im);
        
        
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null){
	           // split at "/", add each part as a label
	           String[] msgs = event.getParameters().split("/");
	           long id=Long.valueOf(msgs[0]);
	           
	           apartment=service.loadApartmentById(id);
	           mostrarApartamento();
	           
	    }
		
	}

}
