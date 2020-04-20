package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        passwordEncoder = mock(PasswordEncoder.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void addUser() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userService.addUser(user)).willReturn(user);
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());
        User new_user = userService.addUser(user);
        Assert.assertEquals(new_user.getName(), "name1");
        Assert.assertEquals(new_user.getUsername(), "username1");
        Assert.assertEquals(new_user.getEmail(), "email1@gmail.com");
        Assert.assertEquals(new_user.getHandle(), "handle1");
    }

    @Test
    void check_if_taken_username() {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userRepository.containsUsername(user.getUsername())).willReturn(true);
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle()));
    }

    @Test
    void check_if_taken_email() {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userRepository.containsEmail(user.getEmail())).willReturn(true);
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle()));
    }

    @Test
    void check_if_taken_handle() {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        given(userRepository.containsHandle(user.getHandle())).willReturn(true);
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> userService.check_if_taken(user.getUsername(), user.getEmail(), user.getHandle()));
    }

    // editEmail and editHandle have same logic
    @Test
    void editUsername() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        String testId = "testId";
        String newUsername = "newUsername";
        given(userRepository.findById(testId)).willReturn(Optional.of(user));
        given(userRepository.containsUsername(newUsername)).willReturn(false);
        assertTrue(userService.editUsername(testId, newUsername));
    }

    @Test
    void editUsername_duplicateUsername() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        String testId = "testId";
        String newUsername = "newUsername";
        given(userRepository.findById(testId)).willReturn(Optional.of(user));
        given(userRepository.containsUsername(newUsername)).willReturn(true);
        assertFalse(userService.editUsername(testId, newUsername));
    }

    @Test
    void editUsername_InvalidId() {
        String testId = "testId";
        String newUsername = "newUsername";
        given(userRepository.findById(testId)).willReturn(Optional.empty());
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> userService.editUsername(testId, newUsername));
    }


    @Test
    void editProfile() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        userService.editProfile(user1ID, user2.getName(), user2.getUsername(), user2.getPassword(), user2.getEmail(), user2.getHandle());
        assertSame(user1.getName(), user2.getName());
        assertSame(user1.getUsername(), user2.getUsername());
        assertSame(user1.getPassword(), user2.getPassword());
        assertSame(user1.getEmail(), user2.getEmail());
        assertSame(user1.getHandle(), user2.getHandle());
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
        assertFalse(user1.getOutgoingFriendRequestSet().contains(user2));
        assertFalse(user2.getIncomingFriendRequestSet().contains(user1));
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
        assertTrue(user1.getFriendSet().contains(user2));
        assertTrue(user2.getFriendSet().contains(user1));
    }

    @Test
    void acceptIncomingRequest_NoFriendRequest() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        String user2ID = "user2ID";
        user1.addOutgoingFriendRequest(user2);
        user2.addIncomingFriendRequest(user1);
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2ID)).willReturn(Optional.of(user2));
        assertThrows(BaseExceptionHandler.FriendRequestDoesNotExistException.class, () -> userService.acceptIncomingRequest(user1ID, user2ID));
        assertFalse(user1.getFriendSet().contains(user2));
        assertFalse(user2.getFriendSet().contains(user1));
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
        assertTrue(user1.getOutgoingFriendRequestSet().contains(user2));
        assertTrue(user2.getIncomingFriendRequestSet().contains(user1));
    }

    @Test
    void sendOutRequest_AlreadyFriend() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1ID = "user1ID";
        String user2ID = "user2ID";
        user1.addFriend(user2);
        user2.addFriend(user1);
        given(userRepository.findById(user1ID)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2ID)).willReturn(Optional.of(user2));
        assertThrows(BaseExceptionHandler.FriendRequestAlreadyExistException.class, () -> userService.sendOutRequest(user1ID, user2ID));
        assertFalse(user1.getOutgoingFriendRequestSet().contains(user2));
        assertFalse(user2.getIncomingFriendRequestSet().contains(user1));
    }

    @Test
    void getUserWithUsername() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        String testId = "testId";
        given(userRepository.getIdFromUsername(user.getUsername())).willReturn(testId);
        given(userRepository.findById(testId)).willReturn(Optional.of(user));
        User cur_user = userService.getUserWithUsername(user.getUsername(), user.getPassword());
        assertNotNull(cur_user);
    }

    @Test
    void receivedIncomingFriendRequest_AlreadyFriend() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        user1.addFriend(user2);
        user2.addFriend(user1);
        userService.receivedIncomingFriendRequest(user1, user2);
        assertFalse(user1.getOutgoingFriendRequestSet().contains(user2));
        assertFalse(user2.getIncomingFriendRequestSet().contains(user1));
    }

    @Test
    void acceptedOutgoingFriendRequest_NoRequest() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        userService.acceptedOutgoingFriendRequest(user1, user2);
        assertFalse(user1.getFriendSet().contains(user2));
        assertFalse(user2.getFriendSet().contains(user1));
    }

    @Test
    void acceptedOutgoingFriendRequest() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        user1.addOutgoingFriendRequest(user2);
        user2.addIncomingFriendRequest(user1);
        userService.acceptedOutgoingFriendRequest(user1, user2);
        assertTrue(user1.getFriendSet().contains(user2));
    }

    @Test
    void sendOutgoingFriendRequest_AlreadyFriend() throws BaseExceptionHandler.FriendRequestDoesNotExistException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        user1.addFriend(user2);
        user2.addFriend(user1);
        userService.sendOutgoingFriendRequest(user1, user2);
        assertFalse(user1.getOutgoingFriendRequestSet().contains(user2));
        assertFalse(user2.getIncomingFriendRequestSet().contains(user1));
    }

    @Test
    void sendOutgoingFriendRequest_IncomingRequestAlreadyExist() throws BaseExceptionHandler.FriendRequestDoesNotExistException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        user1.addIncomingFriendRequest(user2);
        user2.addOutgoingFriendRequest(user1);
        userService.sendOutgoingFriendRequest(user1, user2);
        assertTrue(user1.getFriendSet().contains(user2));
    }

    @Test
    void sendOutgoingFriendRequest() throws BaseExceptionHandler.FriendRequestDoesNotExistException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        userService.sendOutgoingFriendRequest(user1, user2);
        assertTrue(user1.getOutgoingFriendRequestSet().contains(user2));
    }

    @Test
    void getUserBalance() throws BaseExceptionHandler.InValidFieldValueException {
        User user = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        long balance = 1000;
        user.incrementBalance(balance);
        String userId = "userId";
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        assertEquals(userService.getUserBalance(userId), balance);
    }

    @Test
    void deleteFriend() throws BaseExceptionHandler.InValidFieldValueException {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        String user1Id = "user1Id";
        String user2Id = "user2Id";
        user1.addFriend(user2);
        user2.addFriend(user1);
        given(userRepository.findById(user1Id)).willReturn(Optional.of(user1));
        given(userRepository.findById(user2Id)).willReturn(Optional.of(user2));
        userService.deleteFriend(user1Id, user2Id);
        assertFalse(user1.getFriendSet().contains(user2));
        assertFalse(user2.getFriendSet().contains(user1));
    }

    @Test
    void addFriend() {
        User user1 = new User("name1", "username1", "password1", "email1@gmail.com", "handle1");
        User user2 = new User("name2", "username2", "password2", "email2@gmail.com", "handle2");
        user1.addFriend(user2);
        assertTrue(user1.getFriendSet().contains(user2));
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
        assertThrows(BaseExceptionHandler.InValidFieldValueException.class, () -> userService.checkValidUserId(testId));
    }
}