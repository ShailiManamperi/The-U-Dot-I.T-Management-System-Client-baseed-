package lk.system.it.Service.custom.impl;

import lk.system.it.Dao.DaoFactory;
import lk.system.it.Dao.DaoTypes;
import lk.system.it.Dao.custom.CourseDAO;
import lk.system.it.Dao.custom.Student_CourseDAO;
import lk.system.it.Db.DBConnection;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Entity.Student_Course;
import lk.system.it.Service.custom.Student_CourseService;
import lk.system.it.Service.exception.DuplicateException;
import lk.system.it.Service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student_CourseServiceImpl implements Student_CourseService {
    private final Converter converter;
    private final Connection connection;
    private final Student_CourseDAO studentCourseDAO;

    public Student_CourseServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        studentCourseDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.Student_Course);

    }
    @Override
    public Student_CourseDto saveStudentDetail(Student_CourseDto dto) throws DuplicateException {
        Student_Course save = studentCourseDAO.save(converter.toStud_Cour(dto));
        return converter.fromStud_Cour(save);
    }

    @Override
    public Student_CourseDto findStudent_Course(String id) throws SQLException {
        Student_Course studentDeails = studentCourseDAO.findStudent_deails(id);
        return converter.fromStud_Cour(studentDeails);
    }

    @Override
    public ArrayList<Student_CourseDto> findStudentByCity(String city) throws SQLException {
        ArrayList<Student_CourseDto> studentDtos = new ArrayList<>();
        ArrayList<Student_Course> studentsByCity = studentCourseDAO.findStudentsByCity(city);
        for (Student_Course student : studentsByCity) {
            studentDtos.add(converter.fromStud_Cour(student));
        }
        return studentDtos;
    }
}
