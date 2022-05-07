package es.ite.felizmente;

import java.util.List;
import java.util.Scanner;

import es.ite.felizmente.entity.Admin;
import es.ite.felizmente.service.AdminProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import es.ite.felizmente.entity.User;
import es.ite.felizmente.service.UserProxyService;

@SpringBootApplication
public class FelizMenteAdminApplication implements CommandLineRunner {
	
	@Autowired
	private UserProxyService ups;

	@Autowired
	private AdminProxyService aps;


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

		int op, opLogin;
		boolean succesful = false;

		opLogin = loginMenu();
		while (opLogin != 2 && !succesful) {
			if (opLogin == 1) {
				succesful = getAdminCredentials();
			}
			if (!succesful){
				opLogin = loginMenu();
			}
		}
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

	private boolean getAdminCredentials() {
		Scanner sc = new Scanner(System.in);
		String username, password;

		System.out.println("******************** SYSTEM MENU ********************");
		System.out.println("Please enter your username: ");
		username = sc.nextLine();
		System.out.println("Please enter your password: ");
		password = sc.nextLine();

		return aps.get(Admin.builder().username(username).password(password).build()) != null;
	}

	private int loginMenu() {
		Scanner sc = new Scanner(System.in);
		int op;

		System.out.println("1. Login to System Menu");
		System.out.println("2. Exit");
		op = sc.nextInt();
		while (op < 1 || op > 2) {
			System.out.println("PLEASE SELECT THE CORRECT OPTION");
			System.out.println("1. Login to System Menu");
			System.out.println("2. Exit");
			op = sc.nextInt();
		}
		return op;
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
		String username, email, password;
		Scanner sc = new Scanner(System.in);
		int id;
		User uModify = new User();
		System.out.println("Enter the id of the user: ");
		id = sc.nextInt();
		uModify.setId(id);
		sc.nextLine();
		System.out.println("New Username: ");
		username = sc.nextLine();

		System.out.println("New email: ");
		email = sc.nextLine();

		System.out.println("New password: ");
		password = sc.nextLine();

		uModify.setUsername(username);
		uModify.setEmail(email);
		uModify.setPassword(password);

		ups.modify(uModify);
		
	}

	private void deleteUser() {
		Scanner sc = new Scanner(System.in);
		int id;

		System.out.println("Enter the id of the user: ");
		id = sc.nextInt();

		if(ups.delete(id))
			System.out.println("Usuario con id: " + id + " borrado correctamente");
	}

	private void registerUser() {
		String username, email, password;
		Scanner sc = new Scanner(System.in);
	
		User u = new User();

		System.out.println("Enter the username: ");
		username = sc.nextLine();

		System.out.println("Enter the email: ");
		email = sc.nextLine();

		System.out.println("Enter the password: ");
		password = sc.nextLine();

		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(password);

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
		sc.nextLine();
		while (op < 1 || op > 6) {
			System.out.println("Please select a correct option.");
			System.out.println("1. Register a User");
			System.out.println("2. Delete a User by ID");
			System.out.println("3. Modify User by ID");
			System.out.println("4. Get a User by ID");
			System.out.println("5. List all Users");
			System.out.println("6. Exit");
			op = sc.nextInt();
			sc.nextLine();
		}

		return op;
	}
}