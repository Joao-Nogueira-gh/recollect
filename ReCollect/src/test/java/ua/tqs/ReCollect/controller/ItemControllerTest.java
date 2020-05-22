package ua.tqs.ReCollect.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.tqs.ReCollect.repository.UserRepository;
import ua.tqs.ReCollect.service.ItemService;
import ua.tqs.ReCollect.service.LocationService;
import ua.tqs.ReCollect.service.MyUserDetailsService;
import ua.tqs.ReCollect.service.UserService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebSecurityConfiguration.class)
@WebAppConfiguration
public class ItemControllerTest {

    // @Autowired
    // private TestRestTemplate template;

    // @Autowired
    // private UserService repo;

    MockMvc mockMvc;

    // @MockBean
    // ItemService itemService;

    // @MockBean
    // MyUserDetailsService userDetailsService;

    // @MockBean
    // UserService userService;

    // @MockBean
    // LocationService locationService;

    // @MockBean
    // UserRepository repo;

    @Autowired
	private WebApplicationContext context;



    // Set up and clean up

    @Before
    private void setUp() {

        // repo.save(new User("mail@mail.com", "mail@mail.com", "test", "123123123"));
        mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
                
        System.out.println(mockMvc);

    }

    // @AfterAll
    // private void cleanUp() {

    //     repo.deleteAll();

    // }

    // @Test
    // public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
    //     ResponseEntity<String> result = template.withBasicAuth("mail@mail.com", "password").getForEntity("/announce",
    //             String.class);
    //     assertEquals(HttpStatus.OK, result.getStatusCode());
    // }

    @Test
    public void withMockUser() throws Exception {
        // System.out.println(mockMvc);

        mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/announce").with(user("admin").password("pass").roles("ADMIN"))).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // GET announce page

    // POST announce page correctly

    // POST announce page incorrectly

}