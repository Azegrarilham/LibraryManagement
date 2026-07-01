package util;

import service.BookService;
import service.StudentService;
import service.LoanService;

public class DataManager {

    public static final BookService bookService = new BookService();
    public static final StudentService studentService = new StudentService();
    public static final LoanService loanService = new LoanService();

}
