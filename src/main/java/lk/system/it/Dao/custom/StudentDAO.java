package lk.system.it.Dao.custom;

import lk.system.it.Dao.SuperDAO;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO extends SuperDAO {
    Student save(Student entity) throws ConstraintViolationException;
    Student update(Student entity) throws ConstraintViolationException;
    boolean deleteByPk(String pk) throws ConstraintViolationException;
    List<Student> findAll();
    String findNewId();
    Optional<Student> findByPk(String pk);
    boolean existByPk(String pk);

    Student findStudent(String Stud_id,String type);


}
