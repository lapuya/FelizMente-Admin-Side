package es.ite.felizmente.service;

import es.ite.felizmente.entity.Admin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class AdminProxyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AdminProxyService aps;


    @Test
    public void given_a_valid_admin_should_return_equal(){
        Admin a = Admin.builder().username("amrobinska").password("mypassword2").build();
        Mockito.when(restTemplate.postForEntity("http://localhost:8080/felizmente/admins/login", a, Admin.class))
                .thenReturn(new ResponseEntity<Admin>(a, HttpStatus.OK));
        Admin aAux = aps.get(a);
        Assert.assertEquals(a, aAux);
    }

    @Test
    public void given_not_valid_admin_should_return_null() {
        Admin a = Admin.builder().username("test").password("mypasstest").build();
        Mockito.when(restTemplate.postForEntity("http://localhost:8080/felizmente/admins/login", a, Admin.class))
                .thenReturn(new ResponseEntity<Admin>(a, HttpStatus.NOT_FOUND));
        Admin aAux = aps.get(a);
        Assert.assertNull(aAux);
    }
}
