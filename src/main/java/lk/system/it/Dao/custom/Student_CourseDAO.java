package lk.system.it.Dao.custom;

import lk.system.it.Dao.SuperDAO;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Entity.Student;
import lk.system.it.Entity.Student_Course;

public interface Student_CourseDAO extends SuperDAO {

    Student_Course save(Student_Course entity) throws ConstraintViolationException;
    Student_Course findStudent_deails(String Stud_id);
}
