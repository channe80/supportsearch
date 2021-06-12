package supportsearch.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataHolder {

    public static List<Organization> ORGANIZATIONS = new ArrayList<>();
    public static List<User> USERS = new ArrayList<>();
    public static List<Ticket> TICKETS = new ArrayList<>();

    public static Map<String, Field> ORGANIZATION_FIELDS = new LinkedHashMap<>();
    public static Map<String, Field> USER_FIELDS = new LinkedHashMap<>();
    public static Map<String, Field> TICKET_FIELDS = new LinkedHashMap<>();
}
