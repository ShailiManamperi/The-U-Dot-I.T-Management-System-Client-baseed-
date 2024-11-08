package lk.system.it.Dao.custom.impl;

import lk.system.it.Dao.custom.StudentDAO;
import lk.system.it.Dao.exception.ConstraintViolationException;
import lk.system.it.Dao.util.DBUtil;
import lk.system.it.Entity.Attendance;
import lk.system.it.Entity.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {
    private final Connection connection;

    public StudentDAOImpl(Connection connection) {
            this.connection = connection;
    }

    @Override
    public Student save(Student entity) throws ConstraintViolationException {
        try {
                if(DBUtil.executeUpdate("INSERT INTO Students (student_id, student_name, contact_number, address, school, photo) VALUES (?,?,?,?,?,?)",
                        entity.getStudent_id(),
                        entity.getStudent_name(),
                        entity.getContact_number(),
                        entity.getAddress(),
                        entity.getSchool(),
                        entity.getPhoto()
                ))
            {
                return entity;
            }
            throw new SQLException("Failed to save the items");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Student update(Student entity) throws ConstraintViolationException {
        try {
            String sql ="UPDATE Students SET  student_name = ?, contact_number =?, address = ?, school = ?, photo = ? WHERE student_id=?;";
            if(DBUtil.executeUpdate(sql,entity.getStudent_name(),entity.getContact_number(),entity.getSchool(),
                    entity.getPhoto(),entity.getStudent_id())){
                return entity;
            }
            throw new SQLException("Failed to update the Student..");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM Students WHERE student_id=?",pk)){
                return false;
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
        return true;
    }

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public String findNewId() {
        String itemid = null;
        try {
            String sql = "SELECT student_id FROM Students ORDER BY student_id DESC LIMIT 1";
            ResultSet result = DBUtil.executeQuery(sql);
            if (result.next()) {
                itemid = generateNextStudId(result.getString(1));
            }else {
                itemid = generateNextStudId(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemid;
    }

    private String generateNextStudId(String currentItemId) {
        if (currentItemId == null) {
            return "KS-001";
        } else {
            String[] split = currentItemId.split("KS-");
            int id = Integer.parseInt(split[1]);
            id++;
            String newId = String.format("KS-%03d", id);
            return newId;
        }
    }


    @Override
    public Student findByPk(String pk) {
        Student student=null;
        try {
            String sql = "Select * from Students where student_id= ?";
            ResultSet rst = DBUtil.executeQuery(sql, pk);
            if(rst.next()){
                student= new Student(
                        rst.getString("student_id"),
                        rst.getString("student_name"),
                        rst.getString("contact_number"),
                        rst.getString("address"),
                        rst.getString("school"),
                        rst.getBytes("photo")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Student details");
        }
        return student;
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Students WHERE student_id=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student findStudent(String Stud_id, String type) {
//        System.out.println("dao "+Stud_id+" "+type);
        Student student=null;
        try {
            String sql = "Select * from Students where " + type + "= ?";
            System.out.println(sql);
            ResultSet rst = DBUtil.executeQuery(sql, Stud_id);
            System.out.println(rst.toString());
            if(rst.next()){
                student= new Student(
                        rst.getString("student_id"),
                        rst.getString("student_name"),
                        rst.getString("contact_number"),
                        rst.getString("address"),
                        rst.getString("school"),
                        rst.getBytes("photo")
                );
                System.out.println(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Student details");
        }
        return student;
    }

    public String findStudentNameByPk(String pk) {
        String studentName = null;
        try {
            String sql = "SELECT student_name FROM Students WHERE student_id = ?";
            ResultSet rst = DBUtil.executeQuery(sql, pk);

            if (rst.next()) {
                studentName = rst.getString("student_name");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Student name");
        }
        return studentName;
    }



}
