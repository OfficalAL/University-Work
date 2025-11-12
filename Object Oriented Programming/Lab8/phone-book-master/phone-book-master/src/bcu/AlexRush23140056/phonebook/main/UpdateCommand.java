package bcu.AlexRush23140056.phonebook.main;

import bcu.AlexRush23140056.phonebook.model.*;

/**
 * UpdateCommand implements the Command pattern for updating phone book entries.
 * 
 * Design choice: Follows the same structure as AddCommand for consistency.
 * Encapsulates both validation (in constructor) and execution logic.
 * This makes the code more maintainable and testable.
 * 
 * Package name changed from 'changeme' to 'AlexRush23140056' as unique identifier.
 */
public class UpdateCommand implements Command {
    private final String name;
    private final String phoneNumber;
    
    /**
     * Constructor validates command format and extracts parameters.
     * 
     * Design choice: Validation happens at construction time to fail fast.
     * This follows the "fail fast" principle - detect errors as early as possible.
     * 
     * @param parts Array of command parts: ["update", "name", "phoneNumber"]
     * @throws InvalidCommandException if command doesn't have exactly 3 parts
     */
    public UpdateCommand(String[] parts) throws InvalidCommandException {
        if(parts.length != 3) {
            throw new InvalidCommandException();
        }
        this.name = parts[1];
        this.phoneNumber = parts[2];
    }
    
    /**
     * Executes the update operation on the phone book.
     * 
     * Design choice: Delegates actual business logic to PhoneBook model class.
     * This maintains separation of concerns - Command handles UI/parsing, Model handles data.
     */
    @Override
    public void execute(PhoneBook phoneBook) throws NotPresentException {
        phoneBook.updateEntry(name, phoneNumber);
        System.out.println("Entry updated.");
    }
}