package lk.system.it.Service.custom.impl;




import lk.system.it.Dao.DaoFactory;
import lk.system.it.Dao.DaoTypes;
import lk.system.it.Dao.custom.CourseDAO;
import lk.system.it.Db.DBConnection;
import lk.system.it.Dto.CourseDto;
import lk.system.it.Service.custom.CourseService;
import lk.system.it.Service.exception.DuplicateException;
import lk.system.it.Service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {

    private final Converter converter;
    private final Connection connection;
    private final CourseDAO courseDAO;

    public CourseServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        courseDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.COURSE);

    }

    @Override
    public CourseDto saveCourse(CourseDto courseDto) throws DuplicateException {
        if (courseDAO.existByPk(courseDto.getCourse_id())) {
            throw new DuplicateException("This Course id is already added!");
        } else {
            courseDAO.save(converter.toCourse(courseDto));
            return courseDto;
        }
    }

    @Override
    public ArrayList<String> loadCourseId() throws SQLException, ClassNotFoundException {
        ArrayList<String> courseIds = courseDAO.loadCourseIds();
        return courseIds;
    }

}
