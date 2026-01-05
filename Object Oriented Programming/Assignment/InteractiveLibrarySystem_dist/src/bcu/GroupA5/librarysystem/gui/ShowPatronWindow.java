package bcu.GroupA5.librarysystem.gui;

import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Patron;
import java.awt.*;
import javax.swing.*;

public class ShowPatronWindow extends JFrame {
    /**
     * GUI window that displays a Patron's details, current loans and loan
     * history. The GUI uses the command objects for any state changing
     * operations; this window is read-only to respect the separation
     * between view and command logic required by the coursework.
     */
    public ShowPatronWindow(Patron patron) {
        setTitle("Patron Details");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.add(new JLabel("ID: " + patron.getId()));
        infoPanel.add(new JLabel("Name: " + patron.getName()));
        infoPanel.add(new JLabel("Phone: " + patron.getPhone()));
        infoPanel.add(new JLabel("Email: " + patron.getEmail()));
        infoPanel.add(new JLabel("Books on Loan: " + patron.getBooks().size()));

        StringBuilder booksList = new StringBuilder();
        for (Book book : patron.getBooks()) {
            booksList.append(book.getTitle()).append(" (ID: ").append(book.getId()).append(")\n");
        }
        JTextArea booksArea = new JTextArea(booksList.toString());
        booksArea.setEditable(false);
        booksArea.setBorder(BorderFactory.createTitledBorder("Books Borrowed"));

        // Loan history
        StringBuilder historyList = new StringBuilder();
        historyList.append("Title | Start | Due | Returned | Status\n");
        patron.getLoanHistory().forEach(loan -> {
            historyList.append(loan.getBook().getTitle()).append(" | ")
                .append(loan.getStartDate()).append(" | ")
                .append(loan.getDueDate()).append(" | ")
                .append(loan.getReturnDate() != null ? loan.getReturnDate() : "-").append(" | ")
                .append(loan.isTerminated() ? "Returned" : "Active").append("\n");
        });
        JTextArea historyArea = new JTextArea(historyList.toString());
        historyArea.setEditable(false);
        historyArea.setBorder(BorderFactory.createTitledBorder("Loan History"));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(new JScrollPane(booksArea));
        centerPanel.add(new JScrollPane(historyArea));

        add(infoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
