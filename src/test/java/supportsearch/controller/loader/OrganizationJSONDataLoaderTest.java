package supportsearch.controller.loader;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import supportsearch.controller.search.SearchUtil;
import supportsearch.model.DataHolder;

import java.io.File;
import java.net.URL;

class OrganizationJSONDataLoaderTest {

    File testOrganizations;

    @BeforeEach
    void setUp() {
        URL resource = this.getClass().getResource("/organizationJsonFileTest.json");
        testOrganizations = new File (resource.getFile());
    }

    @Test
    void loadData_loadsToListOfOrganizations() {
        OrganizationJSONDataLoader dataLoader = new OrganizationJSONDataLoader();
        OrganizationJSONDataLoader spyDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testOrganizations).when(spyDataLoader).getOrganizationsJson();
        spyDataLoader.loadData();
        Assertions.assertEquals(2, DataHolder.ORGANIZATIONS.size());
        Assertions.assertEquals(101, DataHolder.ORGANIZATIONS.get(0).getId());
        Assertions.assertEquals("http://initech.zendesk.com/api/v2/organizations/101.json", DataHolder.ORGANIZATIONS.get(0).getUrl());
        Assertions.assertEquals("9270ed79-35eb-4a38-a46f-35725197ea8d", DataHolder.ORGANIZATIONS.get(0).getExternalId());
        Assertions.assertEquals("Enthaze", DataHolder.ORGANIZATIONS.get(0).getName());
        Assertions.assertEquals(4, DataHolder.ORGANIZATIONS.get(0).getDomainNames().size());
        Assertions.assertEquals("ecratic.com", DataHolder.ORGANIZATIONS.get(0).getDomainNames().get(1));
        Assertions.assertEquals(SearchUtil.convertStringToDate("2016-05-21T11:10:28 -10:00"), DataHolder.ORGANIZATIONS.get(0).getCreatedAt());
        Assertions.assertEquals("MegaCorp", DataHolder.ORGANIZATIONS.get(0).getDetails());
        Assertions.assertEquals(false, DataHolder.ORGANIZATIONS.get(0).isSharedTickets());
        Assertions.assertEquals(3, DataHolder.ORGANIZATIONS.get(0).getTags().size());
        Assertions.assertEquals("Farley", DataHolder.ORGANIZATIONS.get(0).getTags().get(2));
    }

}