package lk.system.it.Dao.custom;

import lk.system.it.Dao.SuperDAO;
import lk.system.it.Dtm.MarkDtm;
import lk.system.it.Entity.Attendance;
import lk.system.it.Entity.Student;
import lk.system.it.Entity.Student_Course;
import lk.system.it.Entity.TO.AttendanceTo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public interface AttendanceDao extends SuperDAO {

    ArrayList<Attendance> getAllByCity(String city) throws SQLException, ClassNotFoundException;
    ArrayList<Attendance> getAllAttendance(LocalDate date) throws SQLException, ClassNotFoundException;

    boolean markPresent(String pk) throws SQLException, ClassNotFoundException;
    boolean markAllAbsent(ArrayList<Student_Course> list) throws SQLException, ClassNotFoundException;
    AttendanceTo findstuentById(String id, LocalDate date) throws SQLException;

    boolean searchInAttend(String id) throws SQLException;

    boolean searchIsAdded(LocalDate date) throws SQLException;

    ArrayList<Attendance> getStatusById(String pk) throws SQLException;

    Map<String, Map<LocalDate, Integer>> getDailyAttendanceByCity()  throws SQLException;
}

