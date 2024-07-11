import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FinancialPackages extends JPanel {
    private JTable table;
    private JLabel label;
    private Timer timer;
    private HomeFacade homeFacade;

    public FinancialPackages(Home home) {
        this.homeFacade = new HomeFacade(home);
        setLayout(new BorderLayout());

        // Create the ribbon panel
        JPanel ribbonPanel = new JPanel();
        ribbonPanel.setLayout(new BorderLayout());
        ribbonPanel.setPreferredSize(new Dimension(800, 25));
        ribbonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create the "Previous Page" button
        JButton prevPageButton = new JButton("Previous Page");
        prevPageButton.setPreferredSize(new Dimension(120, 25));
        prevPageButton.addActionListener(e -> homeFacade.showMainPanel());

        // Add your components here
        label = new JLabel("Welcome to Financial Packages");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Add the ribbon panel to the top
        add(ribbonPanel, BorderLayout.NORTH);
        ribbonPanel.add(prevPageButton, BorderLayout.WEST);

        // Create the table data
        String[] columnNames = {"Course", "Level", "Owner", "Types"};
        Object[][] data = readTableDataFromFile("database/scholarship.txt");

        // Create the table
        table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setPreferredWidth(320); // Course
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Level
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Owner
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // Types
        JScrollPane scrollPane = new JScrollPane(table);

        // Timer to switch from JLabel to JTable
        timer = new Timer(500, e -> {
            // Remove the label and add the table
            remove(label);
            add(scrollPane, BorderLayout.CENTER); // Add the table to the center of the panel
            revalidate();
            repaint();
            });
        timer.setRepeats(false); // Only execute once
        timer.start();
    }

    private Object[][] readTableDataFromFile(String filename) {
        List<Object[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line.split(", "));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList.toArray(new Object[0][]);
    }
}
