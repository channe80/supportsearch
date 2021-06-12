package supportsearch.view;

import supportsearch.controller.loader.DataLoader;
import supportsearch.controller.loader.DataLoaderFactory;
import supportsearch.controller.search.OrganizationSearch;
import supportsearch.controller.search.TicketSearch;
import supportsearch.controller.search.UserSearch;
import supportsearch.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchView {

    public static List<String> ORGANIZATION_MENU_OPTIONS = new ArrayList<>();
    public static List<String> USER_MENU_OPTIONS = new ArrayList<>();
    public static List<String> TICKET_MENU_OPTIONS = new ArrayList<>();

    public SearchView() {
        loadAllData();
    }

    private void loadAllData() {
        DataLoaderFactory dataLoaderFactory = new DataLoaderFactory();
        DataLoader organizationLoader = dataLoaderFactory.getDataLoader(SearchEntity.ORGANIZATION);
        organizationLoader.loadData();
        organizationLoader.loadFieldNames();

        DataLoader userLoader = dataLoaderFactory.getDataLoader(SearchEntity.USER);
        userLoader.loadData();
        userLoader.loadFieldNames();

        DataLoader ticketLoader = dataLoaderFactory.getDataLoader(SearchEntity.TICKET);
        ticketLoader.loadData();
        ticketLoader.loadFieldNames();

        //Link entities
        organizationLoader.loadRelatedEntities();
        userLoader.loadRelatedEntities();
        ticketLoader.loadRelatedEntities();

        //Used for displaying options to search (original json properties) and corresponding number choice i.e  1 - _id
        ORGANIZATION_MENU_OPTIONS = new ArrayList<>(DataHolder.ORGANIZATION_FIELDS.keySet());
        USER_MENU_OPTIONS = new ArrayList<>(DataHolder.USER_FIELDS.keySet());
        TICKET_MENU_OPTIONS = new ArrayList<>(DataHolder.TICKET_FIELDS.keySet());
    }

    public String mainMenu() {
        String selection;
        Scanner input = new Scanner(System.in);

        System.out.println("=========================\n");
        System.out.println("SEARCH BY:");
        System.out.println("1 - ORGANIZATION   ");
        System.out.println("2 - USER");
        System.out.println("3 - TICKET");
        System.out.println("q - Quit");
        System.out.print("Enter number of option: ");
        selection = input.next();
        return selection.toLowerCase();
    }

    public String searchBy(String choice) {
        try {
            switch (choice) {
                case "1":
                    return organizationDisplayAndSearch();
                case "2":
                    return userDisplayAndSearch();
                case "3":
                    return ticketDisplayAndSearch();
                case "q":
                    quit();
                default:
                    return mainMenu();
            }
        } catch (NumberFormatException e) {
            System.out.println("Oops! Invalid option!");
            return mainMenu();
        }
    }

    private String ticketDisplayAndSearch() {
        //Display Ticket Fields
        String ticketField = fieldsMenu(TICKET_MENU_OPTIONS);
        if ("q".equals(ticketField)) {
            quit();
        } else if (!"c".equals(ticketField)) {
            //Get search field and search value
            try {
                String searchField = TICKET_MENU_OPTIONS.get(Integer.parseInt(ticketField) - 1);
                String searchValue = enterSearchValue(searchField);
                System.out.println("Searching for '" + searchField + "' with value '" + searchValue + "'... ");
                //Search
                List<Ticket> tickets = TicketSearch.searchTickets(DataHolder.TICKET_FIELDS.get(searchField), searchValue);
                //Display Results
                displayTicketSearchResults(tickets);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops! Invalid search field!");
            }
        }
        // return to main menu if choice is 'c - Cancel' or after displaying results
        return mainMenu();
    }

    private String userDisplayAndSearch() {
        //Display User Fields
        String userField = fieldsMenu(USER_MENU_OPTIONS);
        if ("q".equals(userField)) {
            quit();
        } else if (!"c".equals(userField)) {
            //Get search field and search value
            try {
                String searchField = USER_MENU_OPTIONS.get(Integer.parseInt(userField) - 1);
                String searchValue = enterSearchValue(searchField);
                System.out.println("Searching for '" + searchField + "' with value '" + searchValue + "'... ");
                //Search
                List<User> users = UserSearch.searchUsers(DataHolder.USER_FIELDS.get(searchField), searchValue);
                //Display Results
                displayUserSearchResults(users);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops! Invalid search field!");
            }
        }
        // return to main menu if choice is 'c - Cancel' or after displaying results
        return mainMenu();
    }

    private String organizationDisplayAndSearch() {
        //Display Organization Fields
        String organizationField = fieldsMenu(ORGANIZATION_MENU_OPTIONS);
        if ("q".equals(organizationField)) {
            quit();
        } else if (!"c".equals(organizationField)) {
            //Get search field and search value
            try {
                String searchField = ORGANIZATION_MENU_OPTIONS.get(Integer.parseInt(organizationField) - 1);
                String searchValue = enterSearchValue(searchField);
                System.out.println("Searching for '" + searchField + "' with value '" + searchValue + "'... ");
                //Search
                List<Organization> organizations = OrganizationSearch.searchOrganizations(DataHolder.ORGANIZATION_FIELDS.get(searchField), searchValue);
                //Display Results
                displayOrganizationSearchResults(organizations);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops! Invalid search field!");
            }
        }
        // return to main menu if choice is 'c - Cancel' or after displaying results
        return mainMenu();
    }

    public void quit() {
        System.out.print("Are you sure you want to quite? y/n : ");
        Scanner valueInput = new Scanner(System.in);
        String ans = valueInput.next();
        if ("y".equals(ans.toLowerCase())) {
            System.out.println("\n\n *****  Thank you! See you next time. *****\n\n");
            System.exit(0);
        } else {
            mainMenu();
        }
    }

    private String fieldsMenu(List<String> fieldsOptions) {
        String fieldSelection;
        Scanner fieldInput = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println("Choose number of search field:");
        int itemNumber = 1;
        for (String option : fieldsOptions) {
            System.out.println(itemNumber++ + " - " + option);
        }

        System.out.println("c - Cancel");
        System.out.println("q - Quit");
        System.out.print("Enter the number of field option: ");

        fieldSelection = fieldInput.next();
        return fieldSelection.toLowerCase();
    }

    private String enterSearchValue(String searchField) {
        System.out.print("Enter value (" + searchField+  "):");
        Scanner valueInput = new Scanner(System.in).useDelimiter("\\n");
        String searchString = valueInput.next();
        return searchString;
    }

    private void displayOrganizationSearchResults(List<Organization> organizations) {

        System.out.println("\n-------- Result -------");
        System.out.println("=========================");
        if (organizations == null  || organizations.size() <= 0) {
            System.out.println("Search did not find any organization.");
        } else {
            System.out.println("Search found " + organizations.size() + " organization/s.");
            for (Organization organization : organizations) {
                System.out.println("------");
                System.out.println("ID: " + organization.getId());
                System.out.println("URL: " + organization.getUrl());
                System.out.println("EXTERNAL_ID: " + organization.getExternalId());
                System.out.println("NAME: " + organization.getName());
                System.out.println("DOMAIN NAMES: " + String.join(", ", organization.getDomainNames()));
                System.out.println("DATE CREATED: " + organization.getCreatedAt());
                System.out.println("DETAILS: " + organization.getDetails());
                System.out.println("SHARED TICKET?: " + organization.isSharedTickets());
                System.out.println("TAGS: " + String.join(", ", organization.getTags()));
                System.out.println();
            }
        }
    }

    public void displayUserSearchResults(List<User> users) {
        System.out.println("\n-------- Result -------");
        System.out.println("=========================");
        if (users == null  || users.size() <= 0) {
            System.out.println("Search did not find any user.");
        } else {
            System.out.println("Search found " + users.size() + " user/s.");
            for (User user : users) {
                System.out.println("------");
                System.out.println("ID: " + user.getId());
                System.out.println("URL: " + user.getUrl());
                System.out.println("EXTERNAL_ID: " + user.getExternalId());
                System.out.println("NAME: " + user.getName());
                System.out.println("ALIAS: " + user.getAlias());
                System.out.println("DATE CREATED: " + user.getCreatedAt());
                System.out.println("ACTIVE?: " + user.isActive());
                System.out.println("VERIFIED?: " + user.isVerified());
                System.out.println("SHARED?: " + user.isShared());
                System.out.println("LOCALE: " + (user.getLocale() != null ? user.getLocale().toString() : ""));
                System.out.println("TIMEZONE: " + user.getTimezone());
                System.out.println("LAST LOGIN: " + user.getLastLoginAt());
                System.out.println("EMAIL: " + user.getEmail());
                System.out.println("PHONE: " + user.getPhone());
                System.out.println("SIGNATURE: " + user.getSignature());

                if (user.getOrganization() != null) {
                    System.out.println("ORGANIZATION: " + user.getOrganization().getName() + "(ID: " + user.getOrganization().getId() + ")");
                } else if (user.getOrganizationId() != null) {
                    System.out.println("ORGANIZATION ID: " + user.getOrganizationId());
                } else {
                    System.out.println("ORGANIZATION : ");
                }

                System.out.println("TAGS: " + String.join(", ", user.getTags()));
                System.out.println("SUSPENDED?: " + user.isSuspended());
                System.out.println("ROLE: " + user.getRole());
                System.out.println();
            }
        }
    }

    public void displayTicketSearchResults(List<Ticket> tickets) {
        System.out.println("\n-------- Result -------");
        System.out.println("=========================");
        if (tickets == null  || tickets.size() <= 0) {
            System.out.println("Search did not find any ticket.");
        } else {
            System.out.println("Search found " + tickets.size() + " ticket/s.");
            for (Ticket ticket : tickets) {
                System.out.println("------");
                System.out.println("ID: " + ticket.getId());
                System.out.println("URL: " + ticket.getUrl());
                System.out.println("EXTERNAL_ID: " + ticket.getExternalId());
                System.out.println("DATE CREATED: " + ticket.getCreatedAt());
                System.out.println("TYPE: " + ticket.getType());
                System.out.println("SUBJECT: " + ticket.getSubject());
                System.out.println("DESCRIPTION: " + ticket.getDescription());
                System.out.println("PRIORITY: " + ticket.getPriority());
                System.out.println("STATUS: " + ticket.getStatus());

                if (ticket.getSubmitter() != null) {
                    System.out.println("SUBMITTER: " + ticket.getSubmitter().getName() + "(ID: " + ticket.getSubmitter().getId() + ")");
                } else if (ticket.getSubmitterId() != null) {
                    System.out.println("SUBMITTER ID: " + ticket.getSubmitterId());
                } else {
                    System.out.println("SUBMITTER : ");
                }

                if (ticket.getAssignee() != null) {
                    System.out.println("ASSIGNEE: " + ticket.getAssignee().getName() + "(ID: " + ticket.getAssignee().getId() + ")");
                } else if (ticket.getSubmitterId() != null) {
                    System.out.println("ASSIGNEE ID: " + ticket.getAssigneeId());
                } else {
                    System.out.println("ASSIGNEE : ");
                }

                if (ticket.getOrganization() != null) {
                    System.out.println("ORGANIZATION: " + ticket.getOrganization().getName() + "(ID: " + ticket.getOrganization().getId() + ")");
                } else if (ticket.getOrganizationId() != null) {
                    System.out.println("ORGANIZATION ID: " + ticket.getOrganizationId());
                } else {
                    System.out.println("ORGANIZATION : ");
                }

                System.out.println("TAGS: " + String.join(", ", ticket.getTags()));
                System.out.println("HAS INCIDENTS?: " +ticket.isHasIncidents());
                System.out.println("DATE DUE: " + ticket.getDueAt());
                System.out.println("LOGGED VIA: " + ticket.getVia());
                System.out.println();
            }
        }
    }
}
