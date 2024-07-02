import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MMUApp extends JFrame implements ActionListener{
    private JButton prevPageButton, nextPageButton;
    

    public MMUApp() {
        // Clear the cache file at the start
        clearCacheFile();

        // Create the ribbon panel
        JPanel ribbonPanel = new JPanel(new BorderLayout());
        ribbonPanel.setPreferredSize(new Dimension(800, 20));
        ribbonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create the "Previous Page" button
        prevPageButton = new JButton("Previous Page");
        prevPageButton.setPreferredSize(new Dimension(120, 25));
        prevPageButton.addActionListener(this);
        // Create the "Next Page" button
        nextPageButton = new JButton("Next Page");
        nextPageButton.setPreferredSize(new Dimension(120, 25));
        nextPageButton.addActionListener(this); 

    
        ribbonPanel.add(prevPageButton, BorderLayout.WEST);
        ribbonPanel.add(nextPageButton, BorderLayout.EAST);
        add(ribbonPanel, BorderLayout.NORTH);

        // Create the main panel with a BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Create panels for each level
        mainPanel.add(createLevelPanel("Level 1: Remedial courses, Matriculation", getCoursesByLevel(1)));
        mainPanel.add(createLevelPanel("Level 2: Undergraduate", getCoursesByLevel(2)));
        mainPanel.add(createLevelPanel("Level 3: Postgraduate", getCoursesByLevel(3)));

        // Add vertical glue for spacing
        mainPanel.add(Box.createVerticalGlue());

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);

        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle actions if needed
        if(e.getSource()==prevPageButton){
        Home obj = new Home();
                    obj.pack();
                    obj.setVisible(true);
                    obj.setTitle("Home");
                    obj.setSize(800, 650);
                    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    dispose();
        }
        
        else if(e.getSource()==nextPageButton){
           FinancialPackages obj = new FinancialPackages();
            obj.pack();
            obj.setVisible(true);
            obj.setTitle("Financial Packages");
            obj.setSize(800, 650);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
        }
    }

    private void clearCacheFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database/cache.txt"))) {
            writer.write(""); // Write an empty string to clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel createLevelPanel(String levelName, List<Course> courses) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(levelName));
        panel.setPreferredSize(new Dimension(800, 200)); // Fixed size for each panel

        // Create a table to display the courses
        String[] columnNames = {"Code", "Name", "Duration", "Price", "Add Course"};
        Object[][] data = new Object[courses.size()][5];

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            data[i][0] = course.getCourseCode();
            data[i][1] = course.getCourseName();
            data[i][2] = course.getCourseDuration();
            data[i][3] = "RM" + course.getCourseFee();
            data[i][4] = "Add Course"; // Placeholder text for the button
        }

        JTable courseTable = new JTable(new DefaultTableModel(data, columnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the "Add Course" column is editable
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return column == 4 ? new ButtonRenderer() : super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                return column == 4 ? new ButtonEditor(new JCheckBox(), courses.get(row), MMUApp.this) : super.getCellEditor(row, column);
            }
        };

        courseTable.setFillsViewportHeight(true);
        courseTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        // Set column widths
        courseTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Code column
        courseTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column
        courseTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Duration column
        courseTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Price column
        courseTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Add Course column

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add the scroll pane to the main level panel
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void addCourseToCache(Course course) {
        List<Course> cacheCourses = getCoursesFromFile("database/cache.txt");

        // Check for existing course with the same SeniorityLevel
        Course existingCourse = null;
        for (Course c : cacheCourses) {
            if (c.getSeniorityLevel() == course.getSeniorityLevel()) {
                existingCourse = c;
                break;
            }
        }

        // Remove the existing course if found
        if (existingCourse != null) {
            cacheCourses.remove(existingCourse);
            System.out.println("Removed: " + existingCourse.getCourseName() + " (Seniority Level: " + existingCourse.getSeniorityLevel() + ")");
        }

        // Add the new course to the cache
        cacheCourses.add(course);
        writeCoursesToFile(cacheCourses, "database/cache.txt");
        System.out.println("Added: " + course.getCourseName() + " (Seniority Level: " + course.getSeniorityLevel() + ")");
    }

    private List<Course> getCoursesByLevel(int level) {
        return getCoursesFromFile("database/courses.txt").stream()
                .filter(course -> course.getSeniorityLevel() == level)
                .collect(Collectors.toList());
    }

    private List<Course> getCoursesFromFile(String fileName) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    courses.add(new Course(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private void writeCoursesToFile(List<Course> courses, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Course course : courses) {
                writer.write(course.getCourseCode() + "," +
                        course.getCourseName() + "," +
                        course.getCourseDuration() + "," +
                        course.getSeniorityLevel() + "," +
                        course.getCourseFee());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MMUApp::new);
    }
}
