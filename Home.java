import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Home extends JFrame implements ActionListener {
    private JLabel l1;
    private JButton b1, b2;
    public CardLayout cardLayout = new CardLayout();
    public JPanel mainPanel, homPanel;
    private HomeFacade facade;

    public Home() {
        facade = new HomeFacade(this);
        // Clear the cache file at the start
        facade.clearCacheFile();
        facade.clearFeeCacheFile();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        homPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Font f1 = new Font("Arial", Font.BOLD, 20);
        Font f2 = new Font("Arial", Font.ITALIC, 15);

        l1 = new JLabel("Welcome to the MMU Course Enrollment Manager");
        l1.setFont(f1);
        homPanel.add(l1, gbc);

        b1 = new JButton("Financial Packages");
        b2 = new JButton("Course Manager");

        b1.setFont(f2);
        b2.setFont(f2);
        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.WHITE);
        b1.setBackground(Color.DARK_GRAY);
        b2.setBackground(Color.DARK_GRAY);
        b1.setPreferredSize(new Dimension(200, 50));
        b2.setPreferredSize(new Dimension(200, 50));

        gbc.gridy = 1;
        homPanel.add(b1, gbc);
        gbc.gridy = 2;
        homPanel.add(b2, gbc);

        homPanel.setBackground(Color.WHITE);
        mainPanel.add(homPanel, "Home");

        b1.addActionListener(this);
        b2.addActionListener(this);

        add(mainPanel);
        setTitle("Course Enrollment Calculator");
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            facade.showFinancialPanel();
        } else if (e.getSource() == b2) {
            facade.showCourseSelectionPanel();
        } else {
            System.out.println("Invalid Button");
        }
    }

    public void clearCacheFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database/cache.txt"))) {
            writer.write(""); // Write an empty string to clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearFeeCacheFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database/feecache.txt"))) {
            writer.write(""); // Write an empty string to clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainPanel() {
        cardLayout.show(mainPanel, "Home");
    }

    public void showCourseSelectionPanel() {
        mainPanel.add(new CourseSelection(this), "CourseSelection");
        cardLayout.show(mainPanel, "CourseSelection");
    }

    public void showFinancialPanel() {
        mainPanel.add(new FinancialPackages(this), "FinancialPackages");
        cardLayout.show(mainPanel, "FinancialPackages");
    }

    public void showSelectedPanel() {
        mainPanel.add(new SelectedCourse(this), "SelectedCourse");
        cardLayout.show(mainPanel, "SelectedCourse");
    }

    public void showInvoicePanel() {
        mainPanel.add(new Invoice(this), "Invoice");
        cardLayout.show(mainPanel, "Invoice");
    }
}
