package studentmanagementsystem;

import java.util.Scanner;

public class StudentManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemManager manager = new SystemManager();
        final String FILE_NAME = "students.txt";

        int choice = 0;
        while (choice != 10) {
            System.out.println("\n==========================================");
            System.out.println("        STUDENT MANAGEMENT SYSTEM         ");
            System.out.println("==========================================");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Update Student Name");
            System.out.println("4. Add / Update Subject Grade");
            System.out.println("5. View All Students");
            System.out.println("6. Report: Average Grade per Student");
            System.out.println("7. Report: Highest & Lowest Grade in Subject");
            System.out.println("8. Report: Students Sorted by Grade");
            System.out.println("9. Save / Load Records (Text File)");
            System.out.println("10. Exit");
            System.out.print("Enter your choice (1-10): ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 10.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Student Type (1 = Regular, 2 = Honors): ");
                    String typeChoice = scanner.nextLine().trim();

                    Student newStudent = typeChoice.equals("2")
                            ? new HonorsStudent(id, name)
                            : new Student(id, name);

                    if (manager.addStudent(newStudent)) {
                        System.out.println("Student added successfully.");
                    } else {
                        System.out.println("Error: A student with ID " + id + " already exists.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Student ID to remove: ");
                    String removeId = scanner.nextLine().trim();
                    if (manager.removeStudent(removeId)) {
                        System.out.println("Student removed.");
                    } else {
                        System.out.println("Student ID not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID to update: ");
                    String updateId = scanner.nextLine().trim();
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine().trim();
                    if (manager.updateStudentName(updateId, newName)) {
                        System.out.println("Student name updated.");
                    } else {
                        System.out.println("Student ID not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    String gradeId = scanner.nextLine().trim();
                    if (manager.findStudentById(gradeId) == null) {
                        System.out.println("Student ID not found.");
                        break;
                    }
                    System.out.print("Enter Subject Name: ");
                    String subject = scanner.nextLine().trim();
                    System.out.print("Enter Grade (0 - 100): ");
                    try {
                        double grade = Double.parseDouble(scanner.nextLine().trim());
                        manager.recordGrade(gradeId, subject, grade);
                        System.out.println("Grade recorded successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid grade: " + e.getMessage());
                    }
                    break;

                case 5:
                    manager.displayAllStudents();
                    break;

                case 6:
                    manager.reportStudentAverages();
                    break;

                case 7:
                    System.out.print("Enter Subject Name: ");
                    String searchSub = scanner.nextLine().trim();
                    manager.reportSubjectHighLow(searchSub);
                    break;

                case 8:
                    manager.reportSortedByGrade();
                    break;

                case 9:
                    System.out.print("Enter (S) to Save or (L) to Load: ");
                    String fileOp = scanner.nextLine().trim().toUpperCase();
                    if (fileOp.equals("S")) {
                        manager.saveToFile(FILE_NAME);
                    } else if (fileOp.equals("L")) {
                        manager.loadFromFile(FILE_NAME);
                    } else {
                        System.out.println("Invalid option. Enter S or L.");
                    }
                    break;

                case 10:
                    System.out.println("Exiting Student Management System. Goodbye!");
                    break;

                default:
                    System.out.println("Please choose a valid option (1-10).");
            }
        }
        scanner.close();
    }
}
