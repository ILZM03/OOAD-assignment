import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class FinancialPackages extends JPanel {

    public FinancialPackages(Home home) {
        setLayout(new BorderLayout());
    
    // Create the ribbon panel
    JPanel ribbonPanel = new JPanel();
    ribbonPanel.setLayout(new BorderLayout());
    ribbonPanel.setPreferredSize(new Dimension(800, 25));
    ribbonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Create the "Previous Page" button
     JButton prevPageButton = new JButton("Previous Page");
     prevPageButton.setPreferredSize(new Dimension(120, 25));
     prevPageButton.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
     home.showMainPanel();
        
    }
});
        // Add your components here
        JLabel label = new JLabel("Welcome to Financial Packages");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Add the ribbon panel to the top
        add(ribbonPanel, BorderLayout.NORTH);
        ribbonPanel.add(prevPageButton, BorderLayout.WEST);
    }
}