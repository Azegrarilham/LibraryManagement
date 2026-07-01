package service;

import java.util.ArrayList;

import model.Loan;
import util.FileManager;

public class LoanService {

    private ArrayList<Loan> loans = new ArrayList<>();

    public LoanService() {
        loans = FileManager.load("loans.dat");
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
        FileManager.save("loans.dat", loans);
    }

    public ArrayList<Loan> getAllLoans() {
        return loans;
    }

    public void saveLoans() {
        FileManager.save("loans.dat", loans);
    }

    public void deleteLoan(Loan loan) {
        loans.remove(loan);
        saveLoans();
    }

    public Loan findLoanByNumber(String loanNumber) {

        for (Loan loan : loans) {

            if (loan.getLoanNumber().equalsIgnoreCase(loanNumber)) {
                return loan;
            }
        }

        return null;
    }

    public ArrayList<Loan> getBorrowedLoans() {

        ArrayList<Loan> borrowed = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStatus().equalsIgnoreCase("Borrowed")) {
                borrowed.add(loan);
            }
        }

        return borrowed;
    }

    public boolean updateLoan(Loan updatedLoan) {

        for (int i = 0; i < loans.size(); i++) {

            if (loans.get(i).getLoanNumber().equals(updatedLoan.getLoanNumber())) {

                loans.set(i, updatedLoan);
                saveLoans();
                return true;
            }
        }

        return false;
    }
    public ArrayList<Loan> getReturnedLoans() {

        ArrayList<Loan> returned = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStatus().equalsIgnoreCase("Returned")) {
                returned.add(loan);
            }
        }

        return returned;
    }
}
