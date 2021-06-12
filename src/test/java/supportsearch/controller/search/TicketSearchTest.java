package supportsearch.controller.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import supportsearch.controller.loader.TicketJSONDataLoader;
import supportsearch.model.DataHolder;
import supportsearch.model.Ticket;

import java.io.File;
import java.net.URL;

class TicketSearchTest {

    File testTickets;

    @BeforeEach
    void setUp() {
        URL resource = this.getClass().getResource("/ticketsJsonFileTest.json");
        testTickets = new File (resource.getFile());
        TicketJSONDataLoader dataLoader = new TicketJSONDataLoader();
        TicketJSONDataLoader spyDataLoader = Mockito.spy(dataLoader);
        Mockito.doReturn(testTickets).when(spyDataLoader).getTicketsJson();
        spyDataLoader.loadData();
    }

    @Test
    void searchTickets() throws NoSuchFieldException {
        //Search different fields and values for ticket 436bf9b0-1147-4c0a-8439-6f79833bff5b
        Assertions.assertEquals("9210cdc9-4bee-485f-a078-35396cd74063", TicketSearch.searchTickets(Ticket.class.getDeclaredField("id"), "436bf9b0-1147-4c0a-8439-6f79833bff5b").get(0).getExternalId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("url"), "http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("externalId"), "9210cdc9-4bee-485f-a078-35396cd74063").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("createdAt"), "2016-04-28T11:19:34 -10:00").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("type"), "incident").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("subject"), "A Catastrophe in Korea (North)").get(0).getId());
        String desc = "Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.";
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("description"), desc).get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("priority"), "high").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("status"), "pending").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("submitterId"), "38").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("assigneeId"), "24").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("organizationId"), "116").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("tags"), "Ohio").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("hasIncidents"), "false").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("dueAt"), "2016-07-31T02:37:50 -10:00").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("via"), "web").get(0).getId());
    }

    @Test
    void searchTickets_returnsNone() throws NoSuchFieldException {
        String idDoesNotExist = "1111aaaa";
        Assertions.assertEquals(0, TicketSearch.searchTickets(Ticket.class.getDeclaredField("id"), idDoesNotExist).size());
    }

    @Test
    void searchTickets_oneTicketIsEmpty_searchWillNotBreak() throws NoSuchFieldException {
        //add ticket with empty values
        Ticket ticket = new Ticket();
        DataHolder.TICKETS.add(ticket);

        //Search different fields and values for ticket 436bf9b0-1147-4c0a-8439-6f79833bff5b
        Assertions.assertEquals("9210cdc9-4bee-485f-a078-35396cd74063", TicketSearch.searchTickets(Ticket.class.getDeclaredField("id"), "436bf9b0-1147-4c0a-8439-6f79833bff5b").get(0).getExternalId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("url"), "http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("externalId"), "9210cdc9-4bee-485f-a078-35396cd74063").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("createdAt"), "2016-04-28T11:19:34 -10:00").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("type"), "incident").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("subject"), "A Catastrophe in Korea (North)").get(0).getId());
        String desc = "Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.";
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("description"), desc).get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("priority"), "high").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("status"), "pending").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("submitterId"), "38").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("assigneeId"), "24").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("organizationId"), "116").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("tags"), "Ohio").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("hasIncidents"), "false").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("dueAt"), "2016-07-31T02:37:50 -10:00").get(0).getId());
        Assertions.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b", TicketSearch.searchTickets(Ticket.class.getDeclaredField("via"), "web").get(0).getId());
    }
}