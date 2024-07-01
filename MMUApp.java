import javax.swing.*;
import java.awt.*;

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
        JPanel level1Panel = createLevelPanel("Level 1");
        JPanel level2Panel = createLevelPanel("Level 2");
        JPanel level3Panel = createLevelPanel("Level 3");

        // Add the level panels to the main panel
        mainPanel.add(level1Panel);
        mainPanel.add(level2Panel);
        mainPanel.add(level3Panel);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createLevelPanel(String levelName) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(levelName));
        panel.setLayout(new BorderLayout());

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
