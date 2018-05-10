package es.uca.iw.proyectoCompleto;

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
import es.uca.iw.proyectoCompleto.security.VaadinSessionSecurityContextHolderStrategy;
import es.uca.iw.proyectoCompleto.users.User;
import es.uca.iw.proyectoCompleto.users.UserService;
import es.uca.iw.proyectoCompleto.reports.ReportService;
import es.uca.iw.proyectoCompleto.reports.Report;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ProyectoCompletoApplication {

	private static final Logger log = LoggerFactory.getLogger(ProyectoCompletoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoCompletoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(UserService service,ApartmentService ap, ReportService re, BookingService bs) {
		return (args) -> {
			
			//Esto para probar la tabla del booking
			if(bs.findAll().size()==0)
			{
				bs.save(new Booking("03/07/2018", "13/07/2018", 300));
			}

			if(ap.findAll().size()==0)
			{
				ap.save(new Apartment("apartamento", "es un apartamento",3,false,"unifamiliar"));
				//PONER AQUI BOOKING SERVICE
			}
			if(re.findAll().size()==0)
			{
				re.save(new Report("23/03/91", "hola", "vater sucio", "el vater esta muy sucio loco"));
			}
			if (service.findAll().size() == 0) {
				// save a couple of users with default password: default
				service.save(new User("Juan", "Bauer"));
				service.save(new User("Chloe", "O'Brian"));
				service.save(new User("Kim", "Bauer"));
				service.save(new User("David", "Palmer"));
				service.save(new User("Michelle", "Dessler"));


				User root = new User("root", "root");
				root.setPassword("root");
				service.save(root);

				// fetch all users
				log.info("Users found with findAll():");
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
				log.info("");
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
