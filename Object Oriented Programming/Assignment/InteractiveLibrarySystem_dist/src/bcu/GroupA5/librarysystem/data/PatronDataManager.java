package bcu.GroupA5.librarysystem.data;

import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.main.LibraryException;
import java.io.IOException;

public class PatronDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/patrons.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        // TODO: implementation here
    }

    @Override
    public void storeData(Library library) throws IOException {
        // TODO: implementation here
    }
}