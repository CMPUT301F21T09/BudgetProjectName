package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.cmput301f21t09.budgetprojectname.models.UserModel;


import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Tests for operations involving follows, including following and requesting
 * to follow another user.
 * Using Hashmap<String, Integer> to simulate Firebase DB functionality
 */
public class FollowTest {

    private String currentUserId;
    private String anotherUserId;

    /**
     * Initializes test users
     */
    @Before
    public void init() {
        // using testuser1's uid for testing current user
        currentUserId = "XPG70micV5XLyvhytqghBOLnK8U2";
        // using testuser2's uid for testing another user
        anotherUserId = "BtVfJ5exBBgpYXchbR4wsN8f0cp1";

    }

    /**
     * Tests the sending a follow request
     */
    @Test
    public void testSendRequest() {

    }

    /**
     * Tests the receiving a follow request
     */
    @Test
    public void testReceiveRequest() {
        // current user
        UserModel currentUser = new UserModel(currentUserId, "test1", "Test",
                "User", new HashMap<String, Integer>());
        HashMap<String, Integer> socialMap = currentUser.getSocial();

        // another user has sent current user a follow request
        socialMap.put(anotherUserId, 0);

        // check that this request is seen by current user
        assertEquals(1, socialMap.size());
        assertEquals(0,
                Integer.parseInt(String.valueOf(socialMap.get(anotherUserId))));
    }

    /**
     * Tests the accepting of a follow request
     */
    @Test
    public void testAcceptRequest() {
        // current user
        UserModel currentUser = new UserModel(currentUserId, "test1", "Test",
                "User", new HashMap<String, Integer>());
        HashMap<String, Integer> currentSocialMap = currentUser.getSocial();

        // another user
        UserModel anotherUser = new UserModel(anotherUserId, "test2", "Test2",
                "User", new HashMap<String, Integer>());
        HashMap<String, Integer> anotherSocialMap = anotherUser.getSocial();

        // another user has sent current user a follow request
        currentSocialMap.put(anotherUserId, 0);

        // current user accepts follow request
        currentSocialMap.remove(anotherUserId);
        anotherSocialMap.put(currentUserId, 1);

        // check that other user is removed from social map
        assertEquals(0, currentSocialMap.size());
        // check that other user is following current user (denoted by value 1 in social map)
        assertEquals(1,
                Integer.parseInt(String.valueOf(anotherSocialMap.get(currentUserId))));
    }

    /**
     * Tests the denial of a follow request
     */
    @Test
    public void testDenyRequest() {
        // current user
        UserModel currentUser = new UserModel(currentUserId, "test1", "Test",
                "User", new HashMap<String, Integer>());
        HashMap<String, Integer> currentSocialMap = currentUser.getSocial();

        // another user
        UserModel anotherUser = new UserModel(anotherUserId, "test2", "Test2",
                "User", new HashMap<String, Integer>());
        HashMap<String, Integer> anotherSocialMap = anotherUser.getSocial();

        // another user has sent current user a follow request
        currentSocialMap.put(anotherUserId, 0);

        // current user denies follow request
        currentSocialMap.remove(anotherUserId);

        // check that other user is removed from social map
        assertEquals(0, currentSocialMap.size());
        // check that other user is NOT following current user (denoted by value 1 in social map)
        assertNull(anotherSocialMap.get(currentUserId));

    }

    /**
     * Tests when another user has accepted request
     */
    @Test
    public void testRequestIsAccepted() {

    }

    /**
     * Tests when another user has denied request
     */
    @Test
    public void testRequestIsDenied() {

    }
}
