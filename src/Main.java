import java.util.*;

/**
 * This is the maim function for the Clemson Study Buddy Application
 *
 */

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static FileManager fm = new FileManager();
    private static ProfileManager pm = new ProfileManager(fm, scanner);
    private static SessionManager sm = new SessionManager(fm);
    private static Student currentUser;

    /**
     * Displays the menu
     * @param args - Command line argument
     * @throws Exception - If an file fails an exception will be thrown
     */

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n=== CLEMSON STUDY BUDDY ===");
            System.out.println("1) Login");
            System.out.println("2) Create Profile");
            System.out.println("3) Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": login(); break;
                case "2": currentUser = pm.createProfile(); break;
                case "3": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice."); break;
            }
            // if user logs in, then shoe menu
            if (currentUser != null) mainMenu();
        }
    }
    /**
    * Function handles user login process. Asks for username and passwords and validates input
    *
    */
    private static void login() throws Exception {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<Student> opt = fm.findUser(username);
        if (opt.isPresent()) {
            Student s = opt.get();
            String hash = FileManager.sha256Hex(s.getSalt() + password);
            if (hash.equals(s.getPassword())) {
                currentUser = s;
                System.out.println("Login successful! Welcome " + s.getName());
            } else System.out.println("Incorrect password.");
        } else System.out.println("User not found.");
    }

    /**
     * This function displays the menu after a sucessful login. Provides the user options for joining sessions. WIll continue until user logs out
     * @throws Exception - excpetion is thrown if session fails
     */
    private static void mainMenu() throws Exception {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) View/Join Sessions");
            System.out.println("2) Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    sm.viewSessions(currentUser);
                    sm.joinLeaveSession(currentUser, scanner);
                    break;
                case "2":
                    currentUser = null;
                    return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
}
