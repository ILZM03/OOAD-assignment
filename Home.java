import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {
    private JLabel l1;
    private JButton b1,b2;
    
    public Home() {
        Font f1 = new Font("Arial", Font.BOLD, 20);
        Font f2 = new Font("Arial", Font.ITALIC, 15);
        

        l1 = new JLabel("Welcome to the MMU Course Enrollment Manager");
        l1.setFont(f1);
        
        b1 = new JButton("Financial Packages");
        b2 = new JButton("Course Manager");

        b1.setFont(f2);
        b2.setFont(f2);
        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.WHITE);
        b1.setBackground(Color.DARK_GRAY);
        b2.setBackground(Color.DARK_GRAY);
        b1.setPreferredSize(new Dimension(200,50));
        b2.setPreferredSize(new Dimension(200, 50));

        //Panel 1
        JPanel p1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        p1.add(l1, gbc);
        p1.setBackground(Color.WHITE);

        //Panel 2
        JPanel p2 = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        p2.add(b1, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        p2.add(b2, gbc);
        p2.setBackground(Color.WHITE);

        JPanel p3 = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        p3.add(p1, gbc);
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        p3.add(p2, gbc);
        p3.setBackground(Color.WHITE);

        //Set Border Layout
        add(p3, BorderLayout.CENTER);
        
        //Register Object
        b1.addActionListener(this);
        b2.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            dispose(); // Close the current frame if needed
            FinancialPackages obj = new FinancialPackages();
            obj.pack();
            obj.setVisible(true);
            obj.setTitle("Financial Packages");
            obj.setSize(800, 650);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        }
        else if(e.getSource()==b2){
            dispose(); // Close the current frame if needed
            MMUApp obj = new MMUApp();
            obj.pack();
            obj.setVisible(true);
            obj.setTitle("Course Manager");
            obj.setSize(800, 650);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        }
        else
            System.out.println("Invalid Button");
}

 public static void main(String[] args) {
    Home obj = new Home();
    obj.pack();
    obj.setVisible(true);
    obj.setTitle("Home");
    obj.setSize(800, 650);
    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
