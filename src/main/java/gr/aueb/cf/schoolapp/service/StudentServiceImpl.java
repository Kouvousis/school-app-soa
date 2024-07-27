package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.exceptios.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;

import java.util.List;

public class StudentServiceImpl implements IStudentService {
    private final IStudentDAO studentDAO;

    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public Student insertStudent(StudentInsertDTO dto) throws StudentDAOException {
        Student student;

        try {
            student = mapToStudent(dto);
            return studentDAO.insert(student);
        } catch (StudentDAOException e) {
            e.printStackTrace();
            // rollback
            throw e;
        }
    }

    @Override
    public Student updateStudent(StudentUpdateDTO dto) throws StudentDAOException, StudentNotFoundException {
        Student student;

        try {
            student = mapToStudent(dto);

            if (studentDAO.getById(student.getId()) == null) {
                throw new StudentNotFoundException(student);
            }
            return studentDAO.update(student);
        } catch (StudentDAOException | StudentNotFoundException e) {
            e.printStackTrace();
            // logging
            // rollback
            throw e;
        }

    }

    @Override
    public void deleteStudent(Integer id) throws StudentDAOException, StudentNotFoundException {

        try {
            if (studentDAO.getById(id) == null) {
                throw new StudentNotFoundException("Student was not found");
            }

            studentDAO.delete(id);
        } catch (StudentDAOException | StudentNotFoundException e) {
            e.printStackTrace();
            // rollback
            throw e;
        }
    }

    @Override
    public Student getStudentById(Integer id) throws StudentDAOException, StudentNotFoundException {
        Student student;

        try {
            student = studentDAO.getById(id);
            if (student == null) {
                throw new StudentNotFoundException("Student with id: " + id + " not found");
            }

            return student;
        } catch (StudentDAOException | StudentNotFoundException e) {
            e.printStackTrace();
            // rollback
            throw e;
        }
    }

    @Override
    public List<Student> getStudentsByLastname(String lastname) throws StudentDAOException {
        List<Student> students;

        try {
            students = studentDAO.getByLastName(lastname);
            return students;
        } catch (StudentDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Student mapToStudent(StudentInsertDTO dto) {
        return new Student(null, dto.getFirstname(), dto.getLastname());
    }

    private Student mapToStudent(StudentUpdateDTO dto) {
        return new Student(dto.getId(), dto.getFirstname(), dto.getLastname());
    }
}
