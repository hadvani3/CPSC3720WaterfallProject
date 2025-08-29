import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {
    private final String STUDENTS_FILE = "students.txt";
    private final String SESSIONS_FILE = "sessions.txt";

    // Load users
    public List<Student> loadUsers() {
        List<Student> users = new ArrayList<>();
        File file = new File(STUDENTS_FILE);
        if (!file.exists()) return users;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|");
                String username = parts[0];
                String password = parts[1];
                String salt = parts[2];
                String name = parts[3];
                List<String> courses = new ArrayList<>();
                if (parts.length > 4 && !parts[4].isEmpty())
                    courses = Arrays.stream(parts[4].split(",")).map(String::trim).collect(Collectors.toList());
                Student s = new Student(username, password, salt, name);
                s.setCourses(courses);
                users.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return users;
    }

    public void saveUser(Student s) throws IOException {
        List<Student> users = loadUsers();
        boolean updated = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(s.getUsername())) {
                users.set(i, s);
                updated = true;
                break;
            }
        }
        if (!updated) users.add(s);

        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student u : users) {
                String line = String.join("|",
                        u.getUsername(), u.getPassword(), u.getSalt(), u.getName(),
                        String.join(",", u.getCourses()));
                pw.println(line);
            }
        }
    }

    public Optional<Student> findUser(String username) {
        return loadUsers().stream()
                .filter(s -> s.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    // SHA256 hashing
    public static String sha256Hex(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    // Random salt
    public static String randomHex() {
        byte[] bytes = new byte[16];
        new Random().nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
