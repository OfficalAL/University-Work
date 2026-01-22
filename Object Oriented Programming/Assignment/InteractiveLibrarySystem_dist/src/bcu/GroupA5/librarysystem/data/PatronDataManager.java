package bcu.GroupA5.librarysystem.data;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PatronDataManager implements DataManager {
    /**
     * Loads and stores Patron records. Email and phone fields are persisted
     * alongside the name and ID using the same simple text format as books.
     * Keeping per-resource managers allows focused parsing and clearer
     * error reporting when files are malformed.
     */

    private final String RESOURCE = "./resources/data/patrons.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    String phone = properties[2];
                    String email = properties.length > 3 ? properties[3] : "";
                    
                    Patron patron = new Patron(id, name, phone, email);
                    
                    // [FIX] Load the 'isDeleted' flag
                    if (properties.length > 4) {
                        boolean isDeleted = Boolean.parseBoolean(properties[4]);
                        patron.setDeleted(isDeleted);
                    }
                    
                    library.addPatron(patron);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Patron patron : library.getPatrons()) {
                out.print(patron.getId() + SEPARATOR);
                out.print(patron.getName() + SEPARATOR);
                out.print(patron.getPhone() + SEPARATOR);
                out.print(patron.getEmail() + SEPARATOR);
                
                // [FIX] This line was missing!
                out.print(patron.isDeleted() + SEPARATOR);
                
                out.println();
            }
        }
    }
}