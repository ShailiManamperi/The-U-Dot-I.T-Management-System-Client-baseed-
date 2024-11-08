package lk.system.it.Service.custom;

import lk.system.it.Dto.StudentDto;
import lk.system.it.Service.SuperService;
import lk.system.it.Service.exception.DuplicateException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentService extends SuperService {
     StudentDto saveStudent(StudentDto Dto) throws DuplicateException;
     public String generateNewItemId() throws SQLException;
     public StudentDto searchStudent(String Stud_id,String type);
     StudentDto findByPk(String pk);
     StudentDto updateStudent(StudentDto dto) ;

     boolean deleteStudent(String pk);


}
