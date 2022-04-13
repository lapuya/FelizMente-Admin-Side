package es.ite.felizmente.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.ite.felizmente.entity.User;

@Service
public class UserProxyService {

	public static final String URL = "http://localhost:3306/felizmente/";

	@Autowired
	private RestTemplate restTemplate;

	public User register(User u) {
		try {

			ResponseEntity<User> re = restTemplate.postForEntity(URL, u, User.class);
			System.out.println("register -> Response code " + re.getStatusCode());
			return re.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("register -> User not registered, id: " + u);
			System.out.println("register -> Responde code: " + e.getStatusCode());
			return null;
		}
	}

	public boolean delete(int id) {
		try {

			restTemplate.delete(URL + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("delete -> User NOT deleted, id: " + id);
			System.out.println("delete -> Response code: " + e.getStatusCode());
			return false;
		}
	}

	public boolean modify(User v) {
		try {

			restTemplate.put(URL + v.getId(), v, User.class);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("modify -> User not modified, id: " + v.getId());
			System.out.println("modify -> Responde code: " + e.getStatusCode());
			return false;
		}
	}

	public User get(int id) {
		try {
			ResponseEntity<User> re = restTemplate.getForEntity(URL + id, User.class);
			HttpStatus hs = re.getStatusCode();
			if (hs == HttpStatus.OK) {
				return re.getBody();
			} else {
				System.out.println("Not admitted");
				return null;
			}
		} catch (HttpClientErrorException e) {
			System.out.println("get -> User not found, id: " + id);
			System.out.println("get -> Response code: " + e.getStatusCode());
			return null;
		}
	}

	public List<User> list() {

		try {
			ResponseEntity<User[]> response = restTemplate.getForEntity(URL, User[].class);
			User[] arrayPersonas = response.getBody();
			return Arrays.asList(arrayPersonas);// convertimos el array en un arraylist
		} catch (HttpClientErrorException e) {
			System.out.println("list -> Error getting list of Users");
			System.out.println("list -> Responde code: " + e.getStatusCode());
			return null;
		}
	}
}
