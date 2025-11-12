package bcu.AlexRush23140056.phonebook.main;
import bcu.AlexRush23140056.phonebook.model.*;

/**
 * ShowCommand implements the Command pattern for displaying phone book entries.
 * 
 * Package name changed from 'changeme' to 'AlexRush23140056' as unique identifier.
 */
public class ShowCommand implements Command{
    private final String name;
    
	public ShowCommand(String[] parts) throws InvalidCommandException {
		if(parts.length != 2) {
			throw new InvalidCommandException();
		}
        this.name = parts[1];
		
	} 

	@Override
	public void execute(PhoneBook phoneBook) throws NotPresentException {
		PhoneBookEntry entry = phoneBook.getEntry(name);
		System.out.println("Name: " + entry.getName());
		System.out.println("Phone number: " + entry.getPhoneNumber());
	}
}