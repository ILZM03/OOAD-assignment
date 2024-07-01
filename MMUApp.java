import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MMUApp extends JFrame {

    public MMUApp() {
        setTitle("Course Enrollment Calculator");
        setSize(800, 620); // Increase height to fit the ribbon
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false); // Make the window non-resizable

        // Create the ribbon panel
        JPanel ribbonPanel = new JPanel();
        ribbonPanel.setLayout(new BorderLayout());
        ribbonPanel.setPreferredSize(new Dimension(800, 20));
        ribbonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create the "Next Page" button
        JButton nextPageButton = new JButton("Next Page");
        nextPageButton.setPreferredSize(new Dimension(100, 20));
        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open FinancialPackages.java
                new FinancialPackages().setVisible(true);
                dispose();
            }
        });

        // Add the button to the ribbon panel
        ribbonPanel.add(nextPageButton, BorderLayout.EAST);

        // Create the main panel with a BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Create panels for each level
        JPanel level1Panel = createLevelPanel("Level 1: Remedial courses, Matriculation", getCoursesByLevel(1));
        JPanel level2Panel = createLevelPanel("Level 2: Undergraduate", getCoursesByLevel(2));
        JPanel level3Panel = createLevelPanel("Level 3: Postgraduate", getCoursesByLevel(3));

        // Add the level panels to the main panel
        mainPanel.add(level1Panel);
        mainPanel.add(level2Panel);
        mainPanel.add(level3Panel);

        // Add some vertical glue to ensure spacing
        mainPanel.add(Box.createVerticalGlue());

        // Add the ribbon panel to the frame
        add(ribbonPanel, BorderLayout.NORTH);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createLevelPanel(String levelName, List<Course> courses) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(levelName));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 200)); // Fixed size for each panel

        // Create a table to display the courses
        String[] columnNames = {"Code", "Name", "Duration", "Price"};
        String[][] data = new String[courses.size()][4];

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            data[i][0] = course.getCourseCode();
            data[i][1] = course.getCourseName();
            data[i][2] = course.getCourseDuration();
            data[i][3] = "RM" + course.getCourseFee();
        }

        JTable courseTable = new JTable(data, columnNames);
        courseTable.setFillsViewportHeight(true);
        courseTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        // Set column widths
        courseTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Code column

        courseTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column

        courseTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Duration column

        courseTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Price column

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add the scroll pane to the main level panel
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private List<Course> getCoursesByLevel(int level) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("database/courses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && Integer.parseInt(parts[3]) == level) {
                    courses.add(new Course(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static void main(String[] args) {
        // Create and display the main window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MMUApp().setVisible(true);
            }
        });
    }
}