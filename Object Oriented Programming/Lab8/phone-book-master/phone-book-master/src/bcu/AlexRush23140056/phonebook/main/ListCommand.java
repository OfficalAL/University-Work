package bcu.AlexRush23140056.phonebook.main;

import bcu.AlexRush23140056.phonebook.model.*;
import java.util.List;

/**
 * ListCommand implements the Command pattern for listing all phone book entries.
 * 
 * Design choice: This command takes no parameters (only "list"), but still needs
 * to validate the command format for consistency with other commands.
 * 
 * Package name changed from 'changeme' to 'AlexRush23140056' as unique identifier.
 */
public class ListCommand implements Command {
    
    /**
     * Constructor validates that list command has no additional parameters.
     * 
     * Design choice: Even though list takes no parameters, we still validate
     * to ensure command format consistency and catch user errors like "list extra".
     * 
     * @param parts Array of command parts, should only contain ["list"]
     * @throws InvalidCommandException if command has more than 1 part
     */
    public ListCommand(String[] parts) throws InvalidCommandException {
        if(parts.length != 1) {
            throw new InvalidCommandException();
        }
    }
    
    /**
     * Executes the list operation on the phone book.
     * 
     * Design choice: Replicates the exact logic from original parseAndExecute method.
     * Shows "No entries." when empty, otherwise lists all names.
     * This maintains the exact same user experience as before refactoring.
     */
    @Override
    public void execute(PhoneBook phoneBook) {
        List<String> names = phoneBook.getAllNames();
        if(names.isEmpty()) {
            System.out.println("No entries.");
        } else {
            for(String name : names) {
                System.out.println(name);
            }
        }
    }
}