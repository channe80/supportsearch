package supportsearch.controller.loader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import supportsearch.controller.search.SearchUtil;
import supportsearch.model.DataHolder;

import java.io.File;
import java.net.URL;

class TicketJSONDataLoaderTest {

    File testTickets;

    @BeforeEach
    void setUp() {
        URL resource = this.getClass().getResource("/ticketsJsonFileTest.json");
        testTickets = new File (resource.getFile());
    }

    @Test
    void loadData_loadsToListOfTickets() {
        TicketJSONDataLoader dataLoader = new TicketJSONDataLoader();
        TicketJSONDataLoader spyDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testTickets).when(spyDataLoader).getTicketsJson();
        spyDataLoader.loadData();

        //Search different fields and values for user 436bf9b0-1147-4c0a-8439-6f79833bff5b
        Assertions.assertEquals(2, DataHolder.TICKETS.size());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", DataHolder.TICKETS.get(0).getId());
        Assertions.assertEquals("http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json", DataHolder.TICKETS.get(0).getUrl());
        Assertions.assertEquals("9210cdc9-4bee-485f-a078-35396cd74063", DataHolder.TICKETS.get(0).getExternalId());
        Assertions.assertEquals(SearchUtil.convertStringToDate("2016-04-28T11:19:34 -10:00"), DataHolder.TICKETS.get(0).getCreatedAt());
        Assertions.assertEquals("incident", DataHolder.TICKETS.get(0).getType());
        Assertions.assertEquals("A Catastrophe in Korea (North)", DataHolder.TICKETS.get(0).getSubject());
        Assertions.assertEquals("Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.", DataHolder.TICKETS.get(0).getDescription());
        Assertions.assertEquals("high", DataHolder.TICKETS.get(0).getPriority());
        Assertions.assertEquals("pending", DataHolder.TICKETS.get(0).getStatus());
        Assertions.assertEquals(38, DataHolder.TICKETS.get(0).getSubmitterId());
        Assertions.assertEquals(24, DataHolder.TICKETS.get(0).getAssigneeId());
        Assertions.assertEquals(116, DataHolder.TICKETS.get(0).getOrganizationId());
        Assertions.assertEquals(4, DataHolder.TICKETS.get(0).getTags().size());
        Assertions.assertEquals("American Samoa", DataHolder.TICKETS.get(0).getTags().get(2));
        Assertions.assertEquals(false, DataHolder.TICKETS.get(0).isHasIncidents());
        Assertions.assertEquals(SearchUtil.convertStringToDate("2016-07-31T02:37:50 -10:00"), DataHolder.TICKETS.get(0).getDueAt());
        Assertions.assertEquals("web", DataHolder.TICKETS.get(0).getVia());
    }

    @Test
    void loadHasAEntities_ticketsLinkedToUserAndOrganization() {

        //load organizations
        URL resource = this.getClass().getResource("/organizationJsonFileTest.json");
        File testOrganizations = new File (resource.getFile());
        OrganizationJSONDataLoader dataLoader1 = new OrganizationJSONDataLoader();
        OrganizationJSONDataLoader spyDataLoader1 = Mockito.spy(dataLoader1);
        Mockito.doReturn(testOrganizations).when(spyDataLoader1).getOrganizationsJson();
        spyDataLoader1.loadData();

        //load users
        URL resource2 = this.getClass().getResource("/usersJsonFileTest.json");
        File testUsers = new File (resource2.getFile());
        UserJSONDataLoader dataLoader2 = new UserJSONDataLoader();
        UserJSONDataLoader spyDataLoader2 = Mockito.spy(dataLoader2);
        Mockito.doReturn(testUsers).when(spyDataLoader2).getUsersJson();
        spyDataLoader2.loadData();

        //load tickets
        TicketJSONDataLoader dataLoader = new TicketJSONDataLoader();
        TicketJSONDataLoader spyTicketDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testTickets).when(spyTicketDataLoader).getTicketsJson();
        spyTicketDataLoader.loadData();

        //Ticket.getOrganization is null
        Assertions.assertEquals(false, DataHolder.TICKETS.get(1).getOrganization() != null);
        //Ticket.getSubmitter is null
        Assertions.assertEquals(false, DataHolder.TICKETS.get(1).getSubmitter() != null);
        //Ticket.getAssignee is null
        Assertions.assertEquals(false, DataHolder.TICKETS.get(1).getAssignee() != null);

        spyTicketDataLoader.loadRelatedEntities();

        //Ticket.getOrganization now has a Organization value
        Assertions.assertEquals(true, DataHolder.TICKETS.get(1).getOrganization() != null);
        Assertions.assertEquals("Nutralab", DataHolder.TICKETS.get(1).getOrganization().getName());
        Assertions.assertEquals(DataHolder.TICKETS.get(1).getOrganizationId(), DataHolder.TICKETS.get(1).getOrganization().getId());

        //Ticket.getSubmitter now has a User value
        Assertions.assertEquals(true, DataHolder.TICKETS.get(1).getSubmitter() != null);
        Assertions.assertEquals("Cross Barlow", DataHolder.TICKETS.get(1).getSubmitter().getName());
        Assertions.assertEquals(DataHolder.TICKETS.get(1).getSubmitterId(), DataHolder.TICKETS.get(1).getSubmitter().getId());

        //Ticket.getAssignee now has a User value
        Assertions.assertEquals(true, DataHolder.TICKETS.get(1).getAssignee() != null);
        Assertions.assertEquals("Francisca Rasmussen", DataHolder.TICKETS.get(1).getAssignee().getName());
        Assertions.assertEquals(DataHolder.TICKETS.get(1).getAssigneeId(), DataHolder.TICKETS.get(1).getAssignee().getId());

    }
}