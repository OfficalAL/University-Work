package bcu.GroupA5.librarysystem.data;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new BookDataManager());
        
        /* Uncomment the two lines below when the implementation of their 
        loadData() and storeData() methods is complete */
        dataManagers.add(new PatronDataManager());
        dataManagers.add(new LoanDataManager());
    }
    
    public static Library load() throws LibraryException, IOException {

        Library library = new Library();
        for (DataManager dm : dataManagers) {
            dm.loadData(library);
        }
        return library;
    }

    public static void store(Library library) throws IOException {

        /*
         * Centralised persistence entry point. Commands call this method
         * to flush current in-memory state to disk immediately after any
         * mutating operation. Keeping persistence in one place makes it
         * easier to change storage format later (e.g. JSON, DB) and keeps
         * command code focused on domain logic with a single persistence
         * call.
         */
        for (DataManager dm : dataManagers) {
            dm.storeData(library);
        }
    }
    
}