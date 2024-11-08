package lk.system.it.Service.custom.impl;

import lk.system.it.Dao.DaoFactory;
import lk.system.it.Dao.DaoTypes;
import lk.system.it.Dao.custom.AttendanceDao;
import lk.system.it.Dao.custom.StudentDAO;
import lk.system.it.Db.DBConnection;
import lk.system.it.Dtm.MarkDtm;
import lk.system.it.Dto.AttendanceDto;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Entity.Attendance;
import lk.system.it.Entity.Student;
import lk.system.it.Entity.Student_Course;
import lk.system.it.Entity.TO.AttendanceTo;
import lk.system.it.Service.custom.AttendanceService;
import lk.system.it.Service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
public class AttendanceServiceImpl implements AttendanceService {
    private final Connection connection;
    private final Converter converter;
    private final StudentDAO studentDAO;
    private final AttendanceDao attendanceDao;

    public AttendanceServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        studentDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.STUDENT);
        attendanceDao = DaoFactory.getInstance().getDAO(connection,DaoTypes.ATTEND);
        converter = new Converter();
    }
    @Override
    public ArrayList<MarkDtm> getAllByCity(String city) throws SQLException {
        ArrayList<MarkDtm> list = new ArrayList<>();
        try {
            ArrayList<Attendance> allByCity = attendanceDao.getAllByCity(city);
            for (int i = 0; i<allByCity.size(); i++){
                Attendance attendance = allByCity.get(i);
                String name = studentDAO.findStudentNameByPk(attendance.getStudent_id());
                list.add(i,converter.fromAttendance(attendance,name));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public ArrayList<MarkDtm> getAllAttendance(LocalDate date) throws SQLException {
        ArrayList<MarkDtm> list = new ArrayList<>();
        try {
            ArrayList<Attendance> allByCity = attendanceDao.getAllAttendance(date);
            for (int i = 0; i<allByCity.size(); i++){
                Attendance attendance = allByCity.get(i);
                String name = studentDAO.findStudentNameByPk(attendance.getStudent_id());
                list.add(i,converter.fromAttendance(attendance,name));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean markPresent(String id) throws SQLException, ClassNotFoundException {
        boolean b = attendanceDao.markPresent(id);
        return b;
    }

    @Override
    public boolean markAllAbsent(ArrayList<Student_CourseDto> dtoes) throws SQLException, ClassNotFoundException {
        ArrayList<Student_Course> list = new ArrayList<>();
        for (int i=0; i<dtoes.size();i++){
            Student_Course studCour = converter.toStud_Cour(dtoes.get(i));
            list.add(studCour);
        }
        return attendanceDao.markAllAbsent(list);
    }

    @Override
    public MarkDtm getstudentattend(String id, LocalDate date) throws SQLException {
        String name = studentDAO.findStudentNameByPk(id);
        AttendanceTo attendance = attendanceDao.findstuentById(id, date);
        return new MarkDtm(id,name,attendance.getStatus());
    }

    @Override
    public boolean searchIsAdded(LocalDate date) throws SQLException {
        boolean b = attendanceDao.searchIsAdded(date);
        return b;
    }

    @Override
    public ArrayList<AttendanceDto> getStatusById(String pk) throws SQLException {
        ArrayList<AttendanceDto> list = new ArrayList<>();
        ArrayList<Attendance> statusById = attendanceDao.getStatusById(pk);
        for (int i = 0; i<statusById.size(); i++){
            Attendance attendance = statusById.get(i);
            list.add(i,converter.fromAttend(attendance));
        }
        return list;
    }

}
