package lk.system.it.Service.custom;


import lk.system.it.Dto.CourseDto;
import lk.system.it.Service.SuperService;
import lk.system.it.Service.exception.DuplicateException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface CourseService extends SuperService {
    public CourseDto saveCourse(CourseDto courseDto) throws DuplicateException;

    ArrayList<String> loadCourseId() throws SQLException, ClassNotFoundException;
}
