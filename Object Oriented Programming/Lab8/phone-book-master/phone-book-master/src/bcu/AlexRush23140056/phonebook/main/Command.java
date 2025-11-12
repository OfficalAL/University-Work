package bcu.AlexRush23140056.phonebook.main;
import bcu.AlexRush23140056.phonebook.model.*;

/**
 * Command interface for implementing the Command pattern in the phone book application.
 * 
 * Package name changed from 'changeme' to 'AlexRush23140056' as unique identifier.
 * This follows the task requirement to rename the package to identify the work as yours.
 */ 
public interface Command {
	public void execute (PhoneBook phoneBook) 
			throws AlreadyPresentException, NotPresentException;
	
}