package lk.system.it.Dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.system.it.Dao.custom.AttendanceDao;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Dao.util.DBUtil;
import lk.system.it.Dtm.MarkDtm;
import lk.system.it.Entity.Attendance;
import lk.system.it.Entity.Student;
import lk.system.it.Entity.Student_Course;
import lk.system.it.Entity.TO.AttendanceTo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AttendanceDaoImpl implements AttendanceDao {
    private final Connection connection;

    public AttendanceDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Attendance> getAllByCity(String city) throws SQLException, ClassNotFoundException {
        try {
            ArrayList<Attendance> Ad = new ArrayList<>();
            ResultSet rs = DBUtil.executeQuery("select * from Student_Course where city=?",city);
            while (rs.next()) {
                String id = rs.getString("student_id");
                Attendance attendance = new Attendance(id,LocalDate.now(),"Absent");
                Ad.add(attendance);
            }
            return Ad;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public ArrayList<Attendance> getAllAttendance(LocalDate date) throws SQLException, ClassNotFoundException {
        try {
            ArrayList<Attendance> Ad = new ArrayList<>();
            ResultSet rs = DBUtil.executeQuery("select * from Attend where date=?",date);
            while (rs.next()) {
                String id = rs.getString("student_id");
                LocalDate date1 = rs.getDate("date").toLocalDate();
                String status = rs.getString("status");
                Attendance attendance = new Attendance(id,date1,status);
                Ad.add(attendance);
            }
            return Ad;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean markPresent(String pk) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        boolean b = searchInAttend(pk);
        if (b){
            String updateQuery = "UPDATE Attend SET status = ? WHERE student_id = ? AND date = ? ";
            boolean b1 = DBUtil.executeUpdate(updateQuery, "Present", pk, LocalDate.now());
            if (b1){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean markAllAbsent(ArrayList<Student_Course> list) throws SQLException, ClassNotFoundException {
        String insertQuery = "INSERT INTO Attend (student_id, date, status) VALUES (?, ?, ?)";

        boolean allInserted = true;
        LocalDate currentDate = LocalDate.now();
        for (Student_Course student : list) {
            boolean inserted = DBUtil.executeUpdate(insertQuery, student.getStudent_id(), currentDate, "Absent");
            if (!inserted) {
                allInserted = false;
            }
        }

        return allInserted;
    }

    @Override
    public AttendanceTo findstuentById(String id, LocalDate date) throws SQLException {
        String  sql = "Select * from attend where student_id= ? and date = ?";
        ResultSet resultSet = DBUtil.executeQuery(sql, id, date);
        AttendanceTo data = null;
        while (resultSet.next()){
            data = new AttendanceTo(
                    resultSet.getString("student_id"),
                    resultSet.getString("status")
            );
        }
        return data;
    }

    @Override
    public boolean searchInAttend(String id) throws SQLException {
        String sql = "Select status from Attend where student_id= ? and date = ?";
        ResultSet resultSet = DBUtil.executeQuery(sql, id, LocalDate.now());
        boolean flag = false;
        while (resultSet.next()){
            String status = resultSet.getString("status");
            if (status.equals("Absent")){
                flag =  true;
            }
        }
        return flag;
    }

    @Override
    public boolean searchIsAdded(LocalDate date) throws SQLException {
        boolean flag= false;
        String sql = "Select * from attend where date=?";
        ResultSet resultSet = DBUtil.executeQuery(sql, date);
        while (resultSet.next()){
            flag=true;
        }
        return flag;
    }

    @Override
    public ArrayList<Attendance> getStatusById(String pk) throws SQLException {
        ArrayList<Attendance> list = new ArrayList<>();
        ResultSet rs = DBUtil.executeQuery("select * from Attend where student_id=?",pk);
        while (rs.next()) {
            String id = rs.getString("student_id");
            LocalDate date1 = LocalDate.parse(rs.getString("date"));
            String status = rs.getString("status");
            Attendance attendance = new Attendance(id,date1,status);
            list.add(attendance);
        }
        return list;
    }
}
