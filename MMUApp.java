import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MMUApp extends JFrame {

    public MMUApp() {
        setTitle("Course Enrollment Calculator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false); // Make the window non-resizable

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

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createLevelPanel(String levelName, List<Course> courses) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(levelName));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 200)); // Fixed size for each panel

        // Create a container panel with a vertical BoxLayout to hold course cards
        JPanel courseContainer = new JPanel();
        courseContainer.setLayout(new BoxLayout(courseContainer, BoxLayout.Y_AXIS));

        for (Course course : courses) {
            // Create a panel for each course to make it look like a card
            JPanel coursePanel = new JPanel();
            coursePanel.setLayout(new GridBagLayout());
            coursePanel.setBorder(new LineBorder(Color.BLACK, 1, true));
            coursePanel.setBackground(Color.WHITE);
            coursePanel.setPreferredSize(new Dimension(750, 50)); // Fixed size for each card
            coursePanel.setBorder(new EmptyBorder(5, 20, 5, 20)); // Add padding around the text area

            // Create labels for course details
            JLabel codeLabel = new JLabel(course.getCourseCode());
            JLabel courseLabel = new JLabel(course.getCourseName());
            JLabel durationLabel = new JLabel(course.getCourseDuration());
            JLabel priceLabel = new JLabel("RM" + course.getCourseFee());

            // Define GridBagConstraints
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 10, 0, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // Add labels to course panel with separators
            gbc.gridx = 0;
            coursePanel.add(codeLabel, gbc);

            gbc.gridx = 1;
            coursePanel.add(new JSeparator(SwingConstants.VERTICAL), gbc);

            gbc.gridx = 2;
            coursePanel.add(courseLabel, gbc);

            gbc.gridx = 3;
            coursePanel.add(new JSeparator(SwingConstants.VERTICAL), gbc);

            gbc.gridx = 4;
            coursePanel.add(durationLabel, gbc);

            gbc.gridx = 5;
            coursePanel.add(new JSeparator(SwingConstants.VERTICAL), gbc);

            gbc.gridx = 6;
            coursePanel.add(priceLabel, gbc);

            courseContainer.add(coursePanel);
            courseContainer.add(Box.createVerticalStrut(5)); // Reduce space between cards
        }

        // Create a scroll pane for the course container
        JScrollPane scrollPane = new JScrollPane(courseContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
