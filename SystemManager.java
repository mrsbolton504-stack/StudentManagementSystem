package studentmanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class SystemManager {
    private ArrayList<Student> students;

    public SystemManager() {
        students = new ArrayList<>();
    }

    // Add student
    public boolean addStudent(Student student) {
        if (findStudentById(student.getId()) != null) {
            return false; // Duplicate ID
        }
        students.add(student);
        return true;
    }

    // Remove student
    public boolean removeStudent(String id) {
        Student s = findStudentById(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

    // Search student by ID
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // Update student name or subject grade
    public boolean updateStudentName(String id, String newName) {
        Student s = findStudentById(id);
        if (s != null) {
            s.setName(newName);
            return true;
        }
        return false;
    }

    public boolean recordGrade(String id, String subjectName, double grade) {
        Student s = findStudentById(id);
        if (s != null) {
            s.addOrUpdateSubject(subjectName, grade);
            return true;
        }
        return false;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Report 1: Average grade for each student
    public void reportStudentAverages() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        System.out.println("\n--- Average Grade Report ---");
        for (Student s : students) {
            System.out.printf("%s (%s - %s): %.2f%n",
                    s.getName(), s.getId(), s.getStudentType(), s.calculateAverage());
        }
    }

    // Report 2: Highest and lowest grades for a specific subject
    public void reportSubjectHighLow(String subjectName) {
        Student highestStudent = null;
        Student lowestStudent = null;
        double highest = -1.0;
        double lowest = 101.0;

        for (Student s : students) {
            Double grade = s.getGradeForSubject(subjectName);
            if (grade != null) {
                if (grade > highest) {
                    highest = grade;
                    highestStudent = s;
                }
                if (grade < lowest) {
                    lowest = grade;
                    lowestStudent = s;
                }
            }
        }

        if (highestStudent == null) {
            System.out.println("No grades recorded for subject: " + subjectName);
        } else {
            System.out.println("\n--- Subject Report: " + subjectName + " ---");
            System.out.printf("Highest Grade: %.2f (%s - %s)%n",
                    highest, highestStudent.getName(), highestStudent.getId());
            System.out.printf("Lowest Grade:  %.2f (%s - %s)%n",
                    lowest, lowestStudent.getName(), lowestStudent.getId());
        }
    }

    // Report 3: List of students sorted by average grade (descending)
    public void reportSortedByGrade() {
        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        ArrayList<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparingDouble(Student::calculateAverage).reversed());

        System.out.println("\n--- Students Sorted by Average Grade (Highest to Lowest) ---");
        int rank = 1;
        for (Student s : sorted) {
            System.out.printf("%d. %s (ID: %s | %s) - Average: %.2f%n",
                    rank++, s.getName(), s.getId(), s.getStudentType(), s.calculateAverage());
        }
    }

    // File Handling: Save to text file
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student s : students) {
                StringBuilder line = new StringBuilder();
                line.append(s.getStudentType()).append("|")
                    .append(s.getId()).append("|")
                    .append(s.getName()).append("|");

                ArrayList<Subject> subs = s.getSubjects();
                for (int i = 0; i < subs.size(); i++) {
                    line.append(subs.get(i).getName()).append(":")
                        .append(subs.get(i).getGrade());
                    if (i < subs.size() - 1) {
                        line.append(",");
                    }
                }
                writer.println(line.toString());
            }
            System.out.println("Student records successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // File Handling: Load from text file
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            students.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length >= 3) {
                    String type = parts[0];
                    String id = parts[1];
                    String name = parts[2];

                    Student s = type.equalsIgnoreCase("Honors")
                            ? new HonorsStudent(id, name)
                            : new Student(id, name);

                    if (parts.length == 4 && !parts[3].isEmpty()) {
                        String[] subjectPairs = parts[3].split(",");
                        for (String pair : subjectPairs) {
                            String[] subData = pair.split(":");
                            if (subData.length == 2) {
                                s.addOrUpdateSubject(subData[0], Double.parseDouble(subData[1]));
                            }
                        }
                    }
                    students.add(s);
                }
            }
            System.out.println("Student records successfully loaded from " + filename);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
