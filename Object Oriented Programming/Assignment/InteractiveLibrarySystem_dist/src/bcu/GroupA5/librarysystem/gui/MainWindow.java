package bcu.GroupA5.librarysystem.gui;
import bcu.GroupA5.librarysystem.commands.Command;
import bcu.GroupA5.librarysystem.commands.DeleteBook;
import bcu.GroupA5.librarysystem.commands.DeletePatron;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {
    /**
     * Primary GUI container providing menus and table views. The main
     * window is deliberately lightweight: it builds views from the
     * `Library` model and spawns dedicated windows for mutating actions
     * which then execute Command objects to modify state and persist.
     */

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        JMenuItem booksRenew = new JMenuItem("Renew");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        booksMenu.add(booksRenew);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }
        booksRenew.addActionListener(this);

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Patrons");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
//    public static void main(String[] args) throws IOException, LibraryException {
//        Library library = LibraryData.load();
//        new MainWindow(library);			
//    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
        } else if (ae.getSource() == booksDel) {
            String input = JOptionPane.showInputDialog(this, "Enter Book ID to delete:");
            if (input != null) {
                try {
                    int bookId = Integer.parseInt(input);
                    Command delBook = new DeleteBook(bookId);
                    delBook.execute(library, null);
                    displayBooks();
                } catch (LibraryException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (ae.getSource() == booksIssue) {
            new BorrowBookWindow(this);
        } else if (ae.getSource() == booksReturn) {
            new ReturnBookWindow(this);
        } else if (ae.getSource().toString().contains("Renew")) {
            new RenewBookWindow(this);
        } else if (ae.getSource() == memView) {
            displayPatrons();
        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);
        } else if (ae.getSource() == memDel) {
            String input = JOptionPane.showInputDialog(this, "Enter Patron ID to delete:");
            if (input != null) {
                try {
                    int patronId = Integer.parseInt(input);
                    Command delPatron = new DeletePatron(patronId);
                    delPatron.execute(library, null);
                    displayPatrons();
                } catch (LibraryException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void displayPatrons() {
        List<bcu.GroupA5.librarysystem.model.Patron> patronsList = library.getPatrons();
        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "Books on Loan"};
        Object[][] data = new Object[patronsList.size()][5];
        for (int i = 0; i < patronsList.size(); i++) {
            bcu.GroupA5.librarysystem.model.Patron patron = patronsList.get(i);
            data[i][0] = patron.getId();
            data[i][1] = patron.getName();
            data[i][2] = patron.getPhone();
            data[i][3] = patron.getEmail();
            data[i][4] = patron.getBooks().size();
        }
        JTable table = new JTable(data, columns);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String title = (String) table.getValueAt(row, 0);
                        Book book = null;
                        for (Book b : library.getBooks()) {
                            if (b.getTitle().equals(title)) {
                                book = b;
                                break;
                            }
                        }
                        if (book != null) {
                            new ShowBookWindow(book);
                        }
                    }
                }
            }
        });
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    public void displayBooks() {
        List<Book> booksList = library.getBooks();
        // headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status"};

        Object[][] data = new Object[booksList.size()][4];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = book.getPublicationYear();
            data[i][3] = book.getStatus();
        }

        JTable table = new JTable(data, columns);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String title = (String) table.getValueAt(row, 0);
                        Book book = null;
                        for (Book b : booksList) {
                            if (b.getTitle().equals(title)) {
                                book = b;
                                break;
                            }
                        }
                        if (book != null) {
                            new ShowBookWindow(book);
                        }
                    }
                }
            }
        });
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }	
}