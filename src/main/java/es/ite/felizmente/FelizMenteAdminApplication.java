package es.ite.felizmente;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
public class FelizMenteAdminApplication implements CommandLineRunner {
	
	@Autowired
	private UserProxyService ups;


	@Autowired
	private ApplicationContext context;

	// Makes http comms to our REST service
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(FelizMenteAdminApplication.class, args);
	}
	
	@Override
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

		// Stops our app
		stopApplicacion();
	}
	
	public void stopApplicacion() {
		SpringApplication.exit(context, () -> 0);
	}

	private void listUsers() {
		List<User> lista = ups.list();
		for (int i = 0; i < lista.size(); i++)
			System.out.println(lista.get(i));
		
	}

	private void getUserById() {
		int id;

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the id of the user: ");
		id = sc.nextInt();
		System.out.println(ups.get(id));		
	}

	private void modifyUser() {
		String street, number, region, zipCode, country; //address
		String name, lastName;
		Scanner sc = new Scanner(System.in);
		int id;
		User uModify = new User();
		Address aModify = new Address();
		System.out.println("Enter de the id of the user: ");
		id = sc.nextInt();
		uModify.setId(id);
		
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
		
		aModify.setStreet(street);
		aModify.setNumber(number);
		aModify.setRegion(region);
		aModify.setZipCode(zipCode);
		aModify.setCountry(country);
		uModify.setLastName(lastName);
		uModify.setName(name);
		uModify.setAddress(aModify);
		
		ups.modify(uModify);
		
	}

	private void deleteUser() {
		Scanner sc = new Scanner(System.in);
		int id;

		System.out.println("Enter the id of the user: ");
		id = sc.nextInt();

		ups.delete(id);
		
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
		u.setName(name);
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
