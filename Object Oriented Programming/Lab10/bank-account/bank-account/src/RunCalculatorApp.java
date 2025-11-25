import javax.swing.*;

public class RunCalculatorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI gui = new CalculatorGUI();
            gui.setVisible(true);
        });
    }
}
