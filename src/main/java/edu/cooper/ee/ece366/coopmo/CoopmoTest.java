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

    public static void run() throws IOException, InterruptedException {
        // Test: Creating two users
        String user1 = createUser("name1", "username1", "password1", "email1@gmail.com", "handle1");
        if (user1 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User1 ID: " + user1 + "\n");

        String user2 = createUser("name2", "username2", "password2", "email2@gmail.com", "handle2");
        if (user2 == null)
            System.out.println("Creating a new User failed");
        System.out.println("User2 ID: " + user2 + "\n");

        // Test: Creating duplicate users
        String original = createUser("Original", "original", "password", "original@gmail.com", "superoriginal");
        if (original == null)
            System.out.println("Creating a new User failed");
        System.out.println("Original ID: " + original + "\n");

        //Test username
        String clone = createUser("clone", "original", "password", "clone@gmail.com", "imaclone");
        if (clone == null)
            System.out.println("Creating a new User failed");
        System.out.println("Clone ID: " + clone + "\n");

        // Test email
        String clone2 = createUser("clone2", "clone2", "password", "original@gmail.com", "imaclone2");
        if (clone2 == null)
            System.out.println("Creating a new User failed");
        System.out.println("Clone2 ID: " + clone2 + "\n");

        // Test handle
        String clone3 = createUser("clone3", "clone3", "password", "clone3@gmail.com", "superoriginal");
        if (clone3 == null)
            System.out.println("Creating a new User failed");
        System.out.println("Clone3 ID: " + clone3 + "\n");

        // Test: sending Friend Request
        boolean ret = sendOutRequest(user1, user2);
        if (!ret)
            System.out.println("Sending Friend Request failed\n");

        // Check to see if sent Friend Request has been globally updated in relevant fields
        String user1OutgoingFriendRequest = getUserOutgoingFriendRequest(user1);
        if (user1OutgoingFriendRequest == null)
            System.out.println("Failed to get User's Outgoing Friend Requests");
        System.out.println("User1 OutgoingFriendRequests: " + user1OutgoingFriendRequest + "\n");

        String user2IncomingFriendRequest = getUserIncomingFriendRequest(user2);
        if (user2IncomingFriendRequest == null)
            System.out.println("Failed to get User's Incoming Friend Requests");
        System.out.println("User2 IncomingFriendRequests: " + user2IncomingFriendRequest + "\n");

        // Test: deleting Friend Request
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
            System.out.println("Canceling Friend Request failed\n");

        String minhOutgoingFriendRequest = getUserOutgoingFriendRequest(minh);
        if (minhOutgoingFriendRequest == null)
            System.out.println("Failed to get User's Outgoing Friend Requests");
        System.out.println("Minh OutgoingFriendRequests: " + minhOutgoingFriendRequest + "\n");

        String danIncomingFriendRequest = getUserIncomingFriendRequest(dan);
        if (danIncomingFriendRequest == null)
            System.out.println("Failed to get User's Incoming Friend Requests");
        System.out.println("Dan IncomingFriendRequests: " + danIncomingFriendRequest + "\n");

        System.out.println("Cancelling Friend Request");
        ret = cancelFriendRequest(minh, dan);
        if (!ret) {
            System.out.println("Failed to cancel Friend Request");
        }

        minhOutgoingFriendRequest = getUserOutgoingFriendRequest(minh);
        if (minhOutgoingFriendRequest == null)
            System.out.println("Failed to get User's Outgoing Friend Requests");
        System.out.println("Minh OutgoingFriendRequests: " + minhOutgoingFriendRequest + "\n");

        danIncomingFriendRequest = getUserIncomingFriendRequest(dan);
        if (danIncomingFriendRequest == null)
            System.out.println("Failed to get User's Incoming Friend Requests");
        System.out.println("Dan IncomingFriendRequests: " + danIncomingFriendRequest + "\n");

        // Test: accepting Friend Request
        ret = acceptIncomingRequest(user2, user1);
        if (!ret) return;

        // Check to see if accepted Friend Request has been globally updated in relevant fields
        String user1FriendList = getUserFriendList(user1);
        if (user1FriendList == null) return;
        System.out.println("User1 FriendList: " + user1FriendList);

        String user2FriendList = getUserFriendList(user2);
        if (user2FriendList == null) return;
        System.out.println("User2 FriendList: " + user2FriendList);

        // Test: creating two BankAccounts
        String user1BankAccount = createBankAccount(user1, "999999999", "9000");
        if (user1BankAccount == null) return;
        System.out.println("User1 BankAccount ID: " + user1BankAccount);

        String user2BankAccount = createBankAccount(user2, "999999998", "9000");
        if (user2BankAccount == null) return;
        System.out.println("User2 BankAccount ID: " + user2BankAccount);

        // Adding cash to first BankAccount
        ret = createCash(user1, user1BankAccount, "9000", "IN");
        if (!ret) return;

        // Check to see created Cash has been globally updated in relevant fields
        Long user1Balance = getUserBalance(user1);
        if (user1Balance == null) return;
        System.out.println("User1 Balance: " + user1Balance);

        Long user2Balance = getUserBalance(user2);
        if (user2Balance == null) return;
        System.out.println("User2 Balance: " + user2Balance);

        // Test: creating public Payment between two users
        ret = createPayment(user1, user2, "3000", "PUBLIC");
        if (!ret) return;

        // Check to see if public Payment has updated relevant fields
        user1Balance = getUserBalance(user1);
        if (user1Balance == null) return;
        System.out.println("User1 Balance: " + user1Balance);

        user2Balance = getUserBalance(user2);
        if (user2Balance == null) return;
        System.out.println("User2 Balance: " + user2Balance);

        String user1PublicPaymentList = getLatestPublicPayment("10");
        if (user1PublicPaymentList == null) return;
        System.out.println("User1 PublicPaymentList: " + user1PublicPaymentList);

        String user2PublicPaymentList = getLatestPublicPayment("10");
        if (user2PublicPaymentList == null) return;
        System.out.println("User2 PublicPaymentList: " + user2PublicPaymentList);

        // Test: creating private Payment between two users
        ret = createPayment(user1, user2, "3000", "PRIVATE");
        if (!ret) return;

        // Check to see if private Payment has updated relevant fields
        user1Balance = getUserBalance(user1);
        if (user1Balance == null) return;
        System.out.println("User1 Balance: " + user1Balance);

        user2Balance = getUserBalance(user2);
        if (user2Balance == null) return;
        System.out.println("User2 Balance: " + user2Balance);

        String user1PrivatePaymentList = getLatestPrivatePayment(user1, "10");
        if (user1PrivatePaymentList == null) return;
        System.out.println("User1 PrivatePaymentList: " + user1PrivatePaymentList);

        String user2PrivatePaymentList = getLatestPrivatePayment(user2, "10");
        if (user2PrivatePaymentList == null) return;
        System.out.println("User2 PrivatePaymentList: " + user2PrivatePaymentList);
        // Test: creating friend Payment between two users
        ret = createPayment(user1, user2, "3000", "FRIEND");
        if (!ret) return;

        // Check to see if friend Payment has updated relevant fields
        user1Balance = getUserBalance(user1);
        if (user1Balance == null) return;
        System.out.println("User1 Balance: " + user1Balance);

        user2Balance = getUserBalance(user2);
        if (user2Balance == null) return;
        System.out.println("User2 Balance: " + user2Balance);

        String user1FriendPaymentList = getLatestFriendPayment(user1, "10");
        if (user1FriendPaymentList == null) return;
        System.out.println("User1 FriendPaymentList: " + user1FriendPaymentList);

        String user2FriendPaymentList = getLatestFriendPayment(user2, "10");
        if (user2FriendPaymentList == null) return;
        System.out.println("User2 FriendPaymentList: " + user2FriendPaymentList);

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

    public static boolean showUser(String username, String password) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .withPath("user/editProfile")
                .addParameter("username", username)
                .addParameter("password", password)
                .toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(response.body());
        if (jsonTree.isJsonObject() ) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement messagePayloadBuffer = jsonObject.get("messagePayload");
            JsonObject messagePayload = messagePayloadBuffer.getAsJsonObject();
            System.out.println(jsonObject.get("message").getAsString());
            if(response.statusCode() == 200) {
                System.out.println("User ID: " + messagePayload.get("id").getAsString());
                System.out.println("Username: " + messagePayload.get("username"));
                System.out.println("Password: " + messagePayload.get("password"));
                System.out.println("Email: " + messagePayload.get("email"));
                System.out.println("Handle: " + messagePayload.get("handle"));
                System.out.println("Balance: " + messagePayload.get("balance"));
            }
        }
        return response.statusCode()== 200;
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
                .withPath("user/cancelFriendRequest")
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
                return friendPaymentList.getAsJsonArray().toString();
            }
        }
        return null;
    }

}
