import java.util.ArrayList;
import java.util.List;

public class Student {
    private String username;
    private String password;
    private String salt;
    private String name;
    private List<String> courses;

    public Student(String username, String password, String salt, String name) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSalt() { return salt; }
    public String getName() { return name; }
    public List<String> getCourses() { return courses; }
    public void setCourses(List<String> courses) { this.courses = courses; }

    public boolean isEnrolledIn(String course) {
        return courses.contains(course);
    }
}
