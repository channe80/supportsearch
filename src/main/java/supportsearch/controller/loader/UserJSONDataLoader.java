package supportsearch.controller.loader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import supportsearch.model.DataHolder;
import supportsearch.model.Organization;
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

public class UserJSONDataLoader implements DataLoader {

    private File usersJson = new File("data/users.json");

    public File getUsersJson() {
        return usersJson;
    }

    /**
     * Load users from a json file to List<Organization>
     */
    @Override
    public void loadData() {
        List<User> users = new ArrayList<>();
        try {
            //read org from json
            ObjectMapper mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat(DataLoaderFactory.DATE_PATTERN);
            mapper.setDateFormat(df);
            users = mapper.readValue(this.getUsersJson(), new TypeReference<List<User>>() {});
        }  catch (IOException e) {
            e.printStackTrace();
        }
        DataHolder.USERS = users;
    }

    /**
     * Create a map of json properties and corresponding Field in User class
     */
    @Override
    public void loadFieldNames() {
        Map<String, Field> map = new LinkedHashMap<>();

        Field[] fields = User.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonProperty.class)) {
                String annotationValue = field.getAnnotation(JsonProperty.class).value();
                map.put(annotationValue, field);
            }
        }

        DataHolder.USER_FIELDS = map;
    }

    /**
     * Link Organization to a ticket based on organization id
     */
    public void loadRelatedEntities() {
        //Get organization of the users
        if (DataHolder.USERS != null && !DataHolder.USERS.isEmpty()) {
            for (User user : DataHolder.USERS) {
                //User has an organization
                if(user.getOrganizationId() != null) {
                    Organization organization = DataHolder.ORGANIZATIONS.stream().filter(o -> o.getId().equals(user.getOrganizationId())).findFirst().orElse(null);
                    user.setOrganization(organization);
                }
            }
        }
    }
}
