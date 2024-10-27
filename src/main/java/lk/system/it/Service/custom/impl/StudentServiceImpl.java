package lk.system.it.Service.custom.impl;


import lk.system.it.Dao.DaoFactory;
import lk.system.it.Dao.DaoTypes;
import lk.system.it.Dao.custom.StudentDAO;
import lk.system.it.Db.DBConnection;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Entity.Student;
import lk.system.it.Service.custom.StudentService;
import lk.system.it.Service.exception.DuplicateException;
import lk.system.it.Service.exception.NotFoundException;
import lk.system.it.Service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private final Converter converter;
    private final Connection connection;

    private final StudentDAO studentDAO;

    public StudentServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        studentDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.STUDENT);
    }
    @Override
    public StudentDto saveStudent(StudentDto Dto) throws DuplicateException {
        if (studentDAO.existByPk(Dto.getStudent_id())) {
            throw new DuplicateException("This Item id is already added!");
        } else {
            studentDAO.save(converter.tostudent(Dto));
            return Dto;
        }
    }

    @Override
    public String generateNewItemId() throws SQLException {
        String newStudentId = studentDAO.findNewId();
        return newStudentId;
    }

    @Override
    public StudentDto searchStudent(String Stud_id, String type) {
        System.out.println(Stud_id + " "+type);
        if (!studentDAO.existByPk(Stud_id)){
            System.out.println("runs wrong");
            throw new NotFoundException("Employee is Not Found!");
        }
        Student student = studentDAO.findStudent(Stud_id, type);
        System.out.println(student);
        return converter.fromStudent(student);
    }
}
