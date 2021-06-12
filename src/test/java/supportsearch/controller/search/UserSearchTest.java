package supportsearch.controller.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import supportsearch.controller.loader.UserJSONDataLoader;
import supportsearch.model.DataHolder;
import supportsearch.model.User;

import java.io.File;
import java.net.URL;

class UserSearchTest {

    File testUsers;

    @BeforeEach
    void setUp() {
        URL resource = this.getClass().getResource("/usersJsonFileTest.json");
        testUsers = new File(resource.getFile());
        UserJSONDataLoader dataLoader = new UserJSONDataLoader();
        UserJSONDataLoader spyDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testUsers).when(spyDataLoader).getUsersJson();
        spyDataLoader.loadData();
    }

    @Test
    void searchUsers() throws NoSuchFieldException {
        //Search different fields and values for user 1
        Assertions.assertEquals("74341f74-9c79-49d5-9611-87ef9b6eb75f", UserSearch.searchUsers(User.class.getDeclaredField("id"), "1").get(0).getExternalId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("url"), "http://initech.zendesk.com/api/v2/users/1.json").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("externalId"), "74341f74-9c79-49d5-9611-87ef9b6eb75f").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("name"), "Francisca Rasmussen").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("alias"), "Miss Coffey").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("createdAt"), "2016-04-15T05:19:46 -10:00").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("active"), "true").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("verified"), "true").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("shared"), "false").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("locale"), "en-AU").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("timezone"), "Sri Lanka").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("lastLoginAt"), "2013-08-04T01:03:27 -10:00").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("email"), "coffeyrasmussen@flotonic.com").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("phone"), "8335-422-718").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("signature"), "Don't Worry Be Happy!").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("organizationId"), "101").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("tags"), "Springville").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("suspended"), "true").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("role"), "admin").get(0).getId());

    }

    @Test
    void searchUsers_returnsNone() throws NoSuchFieldException {
        String idDoesNotExist = "100";
        Assertions.assertEquals(0, UserSearch.searchUsers(User.class.getDeclaredField("id"), idDoesNotExist).size());
    }

    @Test
    void searchUsers_invalidNumber_returnsNone() throws NoSuchFieldException {
        String nonNumeric = "a";
        Assertions.assertEquals(0, UserSearch.searchUsers(User.class.getDeclaredField("id"), nonNumeric).size());
    }

    @Test
    void searchUsers_invalidLocale_returnsNone() throws NoSuchFieldException {
        String invalidLocale = "1";
        Assertions.assertEquals(0, UserSearch.searchUsers(User.class.getDeclaredField("locale"), invalidLocale).size());
    }

    @Test
    void searchUsers_oneUserIsEmpty_searchWillNotBreak() throws NoSuchFieldException {
        //add user with empty values
        User user = new User();
        DataHolder.USERS.add(user);

        //Search different fields and values for user 1
        Assertions.assertEquals("74341f74-9c79-49d5-9611-87ef9b6eb75f", UserSearch.searchUsers(User.class.getDeclaredField("id"), "1").get(0).getExternalId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("url"), "http://initech.zendesk.com/api/v2/users/1.json").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("externalId"), "74341f74-9c79-49d5-9611-87ef9b6eb75f").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("name"), "Francisca Rasmussen").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("alias"), "Miss Coffey").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("createdAt"), "2016-04-15T05:19:46 -10:00").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("active"), "true").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("verified"), "true").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("shared"), "false").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("locale"), "en-AU").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("timezone"), "Sri Lanka").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("lastLoginAt"), "2013-08-04T01:03:27 -10:00").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("email"), "coffeyrasmussen@flotonic.com").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("phone"), "8335-422-718").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("signature"), "Don't Worry Be Happy!").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("organizationId"), "101").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("tags"), "Springville").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("suspended"), "true").get(0).getId());
        Assertions.assertEquals(1, UserSearch.searchUsers(User.class.getDeclaredField("role"), "admin").get(0).getId());

    }
}