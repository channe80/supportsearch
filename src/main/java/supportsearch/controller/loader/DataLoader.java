package supportsearch.controller.loader;

/**
 * DataLoader Interface can have different implementation depending on type of entity (Organization/User/Ticket)
 * and source of data i.e JSON file, database, etc.
 */
public interface DataLoader {
    public void loadData();
    public void loadFieldNames();
    public void loadRelatedEntities();
}
