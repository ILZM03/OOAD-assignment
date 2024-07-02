import javax.swing.*;
import java.awt.*;

public class FinancialPackages extends JPanel {

    public FinancialPackages() {
        setLayout(new BorderLayout());

        // Add your components here
        JLabel label = new JLabel("Welcome to Financial Packages");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}