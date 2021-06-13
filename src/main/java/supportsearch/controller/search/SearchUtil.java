package supportsearch.controller.search;

import supportsearch.controller.loader.DataLoaderFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class SearchUtil {

    //Note: Making these methods non-static for unit testing

    /**
     * Generate a predicate that will compare a string (search value) to a given field
     * @param field
     * @param searchValue
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> generatePredicate(Field field, Object searchValue) {
        if (field.getGenericType().getTypeName().equals("java.util.List<java.lang.String>")) {
            return listContainsStringPredicate(field, searchValue);
        } else if (field.getGenericType().getTypeName().equals("java.util.Date")) {
            return equalDatesPredicate(field, searchValue);
        } else if (field.getGenericType().getTypeName().equals("java.lang.String")) {
            return equalsStringPredicate(field, searchValue);
        } else {
            return equalsPredicate(field, searchValue);
        }
    }

    /**
     * Create predicate that compares field and value using equals()
     * @param field
     * @param searchValue
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> equalsPredicate(Field field, Object searchValue) {
        return (T instance) -> {
                try {
                    field.setAccessible(true);
                    Object fieldInstance = field.get(instance);
                    return fieldInstance!= null && searchValue.equals(fieldInstance);
                } catch (IllegalAccessException e) {
                    System.out.println("Error when creating equals predicate " + e.getLocalizedMessage());
                }
            return false;
        };
    }

    /**
     * Create predicate that compares String field and String value
     * @param field
     * @param searchValue
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> equalsStringPredicate(Field field, Object searchValue) {
        return (T instance) -> {
            try {
                field.setAccessible(true);
                String fieldInstance = (String) field.get(instance);
                return fieldInstance!= null && fieldInstance.equalsIgnoreCase((String) searchValue);
            } catch (IllegalAccessException e) {
                System.out.println("Error when creating equals predicate " + e.getLocalizedMessage());
            }
            return false;
        };
    }

    /**
     * Create predicate that check if a given string is present in an List<String> field
     * @param field
     * @param searchValue
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> listContainsStringPredicate(Field field, Object searchValue) {
        return (T instance) -> {
            try {
                field.setAccessible(true);
                List<String> listField = (ArrayList<String>) field.get(instance);
                return listField != null && listField.stream().anyMatch(l->l.equalsIgnoreCase((String) searchValue));
            } catch (IllegalAccessException e) {
                System.out.println("Error when creating predicate for a List field"  + e.getLocalizedMessage());
            }
            return false;
        };
    }

    /**
     * Create predicate to compare a given date string is equal to a Date field
     * @param field
     * @param searchValue
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> equalDatesPredicate(Field field, Object searchValue) {
        return (T instance) -> {
            try {
                field.setAccessible(true);
                Date dateField = (Date) field.get(instance);
                return dateField != null && dateField.equals(searchValue);
            } catch (IllegalAccessException e) {
                System.out.println("Error when creating equals predicate for a Date. "  + e.getLocalizedMessage());
            }
            return false;
        };
    }

    /**
     * Convert String search value to the class/type of the field
     *
     * @param field
     * @param stringValue
     * @return
     */
    public static Object convertStringToFieldType(Field field, String stringValue) {
        Type fieldType = field.getGenericType();
        try {
            //Limitation: Type considered are only the types from the json file
            switch (fieldType.getTypeName()) {
                case "java.lang.String":
                case "java.util.List<java.lang.String>":
                    return stringValue;
                case "java.lang.Long":
                    return Long.parseLong(stringValue);
                case "java.lang.Integer":
                    return Integer.parseInt(stringValue);
                case "java.lang.Boolean":
                    return convertStringToBoolean(stringValue);
                case "java.util.Locale":
                    return convertStringToLocale(stringValue);
                case "java.util.Date":
                    return convertStringToDate(stringValue);
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("!!! Cannot cast the value '" + stringValue + "' to a Number !!!");
            return null;
        }
    }

    /**
     * Convert String to Date based on DataLoaderFactory.DATE_PATTERN
     * @param dateStr
     * @return
     */
    public static Date convertStringToDate(String dateStr) {
        DateFormat df = new SimpleDateFormat(DataLoaderFactory.DATE_PATTERN);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("!!! Cannot cast the value '" + dateStr + "' to a Date !!!");
            return null;
        }
    }

    /**
     * Convert a locale string to a Locale
     * @param stringValue
     * @return
     */
    public static Locale convertStringToLocale(String stringValue) {
        return Locale.forLanguageTag(stringValue);
    }

    /**
     * Convert string to Boolean
     * @param stringValue
     * @return
     */
    public static Boolean convertStringToBoolean(String stringValue) {
        if ("true".equalsIgnoreCase(stringValue)) {
            return Boolean.TRUE;
        } else if ("false".equalsIgnoreCase(stringValue)) {
            return Boolean.FALSE;
        } else {
            //other string values will return null
            return null;
        }
    }
}
