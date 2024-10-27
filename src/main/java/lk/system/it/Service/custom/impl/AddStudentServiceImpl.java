package lk.system.it.Service.custom.impl;

import lk.system.it.Dao.DaoFactory;
import lk.system.it.Dao.DaoTypes;
import lk.system.it.Dao.custom.CourseDAO;
import lk.system.it.Dao.custom.StudentDAO;
import lk.system.it.Dao.custom.Student_CourseDAO;
import lk.system.it.Db.DBConnection;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Entity.Student;
import lk.system.it.Entity.Student_Course;
import lk.system.it.Service.custom.AddStudentService;
import lk.system.it.Service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class AddStudentServiceImpl implements AddStudentService {
    private final Converter converter;
    private final Connection connection;
    private final StudentDAO studentDAO;
    private final Student_CourseDAO studentCourseDAO;

    public AddStudentServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        studentDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.STUDENT);
        studentCourseDAO = DaoFactory.getInstance().getDAO(connection,DaoTypes.Student_Course);

    }
    @Override
    public boolean AddStudent(StudentDto studentDto, Student_CourseDto studentCourseDto) throws SQLException, ClassNotFoundException {
        try {
            DBConnection.getDbConnection().getConnection().setAutoCommit(false);
            Student student = converter.tostudent(studentDto);
            Student_Course studentCourse = converter.toStud_Cour(studentCourseDto);
            Student save = studentDAO.save(student);
            if (save != null) {
                Student_Course save1 = studentCourseDAO.save(studentCourse);
                if (save1 !=null) {
                    DBConnection.getDbConnection().getConnection().commit();
                    return true;
                }
            }
            DBConnection.getDbConnection().getConnection().rollback();
            return false;
        } finally {
            DBConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }
}
