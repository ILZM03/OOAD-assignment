import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


public class SelectedCourse extends JPanel {

  
    public SelectedCourse(Home home) {
        
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
                home.showMMUPanel();
            }
        });
        
        // Home Page Button
        JButton homePageButton = new JButton("Home Page");
        homePageButton.setPreferredSize(new Dimension(120, 25));
        homePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.showMainPanel();
            }
        });

        //Next Page Button
        JButton nextPageButton = new JButton("Next Page");
        nextPageButton.setPreferredSize(new Dimension(120, 25));
        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
        
        // Create the main panel 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout

        JPanel selectedPanel = (createLevelPanel("Selected Course", getCourses()));
        JPanel mandFeePanel = (createLevelPanel2("Mandatory Fees (RM):",getFeeByLevel("Mandatory")));
        JPanel optFeePanel = (createLevelPanel2("Optional Fees (RM):", getFeeByLevel("Optional")));
        mainPanel.add(selectedPanel);
        mainPanel.add(mandFeePanel);
        mainPanel.add(optFeePanel);

        // Add vertical glue for spacing
        mainPanel.add(Box.createVerticalGlue());

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        

        // Add the ribbon panel to the top
        add(ribbonPanel, BorderLayout.NORTH);
        ribbonPanel.add(homePageButton, BorderLayout.CENTER);
        ribbonPanel.add(nextPageButton, BorderLayout.EAST);
        ribbonPanel.add(prevPageButton, BorderLayout.WEST);
    }
        
    
    private JPanel createLevelPanel(String levelName, List<Course> courses) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder(levelName));
            // Create a table to display the cache
            String[] columnNames = {"Code", "Name", "Duration", "Level", "Price"};
            Object[][] data = new Object[courses.size()][5];
    
            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);
                data[i][0] = course.getCourseCode();
                data[i][1] = course.getCourseName();
                data[i][2] = course.getCourseDuration();
                data[i][3] = course.getSeniorityLevel();
                data[i][4] = "RM" + course.getCourseFee();
            }
    
            JTable courseTable = new JTable(new DefaultTableModel(data, columnNames));

            courseTable.setFillsViewportHeight(true);
            courseTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
    
            // Set column widths
            courseTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Code column
            courseTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column
            courseTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Duration column
            courseTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Level column
            courseTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Price column
    
            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(courseTable);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
            // Add the scroll pane to the main level panel
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(800, 400));
    
            return panel;
        }

        private JPanel createLevelPanel2(String levelName, List<Fee> fees) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createTitledBorder(levelName));
            // Create a table to display the cache
            String[] columnNames = {"Type", "Name", "Price"};
            Object[][] data = new Object[fees.size()][3];
    
            for (int i = 0; i < fees.size(); i++) {
                Fee fee = fees.get(i);
                data[i][0] = fee.getFeeType();
                data[i][1] = fee.getFeeName();
                data[i][2] = "RM" +fee.getFeeAmount();
                
            }
    
            JTable feeTable = new JTable(new DefaultTableModel(data, columnNames));

            feeTable.setFillsViewportHeight(true);
            feeTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
    
            // Set column widths
            feeTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Type column
            feeTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column
            feeTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Price column
    
            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(feeTable);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
            // Add the scroll pane to the main level panel
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(800, 200));
    
            return panel;
        }
    
        

    private List<Course> getCourses() {
        return getCoursesFromFile("database/cache.txt").stream()
                .collect(Collectors.toList());
    }

    private List<Fee> getFeeByLevel(String level){
         return getFeesFromFile("database/fee.txt").stream()
                .filter(fee -> fee.getFeeType().equals(level))
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

    // Method to read fee information from a file
    private  List<Fee> getFeesFromFile(String fileName) {
        List<Fee> fees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Fee fee = new Fee(parts[0], parts[1], Double.parseDouble(parts[2]));
                    fees.add(fee);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return fees;
    }

    
}