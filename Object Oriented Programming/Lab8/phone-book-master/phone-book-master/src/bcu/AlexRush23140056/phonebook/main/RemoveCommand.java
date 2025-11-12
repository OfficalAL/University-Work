package bcu.AlexRush23140056.phonebook.main;

import bcu.AlexRush23140056.phonebook.model.*;

/**
 * RemoveCommand implements the Command pattern for removing phone book entries.
 * 
 * Design choice: Similar to ShowCommand, this only needs a name parameter.
 * Follows consistent validation and execution pattern with other commands.
 * 
 * Package name changed from 'changeme' to 'AlexRush23140056' as unique identifier.
 */
public class RemoveCommand implements Command {
    private final String name;
    
    /**
     * Constructor validates command format and extracts the name parameter.
     * 
     * Design choice: Remove only requires name, similar to show command.
     * Validates exactly 2 parts: ["remove", "name"]
     * 
     * @param parts Array of command parts
     * @throws InvalidCommandException if command doesn't have exactly 2 parts
     */
    public RemoveCommand(String[] parts) throws InvalidCommandException {
        if(parts.length != 2) {
            throw new InvalidCommandException();
        }
        this.name = parts[1];
    }
    
    /**
     * Executes the remove operation on the phone book.
     * 
     * Design choice: Delegates to PhoneBook.removeEntry() method.
     * Provides user feedback via System.out.println consistent with other commands.
     */
    @Override
    public void execute(PhoneBook phoneBook) throws NotPresentException {
        phoneBook.removeEntry(name);
        System.out.println("Entry removed.");
    }
}