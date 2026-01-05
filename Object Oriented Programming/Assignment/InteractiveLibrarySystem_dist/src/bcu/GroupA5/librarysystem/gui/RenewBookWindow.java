package bcu.GroupA5.librarysystem.gui;

import bcu.GroupA5.librarysystem.commands.Command;
import bcu.GroupA5.librarysystem.commands.RenewBook;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;

/**
 * Window for renewing a book loan.
 */
public class RenewBookWindow extends JFrame implements ActionListener {
    /**
     * Simple GUI for renewing loans. Uses `RenewBook` command to update
     * domain state. UI contains minimal validation and delegates persistence
     * to the command's storage logic for consistency.
     */
    private final MainWindow mw;
    private JComboBox<Book> bookCombo;
    private JComboBox<Patron> patronCombo;
    private final JTextField newDueDateText = new JTextField();
    private final JButton renewBtn = new JButton("Renew");
    private final JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs the RenewBookWindow.
     * @param mw the main window
     */
    public RenewBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the window components and layout.
     */
    private void initialize() {
        setTitle("Renew Book Loan");
        setSize(400, 180);
        setLocationRelativeTo(mw);
        setLayout(new BorderLayout());

        Library library = mw.getLibrary();
        bookCombo = new JComboBox<>(library.getBooks().stream().filter(Book::isOnLoan).toArray(Book[]::new));
        patronCombo = new JComboBox<>(library.getPatrons().toArray(new Patron[0]));

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Book: "));
        formPanel.add(bookCombo);
        formPanel.add(new JLabel("Patron: "));
        formPanel.add(patronCombo);
        formPanel.add(new JLabel("New Due Date (YYYY-MM-DD): "));
        formPanel.add(newDueDateText);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(renewBtn);
        buttonPanel.add(cancelBtn);

        renewBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == renewBtn) {
            try {
                Book book = (Book) bookCombo.getSelectedItem();
                Patron patron = (Patron) patronCombo.getSelectedItem();
                LocalDate newDueDate = LocalDate.parse(newDueDateText.getText().trim());
                if (book == null || patron == null) {
                    JOptionPane.showMessageDialog(this, "Please select both a book and a patron.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Command renew = new RenewBook(patron.getId(), book.getId(), newDueDate);
                renew.execute(mw.getLibrary(), LocalDate.now());
                mw.displayBooks();
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }
}
