package supportsearch.controller.loader;

import supportsearch.model.SearchEntity;

public class DataLoaderFactory {

    public static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss X";

    public DataLoader getDataLoader(SearchEntity searchEntity) {
        switch(searchEntity) {
            case ORGANIZATION:
                return new OrganizationJSONDataLoader();
            case USER:
                return new UserJSONDataLoader();
            case TICKET:
                return new TicketJSONDataLoader();
            default:
                return null;
        }
    }
}
