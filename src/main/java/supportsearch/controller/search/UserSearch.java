package supportsearch.controller.search;

import supportsearch.model.DataHolder;
import supportsearch.model.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserSearch {

    public static List<User> searchUsers(Field searchField, String searchValue) {
        List<User> users = new ArrayList<>();
        Object searchValueCasted = SearchUtil.convertStringToFieldType(searchField, searchValue);
        if (searchValueCasted != null) {
            Predicate<User> predicate = SearchUtil.generatePredicate(searchField, searchValueCasted);
            if (predicate != null) {
                users = DataHolder.USERS.stream().filter(predicate).collect(Collectors.toList());
            }
        }

        return users;
    }
}
