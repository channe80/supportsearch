package supportsearch.controller.search;

import supportsearch.model.DataHolder;
import supportsearch.model.Ticket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TicketSearch {

    public static List<Ticket> searchTickets(Field searchField, String searchValue) {
        List<Ticket> tickets = new ArrayList<>();
        Object searchValueCasted = SearchUtil.convertStringToFieldType(searchField, searchValue);
        if (searchValueCasted != null) {
            Predicate<Ticket> predicate = SearchUtil.generatePredicate(searchField, searchValueCasted);
            if (predicate != null) {
                tickets = DataHolder.TICKETS.stream().filter(predicate).collect(Collectors.toList());
            }
        }

        return tickets;
    }
}
