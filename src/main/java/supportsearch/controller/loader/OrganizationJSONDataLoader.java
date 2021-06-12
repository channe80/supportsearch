package supportsearch.controller.loader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import supportsearch.model.DataHolder;
import supportsearch.model.Organization;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrganizationJSONDataLoader implements DataLoader {

    private File organizationsJson = new File("data/organizations.json");

    public File getOrganizationsJson() {
        return organizationsJson;
    }

    /**
     * Load organizations from a json file to DataHolder.ORGANIZATIONS
     *
     */
    @Override
    public void loadData() {
        List<Organization> organizations = new ArrayList<>();
        try {
            //read org from json
            ObjectMapper mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat(DataLoaderFactory.DATE_PATTERN);
            mapper.setDateFormat(df);
            organizations = mapper.readValue(this.getOrganizationsJson(), new TypeReference<List<Organization>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHolder.ORGANIZATIONS = organizations;
    }

    /**
     * Create a map of json properties and corresponding Field in Organization class
     */
    @Override
    public void loadFieldNames() {
        Map<String, Field> map = new LinkedHashMap<>();

        Field[] fields = Organization.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonProperty.class)) {
                String annotationValue = field.getAnnotation(JsonProperty.class).value();
                map.put(annotationValue, field);
            }
        }
        DataHolder.ORGANIZATION_FIELDS = map;
    }

    @Override
    public void loadRelatedEntities() {
        //not needed, user/tickets will have an organization
    }
}
