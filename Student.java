package studentmanagementsystem;

import java.util.ArrayList;

public class Student extends Person {
    private ArrayList<Subject> subjects;

    public Student(String id, String name) {
        super(id, name);
        this.subjects = new ArrayList<>();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void addOrUpdateSubject(String subjectName, double grade) {
        for (Subject s : subjects) {
            if (s.getName().equalsIgnoreCase(subjectName)) {
                s.setGrade(grade);
                return;
            }
        }
        subjects.add(new Subject(subjectName, grade));
    }

    public Double getGradeForSubject(String subjectName) {
        for (Subject s : subjects) {
            if (s.getName().equalsIgnoreCase(subjectName)) {
                return s.getGrade();
            }
        }
        return null;
    }

    public double calculateAverage() {
        if (subjects.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Subject s : subjects) {
            sum += s.getGrade();
        }
        return sum / subjects.size();
    }

    @Override
    public String getStudentType() {
        return "Regular";
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s (%s) | Avg: %.2f | Subjects: %s",
                getId(), getName(), getStudentType(), calculateAverage(), subjects);
    }
}
