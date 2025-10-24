import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField display;
    private double firstNumber = 0;
    private String operator = "";
    private boolean isOperatorClicked = false;

    public CalculatorApp() {
        setTitle("🧮 Stylish Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Display field
        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 28));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Buttons panel
        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = createStyledButton(text);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setFocusPainted(false);
        button.setBackground(new Color(220, 220, 220));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(180, 180, 255));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(220, 220, 220));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9]")) {
            if (isOperatorClicked) {
                display.setText("");
                isOperatorClicked = false;
            }
            display.setText(display.getText() + input);
        } else if (input.matches("[+\\-*/]")) {
            if (!display.getText().isEmpty()) {
                firstNumber = Double.parseDouble(display.getText());
                operator = input;
                isOperatorClicked = true;
            }
        } else if (input.equals("=")) {
            if (!display.getText().isEmpty()) {
                double secondNumber = Double.parseDouble(display.getText());
                double result = 0;

                switch (operator) {
                    case "+": result = firstNumber + secondNumber; break;
                    case "-": result = firstNumber - secondNumber; break;
                    case "*": result = firstNumber * secondNumber; break;
                    case "/":
                        if (secondNumber != 0) {
                            result = firstNumber / secondNumber;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }

                display.setText(String.valueOf(result));
                isOperatorClicked = true;
            }
        } else if (input.equals("C")) {
            display.setText("");
            firstNumber = 0;
            operator = "";
            isOperatorClicked = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorApp());
    }
}