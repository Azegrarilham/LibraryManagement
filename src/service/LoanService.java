package service;

import java.util.ArrayList;

import model.Loan;

public class LoanService {

    private ArrayList<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public ArrayList<Loan> getAllLoans() {
        return loans;
    }
}
