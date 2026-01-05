package bcu.GroupA5.librarysystem.gui;

import bcu.GroupA5.librarysystem.commands.BorrowBook;
import bcu.GroupA5.librarysystem.commands.Command;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;

public class BorrowBookWindow extends JFrame implements ActionListener {
    /**
     * GUI window to collect patron ID, book ID, and due date to perform
     * a borrow. The window delegates to `BorrowBook` which enforces
     * business rules (limit, loan creation) and performs persistence.
     */
    private MainWindow mw;
    private JComboBox<Book> bookCombo;
    private JComboBox<Patron> patronCombo;
    private JTextField dueDateText = new JTextField();
    private JButton borrowBtn = new JButton("Borrow");
    private JButton cancelBtn = new JButton("Cancel");

    public BorrowBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        setTitle("Borrow Book");
        setSize(400, 200);
        setLocationRelativeTo(mw);
        setLayout(new BorderLayout());

        Library library = mw.getLibrary();
        bookCombo = new JComboBox<>(library.getBooks().stream().filter(b -> !b.isOnLoan()).toArray(Book[]::new));
        patronCombo = new JComboBox<>(library.getPatrons().toArray(new Patron[0]));

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Book: "));
        formPanel.add(bookCombo);
        formPanel.add(new JLabel("Patron: "));
        formPanel.add(patronCombo);
        formPanel.add(new JLabel("Due Date (YYYY-MM-DD): "));
        formPanel.add(dueDateText);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(borrowBtn);
        buttonPanel.add(cancelBtn);

        borrowBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == borrowBtn) {
            try {
                Book book = (Book) bookCombo.getSelectedItem();
                Patron patron = (Patron) patronCombo.getSelectedItem();
                LocalDate dueDate = LocalDate.parse(dueDateText.getText());
                Command borrow = new BorrowBook(patron.getId(), book.getId(), dueDate);
                borrow.execute(mw.getLibrary(), LocalDate.now());
                mw.displayBooks();
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }
}
