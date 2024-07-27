package gr.aueb.cf.schoolapp.dao.exceptios;

import java.io.Serial;

public class StudentDAOException extends Exception {
    @Serial
    private static final long serialVersionUID = 2L;

    public StudentDAOException(String s) {
        super(s);
    }
}
