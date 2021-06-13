package supportsearch.controller.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supportsearch.controller.loader.DataLoaderFactory;
import supportsearch.model.User;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class SearchUtilTest {

    List<User> users = new ArrayList<>();

    @BeforeEach
    void beforeEach() throws ParseException {
        DateFormat df = new SimpleDateFormat(DataLoaderFactory.DATE_PATTERN);
        User u1 = new User();
        u1.setId(123L);
        u1.setName("Francisca Rasmussen");
        u1.setExternalId("1a227508-9f39-427c-8f57-1b72f3fab87c");
        Date d1 = SearchUtil.convertStringToDate("2016-04-15T05:19:46 -10:00");
        u1.setCreatedAt(d1);
        u1.setActive(true);
        u1.setLocale(SearchUtil.convertStringToLocale("zh-CN"));
        u1.setTags(new ArrayList<>(Arrays. asList("Foxworth", "Woodlands", "Herlong")));

        User u2 = new User();
        u2.setId(456L);
        u1.setName("Ingrid Wagner");
        u2.setExternalId("2217c7dc-7371-4401-8738-0a8a8aedc08d");
        Date d2 = SearchUtil.convertStringToDate("2017-04-15T05:19:46 -10:00");
        u2.setCreatedAt(d2);
        u2.setActive(true);
        u2.setLocale(SearchUtil.convertStringToLocale("en-AU"));
        u2.setTags(new ArrayList<>(Arrays. asList("Mulino", "Woodlands", "Kenwood")));

        User u3 = new User();
        u3.setId(555L);
        u3.setExternalId("2217c7dc-7371-4401-8738-0a8a8aedc08d");
        u3.setActive(true);

        users.add(u1);
        users.add(u2);
    }

    @Test
    void equalsStringPredicate_fieldStringType_valuesCompared() throws NoSuchFieldException {
        Field stringField = User.class.getDeclaredField("externalId");
        Object searchValThatExist = SearchUtil.convertStringToFieldType(stringField,"1a227508-9f39-427c-8f57-1b72f3fab87c");
        List<User> collect = users.stream().filter(SearchUtil.equalsStringPredicate(stringField, searchValThatExist)).collect(Collectors.toList());
        Assertions.assertEquals(1, collect.size());
        Assertions.assertEquals(123L, collect.get(0).getId());

        Object searchValDoesNotExist =  SearchUtil.convertStringToFieldType(stringField,"aaaaabbb");
        List<User> collect2 = users.stream().filter(SearchUtil.equalsStringPredicate(stringField, searchValDoesNotExist)).collect(Collectors.toList());
        Assertions.assertEquals(0, collect2.size());
    }

    @Test
    void equalsStringPredicate_fieldStringType_caseIgnored() throws NoSuchFieldException {
        Field stringField = User.class.getDeclaredField("name");
        Object searchValThatExist = SearchUtil.convertStringToFieldType(stringField,"ingrid wagner");
        List<User> collect = users.stream().filter(SearchUtil.equalsStringPredicate(stringField, searchValThatExist)).collect(Collectors.toList());
        Assertions.assertEquals(1, collect.size());
        Assertions.assertEquals(123L, collect.get(0).getId());
    }

    @Test
    void equalsPredicate_fieldLongType_valuesCompared() throws NoSuchFieldException {
        Field longField = User.class.getDeclaredField("id");

        Object searchValThatExist = SearchUtil.convertStringToFieldType(longField, "123");
        List<User> collect = users.stream().filter(SearchUtil.equalsPredicate(longField, searchValThatExist)).collect(Collectors.toList());
        Assertions.assertEquals(1, collect.size());
        Assertions.assertEquals(123L, collect.get(0).getId());

        Object searchValDoesNotExist = SearchUtil.convertStringToFieldType(longField, "899");
        List<User> collect2 = users.stream().filter(SearchUtil.equalsPredicate(longField, searchValDoesNotExist)).collect(Collectors.toList());
        Assertions.assertEquals(0, collect2.size());
    }

    @Test
    void equalsPredicate_fieldLocaleType_valuesCompared() throws NoSuchFieldException {
        Field localeField = User.class.getDeclaredField("locale");
        Object searchValThatExist = SearchUtil.convertStringToFieldType(localeField, "en-AU");
        List<User> collect = users.stream().filter(SearchUtil.equalsPredicate(localeField, searchValThatExist)).collect(Collectors.toList());
        Assertions.assertEquals(1, collect.size());
        Assertions.assertEquals(456L, collect.get(0).getId());

        Object searchValDoesNotExist = SearchUtil.convertStringToFieldType(localeField,"de-CH");
        List<User> collect2 = users.stream().filter(SearchUtil.equalsPredicate(localeField, searchValDoesNotExist)).collect(Collectors.toList());
        Assertions.assertEquals(0, collect2.size());
    }

    @Test
    void equalsPredicate_fieldBooleanType_valuesCompared() throws NoSuchFieldException {
        Field booleanField = User.class.getDeclaredField("active");
        Object searchValThatExist = SearchUtil.convertStringToFieldType(booleanField, "true");
        List<User> collect = users.stream().filter(SearchUtil.equalsPredicate(booleanField, searchValThatExist)).collect(Collectors.toList());
        Assertions.assertEquals(2, collect.size());
        Assertions.assertEquals(123L, collect.get(0).getId());
        Assertions.assertEquals(456L, collect.get(1).getId());

        Object searchValDoesNotExist = SearchUtil.convertStringToFieldType(booleanField,"false");
        List<User> collect2 = users.stream().filter(SearchUtil.equalsPredicate(booleanField, searchValDoesNotExist)).collect(Collectors.toList());
        Assertions.assertEquals(0, collect2.size());
    }

    @Test
    void equalDatesPredicate_fieldDateType_valuesCompared() throws NoSuchFieldException {
        Field dateField = User.class.getDeclaredField("createdAt");

        Object searchValThatExist = SearchUtil.convertStringToFieldType(dateField, "2016-04-15T05:19:46 -10:00");
        List<User> collect = users.stream().filter(SearchUtil.equalDatesPredicate(dateField, searchValThatExist)).collect(Collectors.toList());
        Assertions.assertEquals(1, collect.size());
        Assertions.assertEquals(123L, collect.get(0).getId());

        Object searchValDoesNotExist = SearchUtil.convertStringToFieldType(dateField, "2021-04-15T05:19:46 -10:00");
        List<User> collect2 = users.stream().filter(SearchUtil.equalDatesPredicate(dateField, searchValDoesNotExist)).collect(Collectors.toList());
        Assertions.assertEquals(0, collect2.size());
    }


    @Test
    void listContainsStringPredicate_fieldStringListType_valuesCompared() throws NoSuchFieldException {
        Field listField = User.class.getDeclaredField("tags");

        String searchValThatExist = "Woodlands";
        List<User> collect = users.stream().filter(SearchUtil.listContainsStringPredicate(listField, searchValThatExist)).collect(Collectors.toList());
        Assertions.assertEquals(2, collect.size());
        Assertions.assertEquals(123L, collect.get(0).getId());

        String searchValThatExistInUpperCase2 = "MULINO";
        List<User> collect2 = users.stream().filter(SearchUtil.listContainsStringPredicate(listField, searchValThatExistInUpperCase2)).collect(Collectors.toList());
        Assertions.assertEquals(2, collect.size());
        Assertions.assertEquals(456L, collect2.get(0).getId());

        String searchValDoesNotExist = "Rowe";
        List<User> collect3 = users.stream().filter(SearchUtil.listContainsStringPredicate(listField, searchValDoesNotExist)).collect(Collectors.toList());
        Assertions.assertEquals(0, collect3.size());
    }

    @Test
    void convertStringToBoolean_returnTrue() {
        Assertions.assertTrue(SearchUtil.convertStringToBoolean("true"));
        Assertions.assertTrue(SearchUtil.convertStringToBoolean("TRUE"));
        Assertions.assertTrue(SearchUtil.convertStringToBoolean("tRue"));
    }

    @Test
    void convertStringToBoolean_returnFalse() {
        Assertions.assertFalse(SearchUtil.convertStringToBoolean("false"));
        Assertions.assertFalse(SearchUtil.convertStringToBoolean("FALSE"));
        Assertions.assertFalse(SearchUtil.convertStringToBoolean("faLsE"));
    }

    @Test
    void convertStringToBoolean_returnNull() {
        Assertions.assertEquals(null, SearchUtil.convertStringToBoolean(""));
        Assertions.assertEquals(null, SearchUtil.convertStringToBoolean("sfds"));
        Assertions.assertEquals(null, SearchUtil.convertStringToBoolean("yes"));
        Assertions.assertEquals(null, SearchUtil.convertStringToBoolean("no"));
    }
}