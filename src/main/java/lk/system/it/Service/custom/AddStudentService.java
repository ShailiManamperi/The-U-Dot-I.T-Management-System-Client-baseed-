package lk.system.it.Service.custom;

import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Service.SuperService;

import java.sql.SQLException;

public interface AddStudentService extends SuperService {
    public boolean AddStudent(StudentDto studentDto, Student_CourseDto studentCourseDto) throws SQLException,ClassNotFoundException;
}
