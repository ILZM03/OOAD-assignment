import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MMUApp extends JFrame {

    public MMUApp() {
        setTitle("Course Enrollment Calculator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the main panel with a GridLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3)); // 1 row, 3 columns

        // Create panels for each level
        JPanel level1Panel = createLevelPanel("Level 1: Remedial courses, Matriculation", new String[]{"English Remedial", "Mathematics Remedial", "Engineering Matriculation", "IT Matriculation"});
        JPanel level2Panel = createLevelPanel("Level 2: Undergraduate", new String[]{"Engineering Undergraduate", "IT Undergraduate", "Business Undergraduate", "Law Undergraduate"});
        JPanel level3Panel = createLevelPanel("Level 3: Postgraduate", new String[]{"Engineering Postgraduate", "IT Postgraduate", "Business Postgraduate", "Law Postgraduate"});

        // Add the level panels to the main panel
        mainPanel.add(level1Panel);
        mainPanel.add(level2Panel);
        mainPanel.add(level3Panel);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createLevelPanel(String levelName, String[] courses) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(levelName));
        panel.setLayout(new BorderLayout());

        // Create a combo box for the courses
        JComboBox<String> courseComboBox = new JComboBox<>(courses);

        // Create an "Add Course" button
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                JOptionPane.showMessageDialog(panel, "Added course: " + selectedCourse);
                // Add logic to actually add the course to the panel or list
            }
        });

        // Create a panel to hold the combo box and button
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(courseComboBox, BorderLayout.NORTH);
        controlPanel.add(addCourseButton, BorderLayout.SOUTH);

        // Add the control panel to the main panel
        panel.add(controlPanel, BorderLayout.NORTH);

        // Placeholder label for now
        JLabel placeholderLabel = new JLabel("Subjects will be added here.");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(placeholderLabel, BorderLayout.CENTER);

        return panel;
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
