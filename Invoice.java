import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.io.*;


public class Invoice extends JPanel {

    private String[] columnNames = { "Code", "Name", "Duration", "Level", "Price", "Discount", "Total" };
    private List<Object[]> data = getCombinedData();

    public Invoice(Home home) {
        setLayout(new BorderLayout());

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
                home.showSelectedPanel();

            }
        });
        // Home Page Button
        JButton homePageButton = new JButton("Next Student");
        homePageButton.setPreferredSize(new Dimension(120, 25));
        homePageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // getTotal();
                home.clearCacheFile();
                home.clearFeeCacheFile();
                home.showMainPanel();
            }
        });

        

        JPanel invoicePanel = new JPanel();
        invoicePanel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][0]), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        JTable invoiceTable = new JTable(model);
        invoiceTable.setFillsViewportHeight(true);
        invoiceTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        // Set column widths
        invoiceTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Code column
        invoiceTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Name column
        invoiceTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Duration column
        invoiceTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Level column
        invoiceTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Price column
        invoiceTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Discount column
        invoiceTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Total column

        JScrollPane invoiceScrollPane = new JScrollPane(invoiceTable);
        invoicePanel.add(invoiceScrollPane, BorderLayout.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        invoiceTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        invoiceTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        invoiceTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);


        add(invoicePanel, BorderLayout.CENTER);

        // Add the ribbon panel to the top
        add(ribbonPanel, BorderLayout.NORTH);
        ribbonPanel.add(prevPageButton, BorderLayout.WEST);
        ribbonPanel.add(homePageButton, BorderLayout.EAST);

    }

    private List<Object[]> getCombinedData() {
        List<Course> courses = getCourses();
        List<Fee> fees = getFees();
        List<Discount> discounts = getDiscount();

        List<Object[]> combinedData = new ArrayList<>();
        for (Course course : courses) {
            boolean hasDiscount = false; // Flag to track if a discount is applied to this course

            // Check if there's a discount for the current course level
            for (Discount discount : discounts) {
                if (discount.getCourseLevel() == course.getSeniorityLevel()) {
                    combinedData.add(new Object[] {
                            course.getCourseCode(),
                            course.getCourseName(),
                            course.getCourseDuration(),
                            course.getSeniorityLevel(),
                            "RM " + course.getCourseFee(),
                            "RM " + discount.getDiscountAmount(),
                            "RM " + discount.getDiscountedAmount()
                    });
                    hasDiscount = true;
                }
            }

            // If no discount is applied, add a row with course details and empty discount
            // columns
            if (!hasDiscount) {
                combinedData.add(new Object[] {
                        course.getCourseCode(),
                        course.getCourseName(),
                        course.getCourseDuration(),
                        course.getSeniorityLevel(),
                        "RM " + course.getCourseFee(),
                        "",
                        "RM " + course.getCourseFee()
                });
            }
        }
        for (Fee fee : fees) {
            combinedData.add(new Object[] {
                    fee.getFeeType(),
                    fee.getFeeName(),
                    "",
                    "",
                    "",
                    "",
                    "RM " + fee.getFeeAmount(),
            });
        }

        combinedData.add(new Object[] {
                "Total:",
                "",
                "",
                "",
                "",
                "",
                "RM " + getTotal(),
        });

        return combinedData;
    }

    private List<Course> getCourses() {
        System.out.println("Courses from cache function: " + getCoursesFromFile("database/cache.txt"));
        return getCoursesFromFile("database/cache.txt");

    }

    private List<Fee> getFees() {
        System.out.println("Fees from cache function: " + getFeesFromFile("database/feecache.txt"));
        return getFeesFromFile("database/feecache.txt");
    }

    private List<Course> getCoursesFromFile(String fileName) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    courses.add(new Course(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4])));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private List<Fee> getFeesFromFile(String fileName) {
        List<Fee> fee = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    fee.add(new Fee(parts[0], parts[1], Double.parseDouble(parts[2])));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fee;
    }

    private List<Discount> getDiscount() {
        List<Discount> discount = new ArrayList<>();

        boolean hasLevel1 = false;
        boolean hasLevel2 = false;
        boolean hasLevel3 = false;

        for (Course course : getCourses()) {
            if (course.getSeniorityLevel() == 1) {
                hasLevel1 = true;
            } else if (course.getSeniorityLevel() == 2) {
                hasLevel2 = true;
            } else if (course.getSeniorityLevel() == 3) {
                hasLevel3 = true;
            }
        }

        if (hasLevel1 && hasLevel2) {
            for (Course course : getCourses()) {
                if (course.getSeniorityLevel() == 2) {
                    double discountAmount = Discount.calculateDiscount(course.getCourseFee());
                    double discountedAmount = course.getCourseFee() - discountAmount;
                    discount.add(new Discount(discountAmount, course.getSeniorityLevel(), discountedAmount));

                }
            }

        }
        if (hasLevel2 && hasLevel3) {
            for (Course course : getCourses()) {
                if (course.getSeniorityLevel() == 3) {
                    double discountAmount = Discount.calculateDiscount(course.getCourseFee());
                    double discountedAmount = course.getCourseFee() - discountAmount;
                    discount.add(new Discount(discountAmount, course.getSeniorityLevel(), discountedAmount));

                }
            }
        }
        return discount;
    }

    private double getTotal() {
        double total = 0;
        for (Course course : getCourses()) {
            total += course.getCourseFee();
        }
        for (Fee fee : getFees()) {
            total += fee.getFeeAmount();
        }
        for (Discount discount : getDiscount()) {
            total -= discount.getDiscountAmount();
        }
        System.out.println("Total: " + total);
        return total;
    }


}