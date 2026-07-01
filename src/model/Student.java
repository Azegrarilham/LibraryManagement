package model;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cne;
    private String firstName;
    private String lastName;
    private String major;
    private String email;

    public Student() {
    }

    public Student(String cne, String firstName, String lastName,
            String major, String email) {
        this.cne = cne;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.email = email;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // @Override
    // public String toString() {
    //     return "Student{" +
    //             "cne='" + cne + '\'' +
    //             ", firstName='" + firstName + '\'' +
    //             ", lastName='" + lastName + '\'' +
    //             ", major='" + major + '\'' +
    //             ", email='" + email + '\'' +
    //             '}';
    // }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
