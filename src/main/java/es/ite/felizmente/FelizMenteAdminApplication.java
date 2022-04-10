package es.ite.felizmente;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import es.ite.felizmente.entity.Address;
import es.ite.felizmente.entity.User;
import es.ite.felizmente.service.UserProxyService;

@SpringBootApplication
public class FelizMenteAdminApplication {
	
	@Autowired
	private UserProxyService ups;

	// Inyectamos el propio contexto de Spring, ya que es una app web
	// que se lanzará en un tomcat y necesitaremos poder pararla.

	@Autowired
	private ApplicationContext context;

	// Realiza las comunicaciones http a nuestro servicio REST
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(FelizMenteAdminApplication.class, args);
	}
	
	public void run(String... args) throws Exception {

		int op;

		op = menu();
		while (op != 6) {
			if (op == 1)
				registerUser();
			else if (op == 2)
				deleteUser();
			else if (op == 3)
				modifyUser();
			else if (op == 4)
				getUserById();
			else if (op == 5)
				listUsers();
			op = menu();

		}

		System.out.println("******************** Closing Application ********************");

		// Paramos nuestra aplicación Spring Boot
		stopApplicacion();
	}
	
	public void stopApplicacion() {
		SpringApplication.exit(context, () -> 0);
	}

	private void listUsers() {
		// TODO Auto-generated method stub
		
	}

	private void getUserById() {
		// TODO Auto-generated method stub
		
	}

	private void modifyUser() {
		// TODO Auto-generated method stub
		
	}

	private void deleteUser() {
		// TODO Auto-generated method stub
		
	}

	private void registerUser() {
		String street, number, region, zipCode, country; //address
		String name, lastName;
		Scanner sc = new Scanner(System.in);
	
		User u = new User();
		Address a = new Address();

		System.out.println("Name of the user: ");
		name = sc.nextLine();

		System.out.println("Surname of the user: ");
		lastName = sc.nextLine();
		
		System.out.println("----- Address of the user ----- ");
		System.out.println("Street: ");
		street = sc.nextLine();
		System.out.println("Number: ");
		number = sc.nextLine();
		System.out.println("Region: ");
		region = sc.nextLine();
		System.out.println("Zip Code: ");
		zipCode = sc.nextLine();
		System.out.println("Country: ");
		country = sc.nextLine();
		
		a.setStreet(street);
		a.setNumber(number);
		a.setRegion(region);
		a.setZipCode(zipCode);
		a.setCountry(country);
		u.setLastName(lastName);
		u.setName(lastName);
		u.setAddress(a);

		ups.register(u);
	}

	public int menu() {
		Scanner sc = new Scanner(System.in);
		int op;

		System.out.println("***************** MENU *****************");
		System.out.println("Select an option");
		System.out.println("1. Register a User");
		System.out.println("2. Delete a User by ID");
		System.out.println("3. Modify User by ID");
		System.out.println("4. Get a User by ID");
		System.out.println("5. List all Users");
		System.out.println("6. Exit");

		op = sc.nextInt();
		while (op < 1 || op > 6) {
			System.out.println("1. Register a User");
			System.out.println("2. Delete a User by ID");
			System.out.println("3. Modify User by ID");
			System.out.println("4. Get a User by ID");
			System.out.println("5. List all Users");
			System.out.println("6. Exit");
			op = sc.nextInt();
		}

		return op;

	}

	
	

}
