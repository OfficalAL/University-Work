import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame implements ActionListener {
    private final JTextField num1Field = new JTextField(8);
    private final JTextField num2Field = new JTextField(8);
    private final JButton addButton = new JButton("+");
    private final JButton subButton = new JButton("-");
    private final JButton mulButton = new JButton("*");
    private final JButton divButton = new JButton("/");
    private final JLabel resultLabel = new JLabel("Result: ");

    public CalculatorGUI() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 180);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Num 1:"));
        inputPanel.add(num1Field);
        inputPanel.add(new JLabel("Num 2:"));
        inputPanel.add(num2Field);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(subButton);
        buttonPanel.add(mulButton);
        buttonPanel.add(divButton);

        JPanel resultPanel = new JPanel();
        resultPanel.add(resultLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        subButton.addActionListener(this);
        mulButton.addActionListener(this);
        divButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String num1Txt = num1Field.getText().trim();
        String num2Txt = num2Field.getText().trim();
        double num1, num2;
        try {
            num1 = Double.parseDouble(num1Txt);
            num2 = Double.parseDouble(num2Txt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double result = 0;
        if (e.getSource() == addButton) {
            result = num1 + num2;
        } else if (e.getSource() == subButton) {
            result = num1 - num2;
        } else if (e.getSource() == mulButton) {
            result = num1 * num2;
        } else if (e.getSource() == divButton) {
            if (num2 == 0) {
                JOptionPane.showMessageDialog(this, "Cannot divide by zero.", "Math Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            result = num1 / num2;
        }
        resultLabel.setText(String.format("Result: %.2f", result));
    }
}
