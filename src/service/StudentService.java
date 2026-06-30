package service;

import java.util.ArrayList;

import model.Student;
import util.FileManager;

public class StudentService {

    private ArrayList<Student> students = new ArrayList<>();
    public StudentService() {
        students = FileManager.load("students.dat");
    }
    public boolean addStudent(Student student) {
        if (findStudentByCne(student.getCne()) == null) {
            students.add(student);
            FileManager.save("students.dat", students);
            return true;
        }
        return false;
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public Student findStudentByCne(String cne) {
        for (Student student : students) {
            if (student.getCne().equalsIgnoreCase(cne)) {
                return student;
            }
        }
        return null;
    }
    // Search by major
    public ArrayList<Student> searchByMajor(String major) {
        ArrayList<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getMajor().toLowerCase().contains(major.toLowerCase())) {
                result.add(student);
            }
        }

        return result;
    }
    public boolean updateStudent(Student updatedStudent) {
        Student student = findStudentByCne(updatedStudent.getCne());

        if (student != null) {
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setMajor(updatedStudent.getMajor());
            student.setEmail(updatedStudent.getEmail());
            FileManager.save("students.dat", students);
            return true;
        }

        return false;
    }

    public boolean deleteStudent(String cne) {
        Student student = findStudentByCne(cne);

        if (student != null) {
            students.remove(student);
            FileManager.save("students.dat", students);
            return true;
        }

        return false;
    }
}
