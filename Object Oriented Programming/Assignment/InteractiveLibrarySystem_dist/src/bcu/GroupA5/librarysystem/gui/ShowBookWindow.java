package bcu.GroupA5.librarysystem.gui;

import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Loan;
import java.awt.*;
import javax.swing.*;

public class ShowBookWindow extends JFrame {
    /**
     * GUI window showing detailed Book information. Read-only presentation
     * that reflects the Book/Loan state. Actions that change state are
     * performed via Commands to keep UI and domain logic separate.
     */
    public ShowBookWindow(Book book) {
        setTitle("Book Details");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.add(new JLabel("ID: " + book.getId()));
        infoPanel.add(new JLabel("Title: " + book.getTitle()));
        infoPanel.add(new JLabel("Author: " + book.getAuthor()));
        infoPanel.add(new JLabel("Publication Year: " + book.getPublicationYear()));
        infoPanel.add(new JLabel("Publisher: " + book.getPublisher()));
        infoPanel.add(new JLabel("Status: " + (book.isOnLoan() ? "On Loan" : "Available")));

        if (book.isOnLoan() && book.getLoan() != null) {
            Loan loan = book.getLoan();
            infoPanel.add(new JLabel("Due Date: " + loan.getDueDate()));
            infoPanel.add(new JLabel("Borrowed By: " + loan.getPatron().getName()));
        }

        add(infoPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
