package lk.system.it.Dao.custom.impl;

import lk.system.it.Dao.custom.Student_CourseDAO;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Dao.util.DBUtil;
import lk.system.it.Entity.Student;
import lk.system.it.Entity.Student_Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student_CourseDAOImpl implements Student_CourseDAO {
    private final Connection connection;

    public Student_CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Student_Course save(Student_Course entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO Student_Course (student_id, course_id, status, Qr, city) VALUES (?,?,?,?,?)",
                    entity.getStudent_id(),
                    entity.getCourse_id(),
                    entity.getStatus(),
                    entity.getQr(),
                    entity.getCity()
            ))
            {
                return entity;
            }
            throw new SQLException("Failed to save the items");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Student_Course findStudent_deails(String Stud_id) {
        Student_Course studentCourse = null;
        try {
            String sql = "Select * from student_course where student_id= ?";
            ResultSet rst = DBUtil.executeQuery(sql, Stud_id);
            if(rst.next()){
                studentCourse= new Student_Course(
                        rst.getString("student_id"),
                        rst.getString("course_id"),
                        rst.getString("status"),
                        rst.getBytes("Qr"),
                        rst.getString("city")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Student details in studentCourse");
        }
        return studentCourse;
    }


}
