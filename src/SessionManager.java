import java.util.*;

/**
 * This class manges the study session for the Buddy system
 */
public class SessionManager {
    private List<StudySession> sessions;
    private FileManager fm;

    /**
     * Constructor that initializes a study session
     * @param fm - THe File mananger object
     */
    public SessionManager(FileManager fm) {
        this.fm = fm;
        this.sessions = new ArrayList<>();

        // Preload some sample sessions
        sessions.add(new StudySession("sess001", "CPSC 1010", "MONDAY", "14:00", "15:00", 4));
        sessions.add(new StudySession("sess002", "MATH 1080", "TUESDAY", "10:00", "11:00", 2));
    }

    /**
     * This method shows all study session avialable to the user
     * @param s - a student that is trying to view sessions
     */
    public void viewSessions(Student s) {
        System.out.println("\n=== AVAILABLE SESSIONS ===");
        for (StudySession sess : sessions) {
            System.out.println(sess.getSessionId() + ": " + sess.getCourse() + " " + sess);
        }
    }

    /**
     * This method hadndless when a student is trying to leave a session
     * @param s - A student object that is tring to join or leave
     * @param scanner - reads in user input
     */
    public void joinLeaveSession(Student s, Scanner scanner) {
        System.out.print("Enter session ID to join or leave (or blank to skip): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return;

        Optional<StudySession> opt = sessions.stream().filter(sess -> sess.getSessionId().equalsIgnoreCase(input)).findFirst();
        if (opt.isEmpty()) { System.out.println("Session not found."); return; }

        StudySession session = opt.get();
        if (session.getAttendees().contains(s.getUsername())) {
            session.removeAttendee(s.getUsername());
            System.out.println("Removed from session.");
        } else {
            if (s.isEnrolledIn(session.getCourse()) && session.addAttendee(s.getUsername()))
                System.out.println("Joined session!");
            else
                System.out.println("Cannot join session (full or not enrolled).");
        }
    }
}


