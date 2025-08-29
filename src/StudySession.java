import java.util.ArrayList;
import java.util.List;

/**
 * This class is a represntation of a study session for students.
 * Each session has a special ID, course, time slot and enrollment times
 */
public class StudySession {

    // private member variables for Study Session class
    private String sessionId;
    private String course;
    private String day;
    private String startTime;
    private String endTime;
    private int maxAttendees;
    private List<String> attendees;

    /**
     * This function creates a new Study Session with specific parameters
     * @param sessionId - ID for the session
     * @param course - course name
     * @param day - day of the session
     * @param startTime - start time for the session
     * @param endTime - end time for the session
     * @param max - maximum slots alloted for the study session
     */

    public StudySession(String sessionId, String course, String day, String startTime, String endTime, int max) {
        this.sessionId = sessionId;
        this.course = course;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxAttendees = max;
        this.attendees = new ArrayList<>();
    }

    /**
     * Thus function fecthes a unique session ID
     * @return - session ID
     */
    public String getSessionId() { return sessionId; }

    /**
     * getter function that fetches course code for study session
     * @return - course ID
     */
    public String getCourse() { return course; }

    /**
     * This getter function will return the days of the session
     * @return - the session day
     */
    public String getDay() { return day; }

    /**
     * THis getter function will return the start time for the study session
     * @return - study session start time
     */
    public String getStartTime() { return startTime; }

    /**
     * This getter function will return the end times for the session
     * @return - end time for the study session
     */
    public String getEndTime() { return endTime; }

    /**
     * Getter function that returns the usernames for attendees
     * @return - the usernames of attendees
     */
    public List<String> getAttendees() { return attendees; }

    /**
     * Getter function that returns the max number of attendees for a study session
     * @return - max number of attendees
     */
    public int getMaxAttendees() { return maxAttendees; }

    /**
     * Checks if session is full
     * @return - true value if the session is full, false if not
     */
    public boolean isFull() { return attendees.size() >= maxAttendees; }

    /**
     * This function adds an attendee to the session. Also prevents multi[ple enrollments from the same persion
     * @param username - username of added student
     * @return - true value if added, false if the study session is full or student already enrolled
     */
    public boolean addAttendee(String username) {
        if (isFull() || attendees.contains(username)) return false;
        attendees.add(username);
        return true;
    }

    /**
     * This function removes an attendee from a session
     * @param username - the username of removed student
     * @return - true value if removed, false otherwise
     */
    public boolean removeAttendee(String username) {
        return attendees.remove(username);
    }

    /**
     * THis method returns a string of the ession
     * @return - string showing meeting details
     */
    @Override
    public String toString() {
        return String.format("%s %s-%s (%d/%d attendees)", day, startTime, endTime, attendees.size(), maxAttendees);
    }
}
