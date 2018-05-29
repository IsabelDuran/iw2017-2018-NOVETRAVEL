package es.uca.iw.proyectoCompleto;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.uca.iw.proyectoCompleto.apartments.Apartment;
import es.uca.iw.proyectoCompleto.apartments.ApartmentService;
import es.uca.iw.proyectoCompleto.bookings.Booking;
import es.uca.iw.proyectoCompleto.bookings.BookingService;
import es.uca.iw.proyectoCompleto.location.Location;
import es.uca.iw.proyectoCompleto.location.LocationService;
import es.uca.iw.proyectoCompleto.reports.Report;
import es.uca.iw.proyectoCompleto.reports.ReportService;
import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.Administrator;
import es.uca.iw.proyectoCompleto.users.Manager;
import es.uca.iw.proyectoCompleto.users.Registered;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ProyectoCompletoApplication {

	private static final Logger log = LoggerFactory.getLogger(ProyectoCompletoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ProyectoCompletoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(UserService service,ApartmentService ap, ReportService re, BookingService bs) {
		return (args) -> {
			try {
			 LocalDate fe = LocalDate.of(2018, 8, 03);
			 LocalDate fs = LocalDate.of(2018, 8, 13);
			 
			 LocalDate t1 = LocalDate.of(2018, 12, 13);
			 LocalDate t2 = LocalDate.of(2018, 12, 23);
			 
			 Apartment a1=null, a2 = null;
			 User u1=null, u2 = null;
			 
			if(re.findAll().size()==0)
			{
				re.save(new Report("23/03/91", "hola", "vater sucio", "el vater esta muy sucio loco"));
			}
			
			if (service.findAll().size() == 0) 
			{
				
				u1=new User("Juan", "Bauer");
				u2 = new User("Michelle", "Dessler");
				// save a couple of users with default password: default
				service.save(new User("Chloe", "O'Brian"));
				service.save(new User("Kim", "Bauer"));
				service.save(new User("David", "Palmer"));
				service.save(u1);
				service.save(u2);
				
				Registered registrado = new Registered("registrado", "registrado");
				registrado.setPassword("registrado");
				service.save(registrado);
				
				Manager manager = new Manager("manager", "manager");
				manager.setPassword("manager");
				service.save(manager);
				
				Administrator admin = new Administrator("admin", "admin");
				admin.setPassword("admin");
				service.save(admin);
				
				User root = new User("root", "root");
				root.setPassword("root");
				service.save(root);
				
				System.out.println("USUARIOS 1 CREADOS!!!!!!! ");

				// fetch all users
			/*	log.info("Users found with findAll():");
				log.info("-------------------------------");
				for (User user : service.findAll()) {
					log.info(user.toString());
				}
				log.info("");

				// fetch an individual user by ID
				User user = service.findOne(1L);
				log.info("User found with findOne(1L):");
				log.info("--------------------------------");
				log.info(user.toString());
				log.info("");

				// fetch users by last name
				log.info("User found with findByLastNameStartsWithIgnoreCase('Bauer'):");
				log.info("--------------------------------------------");
				for (User bauer : service.findByLastNameStartsWithIgnoreCase("Bauer")) {
					log.info(bauer.toString());
				}
				log.info("");*/
				
				System.out.println("USUARIOS final CREADOS!!!!!!! ");
			}
			
			if(ap.findAll().size()==0)
			{
			
				a1=new Apartment("apartamento", "es un apartamento",3.0,true,"unifamiliar");
				Location l1 = new Location("hola","11500", "Antonio", 1, 1 , 'c');
				//ls.save(l1); 
				//a1.setLocation(l1);
				a2 = new Apartment("apartamento2", "aaa", 20.0, true, "piso");
				ap.save(a1);
				ap.save(a2);
				
				System.out.println("APARTAMENTOS CREADOS!!!!");
				
			}
			
			if(bs.findAll().size()==0 && ap.findAll().size() != 0 && service.findAll().size() != 0)
			{
				bs.save(new Booking(fe, fs, (double)300, a1, u1));
				bs.save(new Booking(t1,t2, (double) 500, a2, u2));
				System.out.println(" RESERVAS CREADAS!!!!!");
			}}
			catch(Exception e)
			{
				System.out.println("Excepcion no controlada" + e.getMessage());
			}

		};
	}	


	@Configuration
	@EnableGlobalMethodSecurity(securedEnabled = true)
	public static class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

		@Autowired
		private UserDetailsService userDetailsService;

		@Bean
		public PasswordEncoder encoder() {
			return new BCryptPasswordEncoder(11);
		}

		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService);
			authProvider.setPasswordEncoder(encoder());
			return authProvider;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.authenticationProvider(authenticationProvider());

			// auth
			// .inMemoryAuthentication()
			// .withUser("admin").password("p").roles("ADMIN", "MANAGER",
			// "USER")
			// .and()
			// .withUser("manager").password("p").roles("MANAGER", "USER")
			// .and()
			// .withUser("user").password("p").roles("USER");
			
		}

		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return authenticationManager();
		}

		static {
			// Use a custom SecurityContextHolderStrategy
			SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
		}
	}
}
