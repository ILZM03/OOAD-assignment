import javax.swing.*;
import java.awt.*;

public class FinancialPackages extends JFrame {

    public FinancialPackages() {
        setTitle("Financial Packages");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add your components here
        JLabel label = new JLabel("Welcome to Financial Packages");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Create and display the FinancialPackages window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FinancialPackages().setVisible(true);
            }
        });
    }
}
