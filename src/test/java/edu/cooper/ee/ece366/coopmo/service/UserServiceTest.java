package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void addUser() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userService.addUser(user)).willReturn(user);
        User new_user = userService.addUser(user);
        Assert.assertEquals(new_user.getName(), "name1");
        Assert.assertEquals(new_user.getUsername(), "username1");
        Assert.assertEquals(new_user.getPassword(), "password1");
        Assert.assertEquals(new_user.getEmail(), "email1@gmail.com");
        Assert.assertEquals(new_user.getHandle(), "handle1");
    }

    @Test
    void check_if_taken_username() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userRepository.containsUsername(user.getUsername())).willReturn(true);
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle()));
    }

    @Test
    void check_if_taken_email() {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userRepository.containsEmail(user.getEmail())).willReturn(true);
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> {
            userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle());
        });
    }

    @Test
    void check_if_taken_handle() {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userRepository.containsHandle(user.getHandle())).willReturn(true);
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> {
            userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle());
        });
    }

    // editEmail and editHandle have same logic
    @Test
    void editUsername() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        String testId = "testId";
        String newUsername = "newUsername";
        given(userRepository.findById(testId)).willReturn(Optional.of(user));
        given(userRepository.containsUsername(newUsername)).willReturn(false);
        assertThat(userService.editUsername(testId, newUsername)).isTrue();
    }

    @Test
    void editUsername_duplicateUsername() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        String testId = "testId";
        String newUsername = "newUsername";
        given(userRepository.findById(testId)).willReturn(Optional.of(user));
        given(userRepository.containsUsername(newUsername)).willReturn(true);
        assertThat(userService.editUsername(testId, newUsername)).isFalse();
    }

    @Test
    void editUsername_InvalidId() throws BaseExceptionHandler.InValidFieldValueException {
        String testId = "testId";
        String newUsername = "newUsername";
        given(userRepository.findById(testId)).willReturn(Optional.empty());
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> {
            userService.editUsername(testId, newUsername);
        });
    }


    @Test
    void editProfile() {
    }

    @Test
    void cancelOutgoingFriendRequest() throws BaseExceptionHandler.InValidFieldValueException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        String user2ID = "user2ID";
        user1.addOutgoingFriendRequest(user2);
        user2.addIncomingFriendRequest(user1);
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2ID)).willReturn(Optional.of(user2));
        assertThat(userService.cancelOutgoingFriendRequest(user1ID, user2ID)).isEqualTo(0);
        assertThat(user1.getOutgoingFriendRequestSet().contains(user2)).isFalse();
        assertThat(user2.getIncomingFriendRequestSet().contains(user1)).isFalse();
    }

    @Test
    void acceptIncomingRequest() throws BaseExceptionHandler.FriendRequestDoesNotExistException, BaseExceptionHandler.InValidFieldValueException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        String user2ID = "user2ID";
        user1.addOutgoingFriendRequest(user2);
        user2.addIncomingFriendRequest(user1);
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2ID)).willReturn(Optional.of(user2));
        userService.acceptIncomingRequest(user2ID, user1ID);
        assertThat(user1.getFriendSet().contains(user2)).isTrue();
        assertThat(user2.getFriendSet().contains(user1)).isTrue();
    }

    @Test
    void acceptIncomingRequest_NoFriendRequest() throws BaseExceptionHandler.FriendRequestDoesNotExistException, BaseExceptionHandler.InValidFieldValueException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        String user2ID = "user2ID";
        user1.addOutgoingFriendRequest(user2);
        user2.addIncomingFriendRequest(user1);
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2ID)).willReturn(Optional.of(user2));
        assertThrows(BaseExceptionHandler.FriendRequestDoesNotExistException.class, () -> {
            userService.acceptIncomingRequest(user1ID, user2ID);
        });
        assertThat(user1.getFriendSet().contains(user2)).isFalse();
        assertThat(user2.getFriendSet().contains(user1)).isFalse();
    }

    @Test
    void sendOutRequest() throws BaseExceptionHandler.FriendRequestDoesNotExistException, BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.FriendRequestAlreadyExistException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        String user2ID = "user2ID";
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2ID)).willReturn(Optional.of(user2));
        userService.sendOutRequest(user1ID, user2ID);
        assertThat(user1.getOutgoingFriendRequestSet().contains(user2)).isTrue();
        assertThat(user2.getIncomingFriendRequestSet().contains(user1)).isTrue();
    }

    @Test
    void sendOutRequest_AlreadyFriend() throws BaseExceptionHandler.FriendRequestDoesNotExistException, BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.FriendRequestAlreadyExistException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        String user2ID = "user2ID";
        user1.addFriend(user2);
        user2.addFriend(user1);
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2ID)).willReturn(Optional.of(user2));
        assertThrows(BaseExceptionHandler.FriendRequestAlreadyExistException.class, () -> {
            userService.sendOutRequest(user1ID, user2ID);
        });
        assertThat(user1.getOutgoingFriendRequestSet().contains(user2)).isFalse();
        assertThat(user2.getIncomingFriendRequestSet().contains(user1)).isFalse();
    }

    @Test
    void getUserWithUsername() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        String testId = "testId";
        given(userRepository.getIdfromUsername(user.getUsername())).willReturn(testId);
        given(userRepository.findById(testId)).willReturn(Optional.of(user));
        User cur_user = userService.getUserWithUsername(user.getUsername(), user.getPassword());
        assertThat(cur_user).isNotNull();
    }

    @Test
    void acceptIncomingFriendRequest() {

    }

    @Test
    void receivedIncomingFriendRequest() {
    }

    @Test
    void acceptedOutgoingFriendRequest() {
    }

    @Test
    void sendOutgoingFriendRequest() {
    }

    @Test
    void sendOutRequestWithUsername() {
    }

    @Test
    void getUserBalance() {
    }

    @Test
    void getUserFriendSet() {
    }

    @Test
    void getOutgoingFriendRequestSet() {
    }

    @Test
    void getIncomingFriendRequestSet() {
    }

    @Test
    void getBankAccountSet() {
    }

    @Test
    void deleteFriend() {
    }

    @Test
    void addFriend() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        user1.addFriend(user2);
        assertThat(user1.getFriendSet().contains(user2)).isTrue();
    }

    @Test
    void checkValidUserId() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        String testId = "testId";
        given(userRepository.findById(testId)).willReturn(Optional.of(user));
        assertThat(userService.checkValidUserId(testId)).isEqualTo(user);
    }

    @Test
    void checkValidUserId_InvalidID() {
        String testId = "testId";
        given(userRepository.findById(testId)).willReturn(Optional.empty());
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> {
            userService.checkValidUserId(testId);
        });
    }
}