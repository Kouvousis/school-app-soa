package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptios.TeacherDAOException;
import gr.aueb.cf.schoolapp.model.Teacher;

import java.util.List;

public interface ITeacherDAO {
    Teacher insert(Teacher teacher) throws TeacherDAOException;
    Teacher update(Teacher teacher) throws TeacherDAOException;
    void delete(Integer id) throws TeacherDAOException;
    Teacher getById(Integer id) throws TeacherDAOException;
    List<Teacher> getByLastName(String lastname) throws TeacherDAOException;
}
