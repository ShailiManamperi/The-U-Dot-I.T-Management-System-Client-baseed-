package lk.system.it.Service.custom;

import lk.system.it.Dtm.MarkDtm;
import lk.system.it.Dto.AttendanceDto;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Entity.Student;
import lk.system.it.Service.SuperService;
import org.exolab.castor.mapping.xml.Sql;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AttendanceService extends SuperService {

    ArrayList<MarkDtm> getAllByCity(String city) throws SQLException;
    ArrayList<MarkDtm> getAllAttendance(LocalDate date) throws SQLException;

    boolean markPresent(String id) throws SQLException, ClassNotFoundException;

    boolean markAllAbsent(ArrayList<Student_CourseDto> dtoes) throws SQLException, ClassNotFoundException;

    MarkDtm getstudentattend(String id,LocalDate date) throws SQLException;
    boolean searchIsAdded(LocalDate date) throws SQLException;

    ArrayList<AttendanceDto> getStatusById(String pk) throws SQLException;

}
