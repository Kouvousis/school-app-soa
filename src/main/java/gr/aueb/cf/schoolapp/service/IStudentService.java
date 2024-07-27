package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.exceptios.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;

import java.util.List;

public interface IStudentService {
    Student insertStudent(StudentInsertDTO dto) throws StudentDAOException;
    Student updateStudent(StudentUpdateDTO dto) throws StudentDAOException, StudentNotFoundException;
    void deleteStudent(Integer id) throws StudentDAOException, StudentNotFoundException;
    Student getStudentById(Integer id) throws StudentDAOException, StudentNotFoundException;
    List<Student> getStudentsByLastname(String lastname) throws StudentDAOException;
}
