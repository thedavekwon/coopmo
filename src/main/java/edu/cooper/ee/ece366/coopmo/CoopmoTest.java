package edu.cooper.ee.ece366.coopmo;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.mikael.urlbuilder.UrlBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoopmoTest {
    private static HttpClient client = HttpClient.newHttpClient();

    public static void testFriends() throws IOException, InterruptedException {
        // Test: Creating two users
        System.out.println("Initial Test Create Two Users:\n----------------------------");
        String user1 = createUser("name1", "username1", "password1", "email1@gmail.com", "handle1");
        if (user1 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User1 ID: " + user1 + "\n");

        String user2 = createUser("name2", "username2", "password2", "email2@gmail.com", "handle2");
        if (user2 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User2 ID: " + user2 + "\n");

        // Test: Creating duplicate users
        System.out.println("Test for No duplicate users:\n----------------------------");
        System.out.println("Original User");
        String original = createUser("Original", "original", "password", "original@gmail.com", "superoriginal");
        if (original == null)
            System.out.println("Creating a new User failed");
        System.out.println("Original ID: " + original + "\n");

        //Test username
        System.out.println("Same username");
        String clone = createUser("clone", "original", "password", "clone@gmail.com", "imaclone");
        if (clone == null)
            System.out.println("Creating a new User failed");
        System.out.println("Clone ID: " + clone + "\n");

        // Test email
        System.out.println("Same email");
        String clone2 = createUser("clone2", "clone2", "password", "original@gmail.com", "imaclone2");
        if (clone2 == null)
            System.out.println("Creating a new User failed");
        System.out.println("Clone2 ID: " + clone2 + "\n");

        // Test handle
        System.out.println("Same handle");
        String clone3 = createUser("clone3", "clone3", "password", "clone3@gmail.com", "superoriginal");
        if (clone3 == null)
            System.out.println("Creating a new User failed");
        System.out.println("Clone3 ID: " + clone3 + "\n");

        // Test: sending Friend Request
        System.out.println("Test for Sending Friend Request:\n----------------------------");
        boolean ret = sendOutRequest(user1, user2);
        if (!ret) System.out.println("Sending Friend Request failed\n");

        // Check to see if sent Friend Request has been globally updated in relevant fields
        String user1OutgoingFriendRequest = getUserOutgoingFriendRequest(user1);
        if (user1OutgoingFriendRequest == null) System.out.println("Failed to get User's Outgoing Friend Requests");
        System.out.println("User1 OutgoingFriendRequests: " + user1OutgoingFriendRequest + "\n");

        String user2IncomingFriendRequest = getUserIncomingFriendRequest(user2);
        if (user2IncomingFriendRequest == null)
            System.out.println("Failed to get User's Incoming Friend Requests");
        System.out.println("User2 IncomingFriendRequests: " + user2IncomingFriendRequest + "\n");

        // Test: deleting Friend Request
        System.out.println("Test for cancelling friend request:\n----------------------------");
        String minh = createUser("Minh-Thai", "minhthai", "password", "minhtyufa@gmail.com", "Edge-Lord");
        if (original == null)
            System.out.println("Creating a new User failed");
        System.out.println("Minh-Thai ID: " + minh + "\n");

        String dan = createUser("Dan", "swoledan", "password", "danqian@gmail.com", "bigboi");
        if (original == null)
            System.out.println("Creating a new User failed");
        System.out.println("Dan ID: " + dan + "\n");

        ret = sendOutRequest(minh, dan);
        if (!ret)
            System.out.println("Sending Friend Request failed\n");

        String minhOutgoingFriendRequest = getUserOutgoingFriendRequest(minh);
        if (minhOutgoingFriendRequest == null) System.out.println("Failed to get User's Outgoing Friend Requests");
        System.out.println("Minh OutgoingFriendRequests: " + minhOutgoingFriendRequest + "\n");

        String danIncomingFriendRequest = getUserIncomingFriendRequest(dan);
        if (danIncomingFriendRequest == null) System.out.println("Failed to get User's Incoming Friend Requests");
        System.out.println("Dan IncomingFriendRequests: " + danIncomingFriendRequest + "\n");

        System.out.println("Cancelling Friend Request");
        ret = cancelFriendRequest(minh, dan);
        if (!ret) {
            System.out.println("Failed to cancel Friend Request");
        }

        minhOutgoingFriendRequest = getUserOutgoingFriendRequest(minh);
        if (minhOutgoingFriendRequest == null) System.out.println("Failed to get User's Outgoing Friend Requests");
        System.out.println("Minh OutgoingFriendRequests: " + minhOutgoingFriendRequest + "\n");

        danIncomingFriendRequest = getUserIncomingFriendRequest(dan);
        if (danIncomingFriendRequest == null)
            System.out.println("Failed to get User's Incoming Friend Requests");
        System.out.println("Dan IncomingFriendRequests: " + danIncomingFriendRequest + "\n");

        // Test: accepting Friend Request
        System.out.println("Test for Accepting Request users:\n----------------------------");
        System.out.println("User 2 accepting user 1's friend request");
        ret = acceptIncomingRequest(user2, user1);
        if (!ret) return;

        // Check to see if accepted Friend Request has been globally updated in relevant fields
        String user1FriendList = getUserFriendList(user1);
        if (user1FriendList == null) return;
        System.out.println("User1 FriendList: " + user1FriendList);

        String user2FriendList = getUserFriendList(user2);
        if (user2FriendList == null) return;
        System.out.println("User2 FriendList: " + user2FriendList);

        System.out.println("\nTest for deleting friend:\n----------------------------");
        System.out.println("User1 deleting User2 as a friend");
        if (!deleteFriend(user1, user2)) return;

        user1FriendList = getUserFriendList(user1);
        if (user1FriendList == null) return;
        System.out.println("User1 FriendList: " + user1FriendList);

        user2FriendList = getUserFriendList(user2);
        if (user2FriendList == null) return;
        System.out.println("User2 FriendList: " + user2FriendList);

        System.out.println("\nTest for editing profile:\n----------------------------");
        System.out.println("Trying to change Minh profile to match user1's");
        if (editProfile(minh, "name1", "username1", "password1",
                "email1@gmail.com", "handle1")) return;

        System.out.println("Showing Minh user through username and password");
        if (!getUserWithUsername("minhthai", "password1")) return;

        System.out.println("\nChanging Profile to new things");
        if (!editProfile(minh, "newname", "newuname", "newpassword",
                "newemail@gmail.com", "newhandle")) return;
        System.out.println("Showing Minh user through username and password");
        if (!getUserWithUsername("newuname", "newpassword")) return;
        System.out.println("Creating user3...");

        String user5 = createUser("name5", "username5", "password5", "email5@gmail.com", "handle5");
        if (user5 == null) return; // failed;
        System.out.println("User5 ID: " + user5);

        System.out.println("Creating user4...");

        String user6 = createUser("name6", "username6", "password6", "email6@gmail.com", "handle6");
        if (user6 == null) return; // failed;
        System.out.println("User4 ID: " + user6);

        System.out.println("Test for Duplicate Friend Request Send Failure:\n----------------------------");
        checkDoubleAddFails(user5, user6);

        System.out.println("Test that accepting the same friend request twice succeeds, then fails:\n----------------------------");
        checkDoubleAcceptFails(user5, user6);

        System.out.println("Test that two users sending each other friend requests will automatically be added::\n----------------------------");
        checkAcceptRequest(user5, user6);
    }

    public static void testPayment() throws IOException, InterruptedException {
        System.out.println("\nTest for different type of payment:\n----------------------------");
        System.out.println("user1, user2, user3, user4, and user5");
        System.out.println("user1 is friend with user2 and user3");
        System.out.println("user2 is friend with user1");
        System.out.println("user3 is friend with user1");
        System.out.println("user4 is friend with user5");
        System.out.println("user5 is friend with user4");

        System.out.println("Initial Test Create Two Users:\n----------------------------");
        String user1 = createUser("n1", "u1", "p1", "e1@gmail.com", "h1");
        if (user1 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User1 ID: " + user1 + "\n");

        String user2 = createUser("n2", "u2", "p2", "e2@gmail.com", "h2");
        if (user2 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User2 ID: " + user2 + "\n");

        String user3 = createUser("n3", "u3", "p3", "e3@gmail.com", "h3");
        if (user3 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User1 ID: " + user3 + "\n");

        String user4 = createUser("n4", "u4", "p4", "e4@gmail.com", "h4");
        if (user4 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User2 ID: " + user4 + "\n");

        String user5 = createUser("n5", "u5", "p5", "e5@gmail.com", "h5");
        if (user5 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User5 ID: " + user5 + "\n");


        boolean ret = sendOutRequest(user1, user2);
        if (!ret) {
            System.out.println("Sending Friend Request failed\n");
            return;
        }
        ret = acceptIncomingRequest(user2, user1);
        if (!ret) {
            System.out.println("Sending Friend Request failed\n");
            return;
        }
        ret = sendOutRequest(user1, user3);
        if (!ret) {
            System.out.println("Sending Friend Request failed\n");
            return;
        }
        ret = acceptIncomingRequest(user3, user1);
        if (!ret) {
            System.out.println("Sending Friend Request failed\n");
            return;
        }
        ret = sendOutRequest(user4, user5);
        if (!ret) {
            System.out.println("Sending Friend Request failed\n");
            return;
        }
        ret = acceptIncomingRequest(user5, user4);
        if (!ret) {
            System.out.println("Sending Friend Request failed\n");
            return;
        }

        System.out.println("Test for Friend Requests:\n----------------------------");
        if (getAllFriendLists(user1, user2, user3, user4, user5)) return;

        // Test: creating two BankAccounts
        System.out.println("Test for creating two BankAccounts:\n----------------------------");
        String user1BankAccount = createBankAccount(user1, "999999999", "9000");
        if (user1BankAccount == null) return;
        System.out.println("User1 BankAccount ID: " + user1BankAccount);

        String user2BankAccount = createBankAccount(user2, "999999998", "9000");
        if (user2BankAccount == null) return;
        System.out.println("User2 BankAccount ID: " + user2BankAccount);

        // Adding cash to first BankAccount
        System.out.println("Test for adding cash to first bank account:\n----------------------------");
        ret = createCash(user1, user1BankAccount, "9000", "IN");
        if (!ret) return;

        // Check to see created Cash has been globally updated in relevant fields
        if (getAllUserBalance(user1, user2, user3, user4, user5)) return;

        // Test: creating public Payment between two users
        System.out.println("Test for sending a payment:\n----------------------------");
        System.out.println("Sending a public payment of 3000 from user 1 to user 2");
        ret = createPayment(user1, user2, "3000", "PUBLIC");
        if (!ret) return;

        // Check to see if public Payment has updated relevant fields
        if (getAllUserBalance(user1, user2, user3, user4, user5)) return;


        // Test for displaying public payments
        System.out.println("Test for displaying pubic payments:\n----------------------------");
        String publicPaymentList = getLatestPublicPayment("10");
        if (publicPaymentList == null) return;
        System.out.println("PublicPaymentList: " + publicPaymentList);

        // Test: creating private Payment between two users
        System.out.println("Test for creating Private payment between users:\n----------------------------");
        System.out.println("Sending a private payment of 3000 from user 1 to user 2");
        ret = createPayment(user1, user2, "3000", "PRIVATE");
        if (!ret) return;

        // Check to see if private Payment has updated relevant fields
        if (getAllUserBalance(user1, user2, user3, user4, user5)) return;


        System.out.println("Test for displaying private payments:\n----------------------------");
        if (getAllPrivatePayment(user1, user2, user3, user4, user5)) return;

        // Test: creating friend Payment between two users
        System.out.println("Test for creating a Friend payment of 3000 between two users:\n----------------------------");
        System.out.println("Sending a friend payment of 3000 from user 1 to user 2");
        ret = createPayment(user1, user2, "3000", "FRIEND");
        if (!ret) return;
        System.out.println("Sending a friend payment of 3000 from user 2 to user 4");
        ret = createPayment(user2, user4, "3000", "FRIEND");
        if (!ret) return;
        System.out.println("Expected friendPaymentList");
        System.out.println("user1: [PUBLIC user1->user2, PRIVATE user1->user2, FRIEND user1->user2, FRIEND user2->user4]");
        System.out.println("user2: [PUBLIC user1->user2, PRIVATE user1->user2, FRIEND user1->user2, FRIEND user2->user4]");
        System.out.println("user3: [PUBLIC user1->user2, FRIEND user1->user2]");
        System.out.println("user4: [FRIEND user2->user4]");
        System.out.println("user5: [FRIEND user2->user4]");
        // Check to see if friend Payment has updated relevant fields
        if (getAllUserBalance(user1, user2, user3, user4, user5)) return;

        System.out.println("Test for displaying friend payments:\n----------------------------");
        if (getAllFriendPaymentList(user1, user2, user3, user4, user5)) return;
    }

    private static boolean getAllFriendLists(String user1, String user2, String user3, String user4, String user5) throws IOException, InterruptedException {
        String user1FriendList = getUserFriendList(user1);
        if (user1FriendList == null) return true;
        System.out.println("User1 FriendList: " + user1FriendList);

        String user2FriendList = getUserFriendList(user2);
        if (user2FriendList == null) return true;
        System.out.println("User2 FriendList: " + user2FriendList);

        String user3FriendList = getUserFriendList(user3);
        if (user3FriendList == null) return true;
        System.out.println("User3 FriendList: " + user3FriendList);

        String user4FriendList = getUserFriendList(user4);
        if (user4FriendList == null) return true;
        System.out.println("User4 FriendList: " + user4FriendList);

        String user5FriendList = getUserFriendList(user5);
        if (user5FriendList == null) return true;
        System.out.println("User5 FriendList: " + user5FriendList);
        return false;
    }

    private static boolean getAllPrivatePayment(String user1, String user2, String user3, String user4, String user5) throws IOException, InterruptedException {
        String user1PrivatePaymentList = getLatestPrivatePayment(user1, "10");
        if (user1PrivatePaymentList == null) return true;
        System.out.println("User1 PrivatePaymentList: " + user1PrivatePaymentList);

        String user2PrivatePaymentList = getLatestPrivatePayment(user2, "10");
        if (user2PrivatePaymentList == null) return true;
        System.out.println("User2 PrivatePaymentList: " + user2PrivatePaymentList);

        String user3PrivatePaymentList = getLatestPrivatePayment(user3, "10");
        if (user3PrivatePaymentList == null) return true;
        System.out.println("User3 PrivatePaymentList: " + user3PrivatePaymentList);

        String user4PrivatePaymentList = getLatestPrivatePayment(user4, "10");
        if (user4PrivatePaymentList == null) return true;
        System.out.println("User4 PrivatePaymentList: " + user4PrivatePaymentList);

        String user5PrivatePaymentList = getLatestPrivatePayment(user5, "10");
        if (user5PrivatePaymentList == null) return true;
        System.out.println("User5 PrivatePaymentList: " + user5PrivatePaymentList);
        return false;
    }

    private static boolean getAllFriendPaymentList(String user1, String user2, String user3, String user4, String user5) throws IOException, InterruptedException {
        String user1FriendPaymentList = getLatestFriendPayment(user1, "10");
        if (user1FriendPaymentList == null) return true;
        System.out.println("User1 FriendPaymentList: " + user1FriendPaymentList);

        String user2FriendPaymentList = getLatestFriendPayment(user2, "10");
        if (user2FriendPaymentList == null) return true;
        System.out.println("User2 FriendPaymentList: " + user2FriendPaymentList);

        String user3FriendPaymentList = getLatestFriendPayment(user3, "10");
        if (user3FriendPaymentList == null) return true;
        System.out.println("User3 FriendPaymentList: " + user3FriendPaymentList);

        String user4FriendPaymentList = getLatestFriendPayment(user4, "10");
        if (user4FriendPaymentList == null) return true;
        System.out.println("User4 FriendPaymentList: " + user4FriendPaymentList);

        String user5FriendPaymentList = getLatestFriendPayment(user5, "10");
        if (user5FriendPaymentList == null) return true;
        System.out.println("User5 FriendPaymentList: " + user5FriendPaymentList);
        return false;
    }

    private static boolean getAllUserBalance(String user1, String user2, String user3, String user4, String user5) throws IOException, InterruptedException {
        Long user1Balance = getUserBalance(user1);
        if (user1Balance == null) return true;
        System.out.println("User1 Balance: " + user1Balance);

        Long user2Balance = getUserBalance(user2);
        if (user2Balance == null) return true;
        System.out.println("User2 Balance: " + user2Balance);

        Long user3Balance = getUserBalance(user3);
        if (user3Balance == null) return true;
        System.out.println("User3 Balance: " + user3Balance);

        Long user4Balance = getUserBalance(user4);
        if (user4Balance == null) return true;
        System.out.println("User4 Balance: " + user4Balance);

        Long user5Balance = getUserBalance(user5);
        if (user5Balance == null) return true;
        System.out.println("User5 Balance: " + user5Balance);
        return false;
    }

    public static String createUser(String name, String username, String password, String email, String handle) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/createUser")
                .addParameter("name", name)
                .addParameter("username", username)
                .addParameter("password", password)
                .addParameter("email", email)
                .addParameter("handle", handle)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload == null)
                return null;
            if (messagePayload.isJsonObject()) {
                JsonElement user = messagePayload.getAsJsonObject().get("user");
                if (user.getAsJsonObject().isJsonObject()) {
                    JsonElement id = user.getAsJsonObject().get("id");
                    return id.getAsString();
                }
            }
        }
        return null;
    }

    public static boolean sendOutRequest(String userId, String friendId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/sendOutRequest")
                .addParameter("userId", userId)
                .addParameter("friendId", friendId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        System.out.println(jsonObject.get("message").getAsString());
        return response.statusCode() == 200;
    }

    public static String getUserOutgoingFriendRequest(String userId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/getUserOutgoingFriendRequest")
                .addParameter("userId", userId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload.isJsonObject()) {
                JsonElement requestList = messagePayload.getAsJsonObject().get("userOutgoingFriendRequest");
                return requestList.getAsJsonArray().toString();
            }
        }
        return null;
    }

    public static String getUserIncomingFriendRequest(String userId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/getUserIncomingFriendRequest")
                .addParameter("userId", userId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload.isJsonObject()) {
                JsonElement requestList = messagePayload.getAsJsonObject().get("userIncomingFriendRequestList");
                return requestList.getAsJsonArray().toString();
            }
        }
        return null;
    }

    private static boolean acceptIncomingRequest(String userId, String friendId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/acceptIncomingRequest")
                .addParameter("userId", userId)
                .addParameter("friendId", friendId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        System.out.println(jsonObject.get("message").getAsString());
        return response.statusCode() == 200;
    }

    public static String getUserFriendList(String userId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/getUserFriendList")
                .addParameter("userId", userId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload.isJsonObject()) {
                JsonElement friendList = messagePayload.getAsJsonObject().get("friendList");
                return friendList.getAsJsonArray().toString();
            }
        }
        return null;
    }

    public static String createBankAccount(String userId, String routingNumber, String balance) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("bank/createBankAccount")
                .addParameter("userId", userId)
                .addParameter("routingNumber", routingNumber)
                .addParameter("balance", balance)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload.isJsonObject()) {
                JsonElement bankAccount = messagePayload.getAsJsonObject().get("bankAccount");
                if (bankAccount.getAsJsonObject().isJsonObject()) {
                    JsonElement id = bankAccount.getAsJsonObject().get("id");
                    return id.getAsString();
                }
            }
        }
        return null;
    }

    public static boolean createCash(String userId, String bankAccountId, String amount, String type) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("cash/createCash")
                .addParameter("userId", userId)
                .addParameter("bankAccountId", bankAccountId)
                .addParameter("amount", amount)
                .addParameter("type", type)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        System.out.println(jsonObject.get("message").getAsString());
        return response.statusCode() == 200;
    }

    public static boolean createPayment(String fromUserId, String toUserId, String amount, String type) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("pay/createPayment")
                .addParameter("fromUserId", fromUserId)
                .addParameter("toUserId", toUserId)
                .addParameter("amount", amount)
                .addParameter("type", type)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        System.out.println(jsonObject.get("message").getAsString());
        return response.statusCode() == 200;
    }

    public static Long getUserBalance(String userId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/getUserBalance")
                .addParameter("userId", userId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            return messagePayload.getAsLong();
        }
        return null;
    }

    public static boolean editProfile(String userId, String newName, String newUsername,
                                      String newPassword, String newEmail, String newHandle) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/editProfile")
                .addParameter("userId", userId)
                .addParameter("newName", newName)
                .addParameter("newUsername", newUsername)
                .addParameter("newPassword", newPassword)
                .addParameter("newEmail", newEmail)
                .addParameter("newHandle", newHandle)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        System.out.println(jsonObject.get("message").getAsString());
        return response.statusCode() == 200;
    }

    public static boolean getUserWithUsername(String username, String password) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/getUserWithUsername")
                .addParameter("username", username)
                .addParameter("password", password)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            System.out.println(jsonObject.get("message").getAsString());
            if (response.statusCode() == 200) {
                JsonElement messagePayloadBuffer = jsonObject.get("messagePayload");
                if (messagePayloadBuffer.isJsonObject()) {
                    JsonObject user = messagePayloadBuffer.getAsJsonObject().get("user").getAsJsonObject();
                    System.out.println("User ID: " + user.get("id").getAsString());
                    System.out.println("Username: " + user.get("username").getAsString());
                    System.out.println("Password: " + user.get("password").getAsString());
                    System.out.println("Email: " + user.get("email").getAsString());
                    System.out.println("Handle: " + user.get("handle").getAsString());
                    System.out.println("Balance: " + user.get("balance").getAsString());
                }
            }
        }
        return response.statusCode() == 200;
    }

    public static boolean deleteFriend(String userId, String friendId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/deleteFriend")
                .addParameter("userId", userId)
                .addParameter("friendId", friendId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        System.out.println(jsonObject.get("message").getAsString());
        return response.statusCode() == 200;
    }

    public static boolean cancelFriendRequest(String userId, String friendId) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/cancelOutgoingFriendRequest")
                .addParameter("userId", userId)
                .addParameter("friendId", friendId)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        System.out.println(jsonObject.get("message").getAsString());
        return response.statusCode() == 200;
    }

    // Payment Record Stuff
    public static String getLatestPublicPayment(String n) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("pay/getLatestPublicPayment")
                .addParameter("n", n)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload.isJsonObject()) {
                JsonElement publicPaymentList = messagePayload.getAsJsonObject().get("LatestPublicPayment");
                System.out.println("size: " + publicPaymentList.getAsJsonArray().size());
                return publicPaymentList.getAsJsonArray().toString();
            }
        }
        return null;
    }

    public static String getLatestPrivatePayment(String userId, String n) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("pay/getLatestPrivatePayment")
                .addParameter("userId", userId)
                .addParameter("n", n)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload.isJsonObject()) {
                JsonElement privatePaymentList = messagePayload.getAsJsonObject().get("LatestPrivatePayment");
                System.out.println("size: " + privatePaymentList.getAsJsonArray().size());
                return privatePaymentList.getAsJsonArray().toString();
            }
        }
        return null;
    }

    public static String getLatestFriendPayment(String userId, String n) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("pay/getLatestFriendPayment")
                .addParameter("userId", userId)
                .addParameter("n", n)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayload = jsonObject.get("messagePayload");
            System.out.println(jsonObject.get("message").getAsString());
            if (messagePayload.isJsonObject()) {
                JsonElement friendPaymentList = messagePayload.getAsJsonObject().get("LatestFriendPayment");
                System.out.println("size: " + friendPaymentList.getAsJsonArray().size());
                return friendPaymentList.getAsJsonArray().toString();
            }
        }
        return null;
    }

    public static void checkDoubleAddFails(String user1ID, String user2ID) throws IOException, InterruptedException {
        System.out.println("Sending duplicate friend request from User3 to User4");

        boolean ret = sendOutRequest(user1ID, user2ID);
        System.out.println("Request from User3 to User4 succeeded: " + ret);

        ret = sendOutRequest(user1ID, user2ID);
        System.out.println("Request from User3 to User4 succeeded: " + ret);

        System.out.println("Cancelling friend request");

        ret = cancelFriendRequest(user1ID, user2ID);
        System.out.println("Cancelling request succeeded: " + ret);

        String outgoingFriendRequest = getUserOutgoingFriendRequest(user1ID);
        if (outgoingFriendRequest == null) System.out.println("Failed to get User3's Outgoing Friend Requests");
        System.out.println("User3 OutgoingFriendRequests: " + outgoingFriendRequest + "\n");
    }

    public static void checkDoubleAcceptFails(String user1ID, String user2ID) throws IOException, InterruptedException {

        System.out.println("Sending friend request from user3 to user4");
        boolean ret = sendOutRequest(user1ID, user2ID);

        ret = acceptIncomingRequest(user2ID, user1ID);
        System.out.println("Accepting request succeeded: " + ret);
        ret = acceptIncomingRequest(user2ID, user1ID);
        System.out.println("Accepting request succeeded: " + ret);

        deleteFriend(user1ID, user2ID);
        System.out.println("Deleted friend user4 from user3");
    }

    public static void checkAcceptRequest(String user1ID, String user2ID) throws IOException, InterruptedException {
        System.out.println("If a user sends a friend request to another user that has already sent them a friend request, it will automatically accept.");

        boolean ret = sendOutRequest(user1ID, user2ID);
        System.out.println("Request from user3 to user4 succeeded: " + ret);

        ret = sendOutRequest(user2ID, user1ID);
        System.out.println("Request from user4 to user3 succeeded: " + ret);

        System.out.println("Getting User4 friend list:");
        String user5FriendList = getUserFriendList(user2ID);
        if (user5FriendList == null) return;
        System.out.println("User4 FriendList: " + user5FriendList);
    }
}
