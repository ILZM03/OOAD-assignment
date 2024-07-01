import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MMUApp extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MMUApp() {
        setTitle("Course Enrollment Calculator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create and add the home panel with buttons
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns, 10px gap

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "AddCourse");
            }
        });

        JButton editPromoButton = new JButton("Edit Promo");
        editPromoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "EditPromo");
            }
        });

        homePanel.add(addCourseButton);
        homePanel.add(editPromoButton);

        // Create instances of AddCourse and EditPromo panels
        AddCourse addCoursePanel = new AddCourse();
        EditPromo editPromoPanel = new EditPromo();

        // Add panels to the main panel with CardLayout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(addCoursePanel, "AddCourse");
        mainPanel.add(editPromoPanel, "EditPromo");

        // Show the home panel initially
        cardLayout.show(mainPanel, "Home");

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
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
