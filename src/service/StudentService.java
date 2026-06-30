package service;

import java.util.ArrayList;

import model.Student;

public class StudentService {

    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
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

    public boolean updateStudent(Student updatedStudent) {
        Student student = findStudentByCne(updatedStudent.getCne());

        if (student != null) {
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setMajor(updatedStudent.getMajor());
            student.setEmail(updatedStudent.getEmail());
            return true;
        }

        return false;
    }

    public boolean deleteStudent(String cne) {
        Student student = findStudentByCne(cne);

        if (student != null) {
            students.remove(student);
            return true;
        }

        return false;
    }
}
