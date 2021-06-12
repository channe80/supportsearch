package supportsearch.controller.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import supportsearch.controller.loader.OrganizationJSONDataLoader;
import supportsearch.model.DataHolder;
import supportsearch.model.Organization;

import java.io.File;
import java.net.URL;

class OrganizationSearchTest {

    File testOrganizations;

    @BeforeEach
    void setUp() {
        URL resource = this.getClass().getResource("/organizationJsonFileTest.json");
        testOrganizations = new File (resource.getFile());
        OrganizationJSONDataLoader dataLoader = new OrganizationJSONDataLoader();
        OrganizationJSONDataLoader spyDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testOrganizations).when(spyDataLoader).getOrganizationsJson();
        spyDataLoader.loadData();
    }

    @Test
    void searchOrganizations() throws NoSuchFieldException {
        //Search different fields and values for organization 101
        Assertions.assertEquals("9270ed79-35eb-4a38-a46f-35725197ea8d", OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("id"), "101").get(0).getExternalId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("url"), "http://initech.zendesk.com/api/v2/organizations/101.json").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("externalId"), "9270ed79-35eb-4a38-a46f-35725197ea8d").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("name"), "Enthaze").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("domainNames"), "kage.com").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("createdAt"), "2016-05-21T11:10:28 -10:00").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("details"), "MegaCorp").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("sharedTickets"), "false").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("tags"), "Fulton").get(0).getId());
    }

    @Test
    void searchOrganizations_returnsNone() throws NoSuchFieldException {
        String idDoesNotExist = "1111";
        Assertions.assertEquals(0, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("id"), idDoesNotExist).size());
    }

    @Test
    void searchUsers_invalidNumber_returnsNone() throws NoSuchFieldException {
        String nonNumeric = "a";
        Assertions.assertEquals(0, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("id"), nonNumeric).size());
    }

    @Test
    void searchUsers_invalidDate_returnsNone() throws NoSuchFieldException {
        String invalideDate = "a";
        Assertions.assertEquals(0, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("createdAt"), invalideDate).size());
    }

    @Test
    void searchUsers_invalidBoolean_returnsNone() throws NoSuchFieldException {
        String invalideBoolean = "a";
        Assertions.assertEquals(0, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("sharedTickets"), invalideBoolean).size());
    }

    @Test
    void searchOrganizations_oneOrganizationIsEmpty_searchWillNotBreak() throws NoSuchFieldException {

        //add organization with empty values
        Organization organization = new Organization();
        DataHolder.ORGANIZATIONS.add(organization);

        //Search different fields and values for organization 101
        Assertions.assertEquals("9270ed79-35eb-4a38-a46f-35725197ea8d", OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("id"), "101").get(0).getExternalId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("url"), "http://initech.zendesk.com/api/v2/organizations/101.json").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("externalId"), "9270ed79-35eb-4a38-a46f-35725197ea8d").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("name"), "Enthaze").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("domainNames"), "kage.com").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("createdAt"), "2016-05-21T11:10:28 -10:00").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("details"), "MegaCorp").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("sharedTickets"), "false").get(0).getId());
        Assertions.assertEquals(101, OrganizationSearch.searchOrganizations(Organization.class.getDeclaredField("tags"), "Fulton").get(0).getId());
    }
}