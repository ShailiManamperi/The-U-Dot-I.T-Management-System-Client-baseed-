package lk.system.it.Dao.custom;

import lk.system.it.Dao.SuperDAO;
import lk.system.it.Entity.User;

import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
    boolean updateuser(User u1) throws SQLException, ClassNotFoundException;

    boolean save(User u1) throws SQLException, ClassNotFoundException;

    User search(String code) throws SQLException, ClassNotFoundException;
}
