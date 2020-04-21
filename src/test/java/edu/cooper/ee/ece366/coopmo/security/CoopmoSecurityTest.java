package edu.cooper.ee.ece366.coopmo.security;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

// TODO Final Project

@RunWith(SpringRunner.class)
//@WebMvcTest(SecuredController.class)
public class CoopmoSecurityTest {

//    @Autowired
//    private MockMvc mvc;
//
//    // ... other methods
//
//    @WithMockUser(value = "spring")
//    @Test
//    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
//        mvc.perform(get("/private/hello").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    public static void testSecurity() throws IOException, InterruptedException{
//        AuthenticationManager authenticationManager;
//        // Test: Checking Login
//        System.out.println("Creating One User");
//        String user1 = createUser("name1", "username1", "password1", "email1@gmail.com", "handle1");
//        if (user1 == null)
//            System.out.println("Creating a new User failed");
//        System.out.println("User1 ID: " + user1 + "\n");
//
//        String username = "username1";
//        String password = "password1";
//
//        try{
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            if(authentication.isAuthenticated()){
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                System.out.println("Successful Authentication");
//            }
//        }
//        catch (AuthenticationException e) {
//            System.out.println("Authentication Failed");
//        }
//    }
}