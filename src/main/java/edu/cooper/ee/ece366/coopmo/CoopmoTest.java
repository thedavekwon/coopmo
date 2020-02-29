package edu.cooper.ee.ece366.coopmo;

import org.springframework.web.reactive.function.client.WebClient;

public class CoopmoTest {
    private static WebClient client = WebClient.builder().baseUrl("http://localhost:8080").build();

    public static void main(String[] args) {
//        createUser("name1", "username1", "password1", "email1@gmail.com", "handle1");
//        getUserSize();
//        user1 = creatUser();
//        user2 = creatUser();
    }


    public static void createUser(String name, String username, String password, String email, String handle) {
        String response = client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/createUser/")
                        .queryParam("name", name)
                        .queryParam("username", username)
                        .queryParam("password", password)
                        .queryParam("email", email)
                        .queryParam("hanlde", handle)
                        .build())
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        System.out.println(response);
        //TODO (return userID)
    }

    public static void getUserSize() {
        String response = client.get()
                .uri("/user/getUserSize")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        System.out.println(response);
    }
}
