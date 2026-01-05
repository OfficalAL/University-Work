package bcu.GroupA5.librarysystem.data;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import java.io.IOException;

public interface DataManager {
    /**
     * DataManager abstracts loading and storing of different resource
     * types (books, patrons, loans). This separation allows swapping the
     * underlying storage format later without changing the domain logic.
     */
    
    public static final String SEPARATOR = "::";
    
    public void loadData(Library library) throws IOException, LibraryException;
    public void storeData(Library library) throws IOException;
}