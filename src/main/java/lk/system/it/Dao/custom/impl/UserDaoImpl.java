package lk.system.it.Dao.custom.impl;

import lk.system.it.Dao.custom.UserDAO;
import lk.system.it.Dao.util.DBUtil;
import lk.system.it.Entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDAO {
    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean updateuser(User u1) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET verification = ? WHERE user_name = ?";
        return DBUtil.executeUpdate(sql,
                u1.getVerification(),
                u1.getUsername()
        );
    }

    @Override
    public boolean save(User u1) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user (user_name,dis_name,password,verification,hint) VALUES (?, ? ,?, ?,?)";
        return DBUtil.executeUpdate(sql,
                u1.getUsername(),
                u1.getDis_name(),
                u1.getPassword(),
                u1.getVerification(),
                u1.getHint()
        );
    }

    @Override
    public User search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM user WHERE user_name = ?";
        ResultSet result = DBUtil.executeQuery(sql, code);
        if (result.next()) {
            return new User(
                    result.getString("user_name"),
                    result.getString("dis_name"),
                    result.getString("password"),
                    result.getString("verification"),
                    result.getString("hint")
            );
        }
        return null;
    }
}
