package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;

import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;

import org.junit.Test;

import java.util.HashMap;

/**
 * Tests for operations involving follows, including following and requesting
 * to follow another user.
 */
public class FollowTest {

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
        // TODO: potentially move to before()
        // using testuser1's uid for testing current user
        String currentUserId = "XPG70micV5XLyvhytqghBOLnK8U2";
        // using testuser2's uid for testing another user
        String anotherUserId = "BtVfJ5exBBgpYXchbR4wsN8f0cp1";
        UserController userController = new UserController();
        userController.readUser(currentUserId, currentUserModel -> {
                HashMap<String, Integer> socialMap = currentUserModel.getSocial();
                // another user has sent current user a follow request
                socialMap.put(anotherUserId, 0);
                // check that this request is seen by current user
                assertEquals(1, socialMap.size());
                assertEquals(0,
                        Integer.parseInt(String.valueOf(socialMap.get(anotherUserId))));
        });

    }

    /**
     * Tests the accepting of a follow request
     */
    @Test
    public void testAcceptRequest() {

    }

    /**
     * Tests the denial of a follow request
     */
    @Test
    public void testDenyRequest() {

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
