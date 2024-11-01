package lk.system.it.Service.custom;

import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Service.SuperService;
import lk.system.it.Service.exception.DuplicateException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Student_CourseService extends SuperService {
    public Student_CourseDto saveStudentDetail(Student_CourseDto dto) throws DuplicateException;

    public Student_CourseDto findStudent_Course(String id) throws SQLException;
    ArrayList<Student_CourseDto> findStudentByCity(String city) throws SQLException;
}
