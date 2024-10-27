package lk.system.it.Dao.custom;

import lk.system.it.Dao.SuperDAO;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Entity.Course;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CourseDAO extends SuperDAO {

    Course save(Course entity) throws ConstraintViolationException;
    boolean existByPk(String pk);

    ArrayList<String> loadCourseIds() throws SQLException, ClassNotFoundException;
}
