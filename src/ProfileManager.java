import java.util.*;
import java.util.stream.Collectors;

/**
 * This class regulates the profile creation of the iser and the valadation system for the app
 * Will hadnle new user registrations and course validation
 */
public class ProfileManager {
    private FileManager fm;
    private Scanner scanner;

    /**
     * A constructor that creates a profile manager object
     * @param fm - object for data
     * @param scanner - reads in user input
     */
    public ProfileManager(FileManager fm, Scanner scanner) {
        this.fm = fm;
        this.scanner = scanner;
    }

    /**
     * This function creates a user profile
     * @return - a student obj if works, if not null
     * @throws Exception - excpetion is thrown if file operation fails
     */
    public Student createProfile() throws Exception {
        System.out.print("Choose username: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) { System.out.println("Invalid."); return null; }
        if (fm.findUser(username).isPresent()) { System.out.println("Already exists."); return null; }

        System.out.print("Full name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Password: ");
        String pw = scanner.nextLine();
        String salt = FileManager.randomHex();
        String hash = FileManager.sha256Hex(salt + pw);

        Student s = new Student(username, hash, salt, name);

        System.out.print("Enter courses comma separated (e.g., CPSC 1010,MATH 1080) or blank: ");
        String c = scanner.nextLine().trim();
        if (!c.isEmpty()) {
            List<String> courses = Arrays.stream(c.split(","))
                    .map(String::trim)
                    .filter(ProfileManager::validateCourseInput)
                    .collect(Collectors.toList());
            s.setCourses(courses);
        }

        fm.saveUser(s);
        System.out.println("Profile created.");
        return s;
    }

    /**
     * THis method confirms a course input with Clemson's course format
     * @param raw - course input for validation
     * @return - true if course format is correct, false if user enters course that does not work
     */
    public static boolean validateCourseInput(String raw) {
        String[] parts = raw.split("\\s+");
        if (parts.length != 2) { System.out.println("Use format PREFIX ####"); return false; }
        String prefix = parts[0].toUpperCase();
        String num = parts[1];
        List<String> valid = Arrays.asList("CPSC","BIOL","MATH","ENGL","HIST","PHYS","CHEM");
        if (!valid.contains(prefix)) { System.out.println("Invalid prefix."); return false; }
        if (!num.matches("\\d{4}")) { System.out.println("Course number must be 4 digits."); return false; }
        return true;
    }
}



