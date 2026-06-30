package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    private String loanNumber;
    private Student student;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;

    public Loan() {
    }

    public Loan(String loanNumber, Student student, Book book,
            LocalDate borrowDate, LocalDate returnDate, String status) {
        this.loanNumber = loanNumber;
        this.student = student;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanNumber='" + loanNumber + '\'' +
                ", student=" + student +
                ", book=" + book +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                '}';
    }
}
