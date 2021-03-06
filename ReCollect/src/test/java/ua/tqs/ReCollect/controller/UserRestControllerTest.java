package ua.tqs.ReCollect.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import ua.tqs.ReCollect.model.User;
import ua.tqs.ReCollect.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserService service;

	@Test
	void apiShouldReturnAccurateListOfUsers() throws Exception {

        ArrayList<User> all = (ArrayList<User>) service.getAll();
        
        @SuppressWarnings("unchecked")
        ArrayList<User> apiItems = this.restTemplate.getForObject("/api/users/", ArrayList.class);
        
        assertEquals(all.size(), apiItems.size());

	}
}