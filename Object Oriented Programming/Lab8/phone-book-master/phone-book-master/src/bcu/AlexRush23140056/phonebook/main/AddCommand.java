package bcu.AlexRush23140056.phonebook.main;

import bcu.AlexRush23140056.phonebook.model.*;

/**
 * AddCommand implements the Command pattern for adding new phone book entries.
 * This was the example provided in the task specification.
 * 
 * Design choice: Encapsulates validation and execution logic for add operations.
 * Constructor validates command format and stores parameters.
 * Execute method performs the actual business operation.
 * 
 * Package name changed from 'changeme' to 'AlexRush23140056' as unique identifier.
 */
public class AddCommand implements Command {
    private final String name;
    private final String phoneNumber;
    
    /**
     * Constructor validates command format and extracts parameters.
     * 
     * Design choice: Validation at construction time follows "fail fast" principle.
     * Fields are final to ensure immutability after construction.
     * 
     * @param parts Array of command parts: ["add", "name", "phoneNumber"]
     * @throws InvalidCommandException if command doesn't have exactly 3 parts
     */
    public AddCommand(String[] parts) throws InvalidCommandException {
        if(parts.length != 3) { 
            throw new InvalidCommandException();
        }
        this.name = parts[1];
        this.phoneNumber = parts[2];
    }
    
    /**
     * Executes the add operation on the phone book.
     * 
     * Design choice: Delegates to PhoneBook model for actual data manipulation.
     * This maintains separation of concerns between UI/command handling and data logic.
     * Provides consistent user feedback with original implementation.
     */
    @Override
    public void execute(PhoneBook phoneBook) throws AlreadyPresentException {
        phoneBook.addEntry(name, phoneNumber);
        System.out.println("Entry added.");
    }
}