package bcu.GroupA5.librarysystem.main;

import bcu.GroupA5.librarysystem.commands.*;
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
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Title: ");
                String title = br.readLine();
                System.out.print("Author: ");
                String author = br.readLine();
                System.out.print("Publication Year: ");
                String publicationYear = br.readLine();
                System.out.print("Publisher: ");
                String publisher = br.readLine();
                
                return new AddBook(title, author, publicationYear, publisher);

            } else if (cmd.equals("addpatron")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Name: ");
                String name = br.readLine();
                System.out.print("Phone: ");
                String phone = br.readLine();
                System.out.print("Email: ");
                String email = br.readLine();
                
                return new AddPatron(name, phone, email);

            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
                
            } else if (parts.length == 1) {
                switch (line) {
                    case "listbooks": return new ListBooks();
                    case "listpatrons": return new ListPatrons();
                    case "help": return new Help();
                }
                
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showbook")) {
                    return new ShowBook(id);
                } else if (cmd.equals("showpatron")) {
                    return new ShowPatron(id);
                } 
                else if (cmd.equals("deletebook")) {
                    return new DeleteBook(id);
                } else if (cmd.equals("deletepatron")) {
                    return new DeletePatron(id);
                }

            } else if (parts.length == 3) {
                int patronId = Integer.parseInt(parts[1]);
                int bookId = Integer.parseInt(parts[2]);
                LocalDate dueDate = LocalDate.now().plusDays(14); 

                if (cmd.equals("borrow")) {
                    return new BorrowBook(patronId, bookId, dueDate);
                } else if (cmd.equals("renew")) {
                    return new RenewBook(patronId, bookId, dueDate);
                } else if (cmd.equals("return")) {
                    return new ReturnBook(patronId, bookId);
                }
            }
        } catch (NumberFormatException ex) {
            throw new LibraryException("Invalid ID format.");
        }

        throw new LibraryException("Invalid command.");
    }
}