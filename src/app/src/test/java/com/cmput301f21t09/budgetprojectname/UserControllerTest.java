package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.cmput301f21t09.budgetprojectname.models.UserModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Tests for database operations on users mocked using arraylists
 */
public class UserControllerTest {
    /**
     * Tests reading of user
     */
    @Test
    public void testReadUser() {
        ArrayList<UserModel> userList = new ArrayList<>();
        UserModel user =
                new UserModel("XPG70micV5XLyvhytqghBOLnK8U2", "testUser1",
                        "Test", "User", new HashMap<>());
        userList.add(user);

        UserModel readUser = userList.get(0);
        assertEquals("XPG70micV5XLyvhytqghBOLnK8U2", readUser.getUID());
        assertEquals("testUser1", readUser.getUsername());
    }

    /**
     * Test reading a user by username
     */
    @Test
    public void testReadUserByUserName() {
        ArrayList<UserModel> userList = new ArrayList<>();
        UserModel user =
                new UserModel("XPG70micV5XLyvhytqghBOLnK8U2", "testUser1",
                        "Test", "User", new HashMap<>());
        userList.add(user);

        UserModel readUser = null;

        // Find the user by username
        for (UserModel targetUser : userList) {
            if (targetUser.equals(user)) {
                readUser = targetUser;
            }
        }

        assertEquals("XPG70micV5XLyvhytqghBOLnK8U2", readUser.getUID());
        assertEquals("testUser1", readUser.getUsername());
    }

    /**
     * Test reading a user's follow requests and users that they follow
     */
    @Test
    public void testReadFollows() {
        ArrayList<UserModel> userList = new ArrayList<>();
        UserModel user =
                new UserModel("XPG70micV5XLyvhytqghBOLnK8U2", "testUser1",
                        "Test", "User", new HashMap<>());

        HashMap<String, Integer> followRequests = new HashMap<>();
        followRequests.put("BtVfJ5exBBgpYXchbR4wsN8f0cp1", 0);
        user.setSocial(followRequests);

        userList.add(user);

        // Check the follow request
        HashMap<String, Integer> readFollowRequests = userList.get(0).getSocial();
        assertTrue(readFollowRequests.get("BtVfJ5exBBgpYXchbR4wsN8f0cp1").equals(0)
                || readFollowRequests.get("BtVfJ5exBBgpYXchbR4wsN8f0cp1").equals(2));

        HashMap<String, Integer> followingUsers = new HashMap<>();
        followRequests.put("ZfOzqcG2GHVoWe14WOXOMdpY4DA2", 1);
        user.setSocial(followingUsers);

        userList.add(user);

        // Check users that we follow
        HashMap<String, Integer> readFollowingUsers = userList.get(1).getSocial();
        assertTrue(readFollowRequests.get("ZfOzqcG2GHVoWe14WOXOMdpY4DA2").equals(1)
                || readFollowRequests.get("ZfOzqcG2GHVoWe14WOXOMdpY4DA2").equals(2));
    }

    /**
     * Test updating a user's Social Map
     */
    @Test
    public void testUpdateUserSocialMap() {
        ArrayList<UserModel> userList = new ArrayList<>();
        UserModel user =
                new UserModel("XPG70micV5XLyvhytqghBOLnK8U2", "testUser1",
                        "Test", "User", new HashMap<>());
        userList.add(user);
        UserModel toUpdateUser = userList.get(0);
        userList.remove(0); // The actual DB will not remove the document

        HashMap<String, Integer> newSocialMap = new HashMap<>();
        newSocialMap.put("BtVfJ5exBBgpYXchbR4wsN8f0cp1", 1);
        toUpdateUser.setSocial(newSocialMap);

        userList.add(toUpdateUser);

        assertEquals(1, userList.size());
        assertEquals(newSocialMap, userList.get(0).getSocial());
    }
}
