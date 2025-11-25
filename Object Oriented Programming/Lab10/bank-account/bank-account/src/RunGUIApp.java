import javax.swing.*;

public class RunGUIApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAccountGUI gui = new BankAccountGUI();
            gui.setVisible(true);
        });
    }
}
