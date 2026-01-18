package bcu.GroupA5.librarysystem.main;

import bcu.GroupA5.librarysystem.commands.AddBook;
import bcu.GroupA5.librarysystem.commands.Command;
import bcu.GroupA5.librarysystem.commands.Help;
import bcu.GroupA5.librarysystem.commands.ListBooks;
import bcu.GroupA5.librarysystem.commands.LoadGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

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
                // CLI support for adding a book (added 18 Jan 2026)
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Title: ");
                String title = br.readLine();
                System.out.print("Author: ");
                String author = br.readLine();
                System.out.print("Publication Year: ");
                String publicationYear = br.readLine();
                return new AddBook(title, author, publicationYear);
            } else if (cmd.equals("addpatron")) {
                // CLI support for adding a patron (added 18 Jan 2026)
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Name: ");
                String name = br.readLine();
                System.out.print("Email: ");
                String email = br.readLine();
                // You may need to adjust constructor args for AddPatron
                return new bcu.GroupA5.librarysystem.commands.AddPatron(name, email);
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                // CLI support for listbooks, listpatrons, help (added 18 Jan 2026)
                switch (line) {
                    case "listbooks":
                        return new ListBooks();
                    case "listpatrons":
                        return new bcu.GroupA5.librarysystem.commands.ListPatrons();
                    case "help":
                        return new Help();
                    default:
                        break;
                }
            } else if (parts.length == 2) {
                // CLI support for showbook and showpatron (enabled 18 Jan 2026)
                int id = Integer.parseInt(parts[1]);
                if (cmd.equals("showbook")) {
                    // Implemented showbook CLI command (18 Jan 2026)
                    return new bcu.GroupA5.librarysystem.commands.ShowBook(id);
                } else if (cmd.equals("showpatron")) {
                    // Implemented showpatron CLI command (18 Jan 2026)
                    return new bcu.GroupA5.librarysystem.commands.ShowPatron(id);
                }
            } else if (parts.length == 3) {
                // CLI support for borrow, renew, return (added 18 Jan 2026)
                int id1 = Integer.parseInt(parts[1]);
                int id2 = Integer.parseInt(parts[2]);
                switch (cmd) {
                    case "borrow":
                        return new bcu.GroupA5.librarysystem.commands.BorrowBook(id1, id2, LocalDate.now());
                    case "renew":
                        return new bcu.GroupA5.librarysystem.commands.RenewBook(id1, id2, LocalDate.now());
                    case "return":
                        return new bcu.GroupA5.librarysystem.commands.ReturnBook(id1, id2);
                    default:
                        break;
                }
            }
        } catch (NumberFormatException ex) {
            // Error handling for invalid ID format (added 18 Jan 2026)
            throw new LibraryException("Invalid ID format.");
        }

        throw new LibraryException("Invalid command.");
    }
}