package edu.cooper.ee.ece366.coopmo;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTests {
    @Autowired
    private UserService userService;

    // scenario
    @Test
    public void testFriends() throws BaseExceptionHandler.InValidFieldValueException, BaseExceptionHandler.FriendRequestDoesNotExistException, BaseExceptionHandler.FriendRequestAlreadyExistException {
        // Test: Creating two users
        User user1 = userService.addUser(new User("name1", "username1", "password1", "email1@gmail.com", "handle1"));
        Assert.assertEquals(user1.getName(), "name1");
        Assert.assertEquals(user1.getUsername(), "username1");
        Assert.assertEquals(user1.getPassword(), "password1");
        Assert.assertEquals(user1.getEmail(), "email1@gmail.com");
        Assert.assertEquals(user1.getHandle(), "handle1");
        Assert.assertNotNull(user1.getId());

        User user2 = userService.addUser(new User("name2", "username2", "password2", "email2@gmail.com", "handle2"));
        Assert.assertEquals(user2.getName(), "name2");
        Assert.assertEquals(user2.getUsername(), "username2");
        Assert.assertEquals(user2.getPassword(), "password2");
        Assert.assertEquals(user2.getEmail(), "email2@gmail.com");
        Assert.assertEquals(user2.getHandle(), "handle2");
        Assert.assertNotNull(user2.getId());

        // Test: sending Friend Request
        userService.sendOutRequest(user1.getId(), user2.getId());
        Assert.assertEquals(new ArrayList<>(userService.getOutgoingFriendRequestSet(user1.getId())).get(0).getId(), user2.getId());
        Assert.assertEquals(new ArrayList<>(userService.getIncomingFriendRequestSet(user2.getId())).get(0).getId(), user1.getId());

        // Test: deleting Friend Request
        User user3 = userService.addUser(new User("Minh-Thai", "minhthai", "password", "minhtyufa@gmail.com", "Edge-Lord"));
        User user4 = userService.addUser(new User("Dan", "swoledan", "password", "danqian@gmail.com", "bigboi"));

        userService.sendOutRequest(user3.getId(), user4.getId());
        Assert.assertEquals(new ArrayList<>(userService.getOutgoingFriendRequestSet(user3.getId())).get(0).getId(), user4.getId());
        Assert.assertEquals(new ArrayList<>(userService.getIncomingFriendRequestSet(user4.getId())).get(0).getId(), user3.getId());

        userService.cancelOutgoingFriendRequest(user3.getId(), user4.getId());
        Assert.assertTrue(userService.getOutgoingFriendRequestSet(user3.getId()).isEmpty());
        Assert.assertTrue(userService.getIncomingFriendRequestSet(user4.getId()).isEmpty());

        // Test: accepting Friend Request
        userService.acceptIncomingRequest(user2.getId(), user1.getId());
        Assert.assertEquals(new ArrayList<>(userService.getUserFriendSet(user1.getId())).get(0).getId(), user2.getId());
        Assert.assertEquals(new ArrayList<>(userService.getUserFriendSet(user2.getId())).get(0).getId(), user1.getId());

        // Test: delete Friend
        userService.deleteFriend(user1.getId(), user2.getId());
        Assert.assertTrue(userService.getUserFriendSet(user3.getId()).isEmpty());
        Assert.assertTrue(userService.getUserFriendSet(user4.getId()).isEmpty());

        // Test: edit profile
        userService.editProfile(user1.getId(), "user10", "username10", "password10", "email10@gmail.com", "handle10");
        Assert.assertEquals(user1.getName(), "user10");
        Assert.assertEquals(user1.getUsername(), "username10");
        Assert.assertEquals(user1.getPassword(), "password10");
        Assert.assertEquals(user1.getEmail(), "email10@gmail.com");
        Assert.assertEquals(user1.getHandle(), "handle10");

        // Test: duplicate friend request
        userService.sendOutRequest(user1.getId(), user2.getId());
        Assert.assertEquals(new ArrayList<>(userService.getOutgoingFriendRequestSet(user1.getId())).get(0).getId(), user2.getId());
        Assert.assertEquals(new ArrayList<>(userService.getIncomingFriendRequestSet(user2.getId())).get(0).getId(), user1.getId());

        userService.sendOutRequest(user1.getId(), user2.getId());
        Assert.assertEquals(userService.getOutgoingFriendRequestSet(user1.getId()).size(), 1);
        Assert.assertEquals(userService.getIncomingFriendRequestSet(user2.getId()).size(), 1);

        // Test: Accepting Friend Request Twice
        userService.acceptIncomingRequest(user2.getId(), user1.getId());
        Assert.assertTrue(userService.getOutgoingFriendRequestSet(user3.getId()).isEmpty());
        Assert.assertTrue(userService.getIncomingFriendRequestSet(user4.getId()).isEmpty());

        assertThrows(BaseExceptionHandler.FriendRequestDoesNotExistException.class, () -> userService.acceptIncomingRequest(user2.getId(), user1.getId()));

        // Test: both way friend request
        userService.sendOutRequest(user3.getId(), user4.getId());
        Assert.assertEquals(new ArrayList<>(userService.getOutgoingFriendRequestSet(user3.getId())).get(0).getId(), user4.getId());
        Assert.assertEquals(new ArrayList<>(userService.getIncomingFriendRequestSet(user4.getId())).get(0).getId(), user3.getId());

        userService.sendOutRequest(user4.getId(), user3.getId());
        Assert.assertEquals(new ArrayList<>(userService.getUserFriendSet(user3.getId())).get(0).getId(), user4.getId());
        Assert.assertEquals(new ArrayList<>(userService.getUserFriendSet(user4.getId())).get(0).getId(), user3.getId());
    }
}
