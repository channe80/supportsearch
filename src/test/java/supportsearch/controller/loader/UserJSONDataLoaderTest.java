package supportsearch.controller.loader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import supportsearch.controller.search.SearchUtil;
import supportsearch.model.DataHolder;
import supportsearch.model.User;

import java.io.File;
import java.net.URL;

class UserJSONDataLoaderTest {

    File testUsers;

    @BeforeEach
    void setUp() {
        URL resource = this.getClass().getResource("/usersJsonFileTest.json");
        testUsers = new File (resource.getFile());
    }

    @Test
    void loadData_loadsToListOfUsers() {
        UserJSONDataLoader dataLoader = new UserJSONDataLoader();
        UserJSONDataLoader spyDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testUsers).when(spyDataLoader).getUsersJson();
        spyDataLoader.loadData();
        Assertions.assertEquals(2, DataHolder.USERS.size());
        Assertions.assertEquals(1, DataHolder.USERS.get(0).getId());
        Assertions.assertEquals("http://initech.zendesk.com/api/v2/users/1.json", DataHolder.USERS.get(0).getUrl());
        Assertions.assertEquals("74341f74-9c79-49d5-9611-87ef9b6eb75f", DataHolder.USERS.get(0).getExternalId());
        Assertions.assertEquals("Francisca Rasmussen", DataHolder.USERS.get(0).getName());
        Assertions.assertEquals("Miss Coffey", DataHolder.USERS.get(0).getAlias());
        Assertions.assertEquals(SearchUtil.convertStringToDate("2016-04-15T05:19:46 -10:00"), DataHolder.USERS.get(0).getCreatedAt());
        Assertions.assertEquals(true, DataHolder.USERS.get(0).isActive());
        Assertions.assertEquals(true, DataHolder.USERS.get(0).isVerified());
        Assertions.assertEquals(false, DataHolder.USERS.get(0).isShared());
        Assertions.assertEquals(SearchUtil.convertStringToLocale("en-AU"), DataHolder.USERS.get(0).getLocale());
        Assertions.assertEquals("Sri Lanka", DataHolder.USERS.get(0).getTimezone());
        Assertions.assertEquals(SearchUtil.convertStringToDate("2013-08-04T01:03:27 -10:00"), DataHolder.USERS.get(0).getLastLoginAt());
        Assertions.assertEquals("coffeyrasmussen@flotonic.com", DataHolder.USERS.get(0).getEmail());
        Assertions.assertEquals("8335-422-718", DataHolder.USERS.get(0).getPhone());
        Assertions.assertEquals("Don't Worry Be Happy!", DataHolder.USERS.get(0).getSignature());
        Assertions.assertEquals(101, DataHolder.USERS.get(0).getOrganizationId());
        Assertions.assertEquals(4, DataHolder.USERS.get(0).getTags().size());
        Assertions.assertEquals("Sutton", DataHolder.USERS.get(0).getTags().get(1));
        Assertions.assertEquals(true, DataHolder.USERS.get(0).isSuspended());
        Assertions.assertEquals("admin", DataHolder.USERS.get(0).getRole());
    }

    @Test
    void loadRelatedEntities_userLinksToOrganization() {

        //load organizations
        URL resource = this.getClass().getResource("/organizationJsonFileTest.json");
        File testOrganizations = new File (resource.getFile());
        OrganizationJSONDataLoader dataLoader = new OrganizationJSONDataLoader();
        OrganizationJSONDataLoader spyDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testOrganizations).when(spyDataLoader).getOrganizationsJson();
        spyDataLoader.loadData();

        //load users
        UserJSONDataLoader dataLoader2 = new UserJSONDataLoader();
        UserJSONDataLoader spyUserDataLoader = Mockito.spy(dataLoader2);
        Mockito.doReturn(testUsers).when(spyUserDataLoader).getUsersJson();
        spyUserDataLoader.loadData();

        //User.getOrganization is null
        Assertions.assertEquals(false, DataHolder.USERS.get(1).getOrganization() != null);

        //load tickets
        URL resource3 = this.getClass().getResource("/ticketsJsonFileTest.json");
        File testTickets = new File (resource3.getFile());
        TicketJSONDataLoader dataLoader3 = new TicketJSONDataLoader();
        TicketJSONDataLoader spyDataLoader3 = Mockito.spy(dataLoader3);
        Mockito.doReturn(testTickets).when(spyDataLoader3).getTicketsJson();
        spyDataLoader3.loadData();

        spyUserDataLoader.loadRelatedEntities();

        //User.getOrganization now has a Organization value
        Assertions.assertEquals(true, DataHolder.USERS.get(1).getOrganization() != null);
        Assertions.assertEquals("Nutralab", DataHolder.USERS.get(1).getOrganization().getName());
        Assertions.assertEquals(DataHolder.USERS.get(1).getOrganizationId(), DataHolder.USERS.get(1).getOrganization().getId());
    }
}