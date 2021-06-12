package supportsearch.controller.search;

import supportsearch.model.DataHolder;
import supportsearch.model.Organization;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrganizationSearch {

    public static List<Organization> searchOrganizations(Field searchField, String searchValue) {
        List<Organization> organizations = new ArrayList<>();
        Object searchValueCasted = SearchUtil.convertStringToFieldType(searchField, searchValue);
        if (searchValueCasted != null) {
            Predicate<Organization> predicate = SearchUtil.generatePredicate(searchField, searchValueCasted);
            if (predicate != null) {
                organizations = DataHolder.ORGANIZATIONS.stream().filter(predicate).collect(Collectors.toList());
            }
        }

        return organizations;
    }

}
