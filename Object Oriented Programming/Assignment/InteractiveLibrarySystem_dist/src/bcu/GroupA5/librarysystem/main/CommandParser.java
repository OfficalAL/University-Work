package bcu.GroupA5.librarysystem.main;

import bcu.GroupA5.librarysystem.commands.AddBook;
import bcu.GroupA5.librarysystem.commands.Command;
import bcu.GroupA5.librarysystem.commands.Help;
import bcu.GroupA5.librarysystem.commands.ListBooks;
import bcu.GroupA5.librarysystem.commands.LoadGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandParser {
    /**
     * CommandParser interprets textual commands from the CLI and converts
     * them into `Command` objects. Kept intentionally simple to match the
     * coursework specification: parsing is manual and synchronous so that
     * the focus remains on demonstrating command responsibilities and
     * persistence behaviour rather than building a complex parsing layer.
     */
    
    public static Command parse(String line) throws IOException, LibraryException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            if (cmd.equals("addbook")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Title: ");
                String title = br.readLine();
                System.out.print("Author: ");
                String author = br.readLine();
                System.out.print("Publication Year: ");
                String publicationYear = br.readLine();
                
                return new AddBook(title, author, publicationYear);
            } else if (cmd.equals("addpatron")) {
                
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                switch (line) {
                    case "listbooks":
                        return new ListBooks();
                    case "listpatrons":
                        break;
                    case "help":
                        return new Help();
                    default:
                        break;
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showbook")) {
                    
                } else if (cmd.equals("showpatron")) {
                    
                }
            } else if (parts.length == 3) {

                switch (cmd) {
                    case "borrow":
                        break;
                    case "renew":
                        break;
                    case "return":
                        break;
                    default:
                        break;
                }
            }
        } catch (NumberFormatException ex) {

        }

        throw new LibraryException("Invalid command.");
    }
}