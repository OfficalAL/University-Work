package bcu.GroupA5.librarysystem.main;

import bcu.GroupA5.librarysystem.commands.Command;
import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.model.Library;
import java.io.*;
import java.time.LocalDate;

public class Main {
    /**
     * Entry point for the console application. This class boots the
     * application by loading persistent data, then reading user commands
     * from standard input. For the coursework we intentionally separate
     * the CLI (`Main`) from the GUI (`LoadGUI`) to keep both interaction
     * modes simple and demonstrable.
     */

    public static void main(String[] args) throws IOException, LibraryException {
        
        Library library = LibraryData.load();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Library system");
        System.out.println("Enter 'help' to see a list of available commands.");
        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            if (line.equals("exit")) {
                break;
            }

            try {
                Command command = CommandParser.parse(line);
                command.execute(library, LocalDate.now());                
            } catch (LibraryException ex) {
                System.out.println(ex.getMessage());
            }
        }
        LibraryData.store(library);
        System.exit(0);
    }
}