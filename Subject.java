package studentmanagementsystem;

public class Subject {
    private String name;
    private double grade;

    public Subject(String name, double grade) {
        this.name = name;
        setGrade(grade);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f", name, grade);
    }
}
