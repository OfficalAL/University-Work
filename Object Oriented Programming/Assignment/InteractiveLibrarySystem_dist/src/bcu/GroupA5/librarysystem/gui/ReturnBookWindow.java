package bcu.GroupA5.librarysystem.gui;

import bcu.GroupA5.librarysystem.commands.Command;
import bcu.GroupA5.librarysystem.commands.ReturnBook;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ReturnBookWindow extends JFrame implements ActionListener {
    /**
     * GUI window to return a book. The window collects user input and
     * executes the `ReturnBook` command. GUI windows act as thin clients
     * that delegate real work to command objects — this keeps business
     * logic testable and concentrated in the model/command classes.
     */
    final private MainWindow mw;
    private JComboBox<Book> bookCombo;
    private JComboBox<Patron> patronCombo;
    final private JButton returnBtn = new JButton("Return");
    final private JButton cancelBtn = new JButton("Cancel");

    public ReturnBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        setTitle("Return Book");
        setSize(400, 150);
        setLocationRelativeTo(mw);
        setLayout(new BorderLayout());

        Library library = mw.getLibrary();
        bookCombo = new JComboBox<>(library.getBooks().stream().filter(Book::isOnLoan).toArray(Book[]::new));
        patronCombo = new JComboBox<>(library.getPatrons().toArray(new Patron[0]));

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Book: "));
        formPanel.add(bookCombo);
        formPanel.add(new JLabel("Patron: "));
        formPanel.add(patronCombo);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(returnBtn);
        buttonPanel.add(cancelBtn);

        returnBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnBtn) {
            try {
                Book book = (Book) bookCombo.getSelectedItem();
                Patron patron = (Patron) patronCombo.getSelectedItem();
                Command ret = new ReturnBook(patron.getId(), book.getId());
                ret.execute(mw.getLibrary(), null);
                mw.displayBooks();
                this.setVisible(false);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Please select both a book and a patron.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException | LibraryException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }
}
