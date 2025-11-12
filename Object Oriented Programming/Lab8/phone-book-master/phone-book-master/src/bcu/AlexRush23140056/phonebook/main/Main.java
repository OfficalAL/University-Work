package bcu.AlexRush23140056.phonebook.main;

import bcu.AlexRush23140056.phonebook.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class for the Phone Book application.
 * 
 * named AlexRush23140056.
 */
public class Main {
	public static final String HELP_MESSAGE = """
		Commands:
			add [name] [phoneNumber]        add a new entry
			show [name]                     show an entry
			update [name] [phoneNumber]     update an entry
			remove [name]                   remove an entry
			list                            show all names
			help                            show this help message
			exit                            exit the program""";
	
	public static void main(String[] args) throws IOException {
		new Main().run();
	}
	
	private final PhoneBook phoneBook;
	
	public Main() {
		this.phoneBook = new PhoneBook();
	}
	
	public Main(PhoneBook phoneBook) {
		this.phoneBook = phoneBook;
	}
	
	public void run() throws IOException {
		BufferedReader keyboard = new BufferedReader(
			new InputStreamReader(System.in)
		);
		
		System.out.println("Address book");
		while(true) {
			System.out.print("> ");
			String command = keyboard.readLine();
			if("exit".equalsIgnoreCase(command)) {
				break;
			}
			
			try {
				parseAndExecute(command);
			} catch(AlreadyPresentException ex) {
				System.out.println("The entry for " + ex.getName() + " already exists.");
			} catch(NotPresentException ex) {
				System.out.println("The entry for " + ex.getName() + " does not exist.");
			} catch(InvalidCommandException ex) {
				System.out.println("Invalid command (enter 'help' to see the valid commands).");
			}
		}
	}
	
	/**
	 * Parses a command string and returns the appropriate Command object.
	 * This method replaces the parsing logic from the original parseAndExecute method.
	 * 
	 * Design choice: Separated parsing from execution to follow Single Responsibility Principle.
	 * Each Command class now encapsulates its own validation and execution logic.
	 * This makes the code more modular and easier to test individual commands.
	 * 
	 * @param command The command string to parse (e.g., "add John 12345")
	 * @return A Command object that can be executed later
	 * @throws InvalidCommandException if the command format is invalid
	 */
	public Command parse(String command) throws InvalidCommandException {
		String[] parts = command.split(" ");
		String firstPart = parts[0];
		
		// Using Command pattern - each command type returns its specific Command implementation
		if("add".equalsIgnoreCase(firstPart)) {
			return new AddCommand(parts);
		} else if("show".equalsIgnoreCase(firstPart)) {
			return new ShowCommand(parts);
		} else if("update".equalsIgnoreCase(firstPart)) {
			return new UpdateCommand(parts);
		} else if("remove".equalsIgnoreCase(firstPart)) {
			return new RemoveCommand(parts);
		} else if("list".equalsIgnoreCase(firstPart)) {
			return new ListCommand(parts);
		} else if("help".equalsIgnoreCase(firstPart)) {
			return new HelpCommand(parts);
		} else {
			throw new InvalidCommandException();
		}
	}
	
	
	/**
	 * Refactored parseAndExecute method using Command pattern.
	 * 
	 * Design choice: This method now delegates to parse() and execute() methods.
	 * This separation allows for easier testing of parsing logic separately from execution.
	 * The method signature remains unchanged to maintain backward compatibility with existing tests.
	 * 
	 * @param command The command string to parse and execute
	 * @throws AlreadyPresentException if trying to add an entry that already exists
	 * @throws NotPresentException if trying to access an entry that doesn't exist
	 * @throws InvalidCommandException if the command format is invalid
	 */
	public void parseAndExecute(String command) throws AlreadyPresentException, NotPresentException, InvalidCommandException {
		Command cmd = parse(command);
		cmd.execute(phoneBook);
	}
}