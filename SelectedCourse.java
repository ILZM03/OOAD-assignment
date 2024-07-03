import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SelectedCourse extends JPanel {
    
    private JTable optionalFeeTable;
    public SelectedCourse(Home home) {
        setLayout(new BorderLayout());

        // Create the ribbon panel
        JPanel ribbonPanel = new JPanel();
        ribbonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
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
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        ribbonPanel.add(prevPageButton, gbc);

        // Home Page Button
        JButton homePageButton = new JButton("Home Page");
        homePageButton.setPreferredSize(new Dimension(120, 25));
        homePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.showMainPanel();
            }
        });
        gbc.weightx = 0.5;
        gbc.gridx = 1;
        ribbonPanel.add(homePageButton, gbc);

        // Next Page Button
        JButton nextPageButton = new JButton("Next Page");
        nextPageButton.setPreferredSize(new Dimension(120, 25));
        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.showInvoicePanel();
            }
        });
        gbc.weightx = 0.5;
        gbc.gridx = 3;
        ribbonPanel.add(nextPageButton, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 25));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFeesToCache();
                JOptionPane.showMessageDialog(null, "Fees saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gbc.weightx = 0.5;
        gbc.gridx = 2;
        ribbonPanel.add(saveButton, gbc);

        // Create the main panel 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout

        JPanel selectedPanel = createSelectedCoursePanel("Selected Course", getCourses());
        JPanel mandFeePanel = createFeePanel("Mandatory Fees (RM):", getFeeByLevel("Mandatory"));
        JPanel optFeePanel = createFeePanel("Optional Fees (RM):", getOptionalFees(getFeeByLevel("Optional")));
        mainPanel.add(selectedPanel);
        mainPanel.add(mandFeePanel);
        mainPanel.add(optFeePanel);

        // Add vertical glue for spacing
        mainPanel.add(Box.createVerticalGlue());
        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        
        // Add the ribbon panel to the top
        add(ribbonPanel, BorderLayout.NORTH);
        
        
    }
        

    private JPanel createSelectedCoursePanel(String levelName, List<Course> courses) {
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
                data[i][4] = "RM " + course.getCourseFee();
            }

            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Disable all cells from being editable
                }
            };
    
            JTable courseTable = new JTable(tableModel);
            courseTable.setFillsViewportHeight(true);
            courseTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
    
            // Set column widths
            courseTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Code column
            courseTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column
            courseTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Duration column
            courseTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Level column
            courseTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Price column
    
            DefaultTableCellRenderer rigRenderer = new DefaultTableCellRenderer();
            rigRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            courseTable.getColumnModel().getColumn(4).setCellRenderer(rigRenderer);
        
            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(courseTable);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
            // Add the scroll pane to the main level panel
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(800, 400));
    
            return panel;
        }

    private JPanel createFeePanel(String levelName, List<Fee> fees) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(levelName));
        String[] columnNames;
        Object[][] data;

        if (levelName.equals("Optional Fees (RM):")) {
            columnNames = new String[]{"Type", "Name", "Select", "Price"};
            data = new Object[fees.size()][4];

            for (int i = 0; i < fees.size(); i++) {
                OptionalFee fee = (OptionalFee) fees.get(i);
                data[i][0] = fee.getFeeType();
                data[i][1] = fee.getFeeName();
                data[i][2] = false;
                data[i][3] = "RM " + fee.getFeeAmount();
            }
        } else {
            columnNames = new String[]{"Type", "Name", "Price"};
            data = new Object[fees.size()][3];
    
            for (int i = 0; i < fees.size(); i++) {
                Fee fee = fees.get(i);
                data[i][0] = fee.getFeeType();
                data[i][1] = fee.getFeeName();
                data[i][2] = "RM " + fee.getFeeAmount();
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (levelName.equals("Optional Fees (RM):") && column == 2) {
                    return Boolean.class; // Checkbox column
                }
                return super.getColumnClass(column);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return levelName.equals("Optional Fees (RM):") && column == 2; // Only the checkbox column is editable for optional fees
            }
        };

        JTable feeTable = new JTable(model);

            feeTable.setFillsViewportHeight(true);
            feeTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
    
            // Set column widths
            
            if (levelName.equals("Optional Fees (RM):")) {
                feeTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Type column
                feeTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column
                feeTable.getColumnModel().getColumn(2).setPreferredWidth(10); // Checkbox column for optional fees
                feeTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Price column

                DefaultTableCellRenderer rigRenderer = new DefaultTableCellRenderer();
                rigRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                feeTable.getColumnModel().getColumn(3).setCellRenderer(rigRenderer);
            }
            else {
                feeTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Type column
                feeTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column
                feeTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Price column
                DefaultTableCellRenderer rigRenderer = new DefaultTableCellRenderer();
                rigRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                feeTable.getColumnModel().getColumn(2).setCellRenderer(rigRenderer);
            }
            
    
            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(feeTable);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
            // Add the scroll pane to the main level panel
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(800, 200));

            if (levelName.equals("Optional Fees (RM):")) {
                optionalFeeTable = feeTable;
            }
    
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

    private List<Fee> getOptionalFees(List<Fee> fees) {
        List<Fee> optionalFees = new ArrayList<>();
        for (Fee fee : fees) {
            if (fee.getFeeType().equals("Optional")) {
                optionalFees.add(new OptionalFee(fee.getFeeType(), fee.getFeeName(), fee.getFeeAmount(),false));
            }
        }
        return optionalFees;
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

    private void addFeesToCache(){
        List<Fee> allFees = new ArrayList<>();
        allFees.addAll(getSelectedOptionalFees());
        allFees.addAll(getMandatoryFees());
        writeFeeToFile(allFees, "database/feecache.txt");
    }
    private List<OptionalFee> getSelectedOptionalFees(){
        DefaultTableModel model = (DefaultTableModel) optionalFeeTable.getModel();
        List<OptionalFee> selectedFees = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 2);
            if (isSelected != null && isSelected) {
                String feeType = (String) model.getValueAt(i, 0);
                String feeName = (String) model.getValueAt(i, 1);
                Double feeAmount = Double.parseDouble(((String) model.getValueAt(i, 3)).substring(2));
                selectedFees.add(new OptionalFee(feeType, feeName, feeAmount, isSelected));
            }
        }
    return selectedFees;
    }
    
    private List<Fee> getMandatoryFees(){
        
        return getFeeByLevel("Mandatory");
    }

    private void writeFeeToFile(List<Fee> fees, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Fee fee : fees) {
                writer.write(fee.getFeeType() + "," + 
                fee.getFeeName() + "," + 
                fee.getFeeAmount());

            writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
}
    }
}
