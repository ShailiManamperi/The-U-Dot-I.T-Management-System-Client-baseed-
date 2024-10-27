package lk.system.it.Dao.custom.impl;

import lk.system.it.Dao.custom.CourseDAO;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Dao.util.DBUtil;
import lk.system.it.Entity.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDaoImpl implements CourseDAO {
    private final Connection connection;

    public CourseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Course save(Course entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO Courses (course_id, course_name, fees, duration) VALUES (?,?,?,?)",
                    entity.getCourse_id(),
                    entity.getCourse_name(),
                    entity.getFees(),
                    entity.getDuration()
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
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Courses WHERE course_id=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> loadCourseIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT course_id FROM Courses";

        ResultSet resultSet = DBUtil.executeQuery(sql);
        ArrayList<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
