package supportsearch.controller.loader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import supportsearch.model.DataHolder;
import supportsearch.model.Organization;
import supportsearch.model.Ticket;
import supportsearch.model.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TicketJSONDataLoader implements DataLoader{

    private File ticketsJson = new File("data/tickets.json");

    public  File getTicketsJson() {
        return ticketsJson;
    }

    /**
     * Load tickets from a json file to DataHolder.TICKETS
     */
    @Override
    public void loadData() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            //read org from json
            ObjectMapper mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat(DataLoaderFactory.DATE_PATTERN);
            mapper.setDateFormat(df);
            tickets = mapper.readValue(this.getTicketsJson(), new TypeReference<List<Ticket>>() {});
        } catch(JsonMappingException e) {
            System.out.println(e.getLocalizedMessage());
        } catch(JsonParseException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        DataHolder.TICKETS = tickets;
    }

    /**
     * Create a map of json properties and corresponding Field in Ticket class
     */
    @Override
    public void loadFieldNames() {
        Map<String, Field> map = new LinkedHashMap<>();

        Field[] fields = Ticket.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonProperty.class)) {
                String annotationValue = field.getAnnotation(JsonProperty.class).value();
                map.put(annotationValue, field);
            }
        }

        DataHolder.TICKET_FIELDS = map;
    }

    /**
     * Link Users and Organizations to a ticket based on user ids and organization id
     */
    @Override
    public void loadRelatedEntities() {

        if (DataHolder.TICKETS != null && !DataHolder.TICKETS.isEmpty()) {
            for (Ticket ticket : DataHolder.TICKETS) {
                //Ticket has an organization
                if(ticket.getOrganizationId() != null) {
                    Organization organization = DataHolder.ORGANIZATIONS.stream().filter(o -> o.getId().equals(ticket.getOrganizationId())).findFirst().orElse(null);
                    ticket.setOrganization(organization);
                }
                //Ticket has a submitter user
                if(ticket.getSubmitterId() != null) {
                    User submitter = DataHolder.USERS.stream().filter(o -> o.getId().equals(ticket.getSubmitterId())).findFirst().orElse(null);
                    ticket.setSubmitter(submitter);
                }
                //Ticket has an assignee user
                if(ticket.getAssigneeId() != null) {
                    User assignee = DataHolder.USERS.stream().filter(o -> o.getId().equals(ticket.getAssigneeId())).findFirst().orElse(null);
                    ticket.setAssignee(assignee);
                }

            }
        }
    }
}
