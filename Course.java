import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseCode;
    private String courseName;
    private String courseDuration;
    private int seniorityLevel;
    private double courseFee;

    // Constructor
    public Course(String courseCode, String courseName, String courseDuration, int seniorityLevel, double courseFee) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.seniorityLevel = seniorityLevel;
        this.courseFee = courseFee;
    }

    // Getters and Setters
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public int getSeniorityLevel() {
        return seniorityLevel;
    }

    public void setSeniorityLevel(int seniorityLevel) {
        this.seniorityLevel = seniorityLevel;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    // Method to write course information to a file
    public static void writeCoursesToFile(List<Course> courses, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Course course : courses) {
                writer.write(course.getCourseCode() + "," +
                             course.getCourseName() + "," +
                             course.getCourseDuration() + "," +
                             course.getSeniorityLevel() + "," +
                             course.getCourseFee());
                writer.newLine();
            }
        }
    }

    // Method to read course information from a file
    public static List<Course> readCoursesFromFile(String fileName) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Course course = new Course(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
                    courses.add(course);
                }
            }
        }
        return courses;
    }
}
