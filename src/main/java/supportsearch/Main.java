package supportsearch;

import supportsearch.view.SearchView;

public class Main {

    public static void main(String[] args) {
        SearchView searchView = new SearchView();

        System.out.println("\n\n *****  WELCOME! *****" );
        String choice = searchView.mainMenu();
        while (!"q".equals(choice)) {
            choice = searchView.searchBy(choice);
        }

        searchView.quit();
    }
}
