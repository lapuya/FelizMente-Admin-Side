package es.ite.felizmente.service;

import es.ite.felizmente.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminProxyService {
    public static final String URL = "http://localhost:8080/felizmente/admins/";

    @Autowired
    private RestTemplate restTemplate;

    public Admin get(Admin a) {
        //possible encryptation of the token here
        StringBuilder sb = new StringBuilder();
        String token = sb.append(a.getUsername()).append("-").append(a.getPassword()).toString();
        try {
            String completeURL = URL + token;
            ResponseEntity<Admin> re = restTemplate.getForEntity(completeURL, Admin.class);
            HttpStatus hs = re.getStatusCode();
            if (hs == HttpStatus.OK) {
                return re.getBody();
            } else {
                System.out.println("Not admitted");
                return null;
            }
        } catch (HttpClientErrorException e) {
            System.out.println("get -> User not found: " + a.toString());
            System.out.println("get -> Response code: " + e.getStatusCode());
            return null;
        }
    }
}
