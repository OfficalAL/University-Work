package bcu.AlexRush23140056.phonebook.main;

import bcu.AlexRush23140056.phonebook.model.*;

/**
 * HelpCommand implements the Command pattern for displaying help information.
 * 
 * Design choice: Similar to ListCommand, takes no parameters but validates format.
 * Accesses the static HELP_MESSAGE from Main class to maintain consistency
 * with the original implementation.
 * 
 * Package name changed from 'changeme' to 'AlexRush23140056' as unique identifier.
 */
public class HelpCommand implements Command {
    
    /**
     * Constructor validates that help command has no additional parameters.
     * 
     * Design choice: Consistent validation pattern with other commands.
     * Ensures users can't accidentally add parameters to help command.
     * 
     * @param parts Array of command parts, should only contain ["help"]
     * @throws InvalidCommandException if command has more than 1 part
     */
    public HelpCommand(String[] parts) throws InvalidCommandException {
        if(parts.length != 1) {
            throw new InvalidCommandException();
        }
    }
    
    /**
     * Executes the help command by displaying the help message.
     * 
     * Design choice: References Main.HELP_MESSAGE to maintain single source of truth
     * for help text. This ensures consistency if help text is ever updated.
     * Alternative would be to duplicate the string, but that violates DRY principle.
     */
    @Override
    public void execute(PhoneBook phoneBook) {
        System.out.println(Main.HELP_MESSAGE);
    }
}