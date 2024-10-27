package lk.system.it.Dao.custom.impl;

import lk.system.it.Dao.custom.Student_CourseDAO;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Dao.util.DBUtil;
import lk.system.it.Entity.Student_Course;

import java.sql.Connection;
import java.sql.SQLException;

public class Student_CourseDAOImpl implements Student_CourseDAO {
    private final Connection connection;

    public Student_CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Student_Course save(Student_Course entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO Student_Course (student_id, course_id, status, Qr) VALUES (?,?,?,?)",
                    entity.getStudent_id(),
                    entity.getCourse_id(),
                    entity.getStatus(),
                    entity.getQr()
            ))
            {
                return entity;
            }
            throw new SQLException("Failed to save the items");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }
}
