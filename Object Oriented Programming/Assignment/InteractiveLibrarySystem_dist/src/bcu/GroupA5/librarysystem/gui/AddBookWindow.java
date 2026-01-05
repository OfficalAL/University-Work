package bcu.GroupA5.librarysystem.gui;

import bcu.GroupA5.librarysystem.commands.AddBook;
import bcu.GroupA5.librarysystem.commands.Command;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddBookWindow extends JFrame implements ActionListener {
    /**
     * GUI window for adding books. Collects book metadata and invokes the
     * `AddBook` command. Keeping the GUI thin and pushing persistence to
     * command objects simplifies testing and centralises file IO handling.
     */

    // Reference to main window (ensure correct import and classpath)
    private final MainWindow mw;
    private final JTextField titleText = new JTextField();
    private final JTextField authText = new JTextField();
    private final JTextField pubDateText = new JTextField();
    private final JTextField publisherText = new JTextField();

    private final JButton addBtn = new JButton("Add");
    private final JButton cancelBtn = new JButton("Cancel");

    public AddBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the contents of the AddBookWindow frame.
     * Sets up the UI components and layout.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Log or handle exception if needed
        }

        setTitle("Add a New Book");
        setSize(300, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2));
        topPanel.add(new JLabel("Title : "));
        topPanel.add(titleText);
        topPanel.add(new JLabel("Author : "));
        topPanel.add(authText);
        topPanel.add(new JLabel("Publishing Date : "));
        topPanel.add(pubDateText);
        topPanel.add(new JLabel("Publisher : "));
        topPanel.add(publisherText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    /**
     * Handles the logic for adding a new book using the input fields.
     * Performs basic validation and executes the AddBook command.
     */
    private void addBook() {
        String title = titleText.getText().trim();
        String author = authText.getText().trim();
        String publicationYear = pubDateText.getText().trim();
        String publisher = publisherText.getText().trim();

        // Basic input validation
        if (title.isEmpty() || author.isEmpty() || publicationYear.isEmpty() || publisher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // create and execute the AddBook Command
            Command addBook = new AddBook(title, author, publicationYear, publisher);
            addBook.execute(mw.getLibrary(), LocalDate.now());
            // refresh the view with the list of books
            mw.displayBooks();
            // hide (close) the AddBookWindow
            this.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}