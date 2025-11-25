
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// This class extends JFrame and implements ActionListener
public class BankAccountGUI extends JFrame implements ActionListener {
	// UI components
	private final JLabel amountLabel = new JLabel("Amount:");
	private final JTextField amountField = new JTextField(10);
	private final JButton depositButton = new JButton("Deposit");
	private final JButton withdrawButton = new JButton("Withdraw");
	private final JLabel balanceLabel = new JLabel("Balance: £0.00");
	// Panels
	private final JPanel topPanel = new JPanel();
	private final JPanel middlePanel = new JPanel();
	private final JPanel bottomPanel = new JPanel();
	// BankAccount object
	private final BankAccount myAccount = new BankAccount("123456", "John Doe");

	public BankAccountGUI() {
		setTitle("Bank Account GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// Top panel: amount label and field
		topPanel.setLayout(new FlowLayout());
		topPanel.add(amountLabel);
		topPanel.add(amountField);

		// Middle panel: deposit and withdraw buttons
		middlePanel.setLayout(new FlowLayout());
		middlePanel.add(depositButton);
		middlePanel.add(withdrawButton);

		// Bottom panel: balance label
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(balanceLabel);

		// Add panels to frame
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		// Add action listeners
		depositButton.addActionListener(this);
		withdrawButton.addActionListener(this);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String amountTxt = amountField.getText().trim();
		if (amountTxt.length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter an amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		double amount = 0;
		try {
			amount = Double.parseDouble(amountTxt);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (amount <= 0) {
			JOptionPane.showMessageDialog(this, "Amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (e.getSource() == depositButton) {
			myAccount.deposit(amount);
		} else if (e.getSource() == withdrawButton) {
			if (amount > myAccount.getBalance()) {
				JOptionPane.showMessageDialog(this, "Insufficient funds.", "Transaction Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			myAccount.withdraw(amount);
		}
		balanceLabel.setText(String.format("Balance: £%.2f", myAccount.getBalance()));
		amountField.setText("");
	}
}
