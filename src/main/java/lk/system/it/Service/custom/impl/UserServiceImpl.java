package lk.system.it.Service.custom.impl;

import lk.system.it.Dao.DaoFactory;
import lk.system.it.Dao.DaoTypes;
import lk.system.it.Dao.custom.UserDAO;
import lk.system.it.Db.DBConnection;
import lk.system.it.Dto.UserDTO;
import lk.system.it.Entity.User;
import lk.system.it.Service.custom.UserService;
import lk.system.it.Service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final Converter converter;
    private final Connection connection;
    private final UserDAO userDAO;

    public UserServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        userDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.USER);
    }

    @Override
    public UserDTO search(String code) throws SQLException, ClassNotFoundException {
        User search = userDAO.search(code);
        return converter.fromUser(search);
    }

    @Override
    public boolean save(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        boolean save = userDAO.save(converter.toUser(userDTO));
        return save;
    }

    @Override
    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        boolean updateuser = userDAO.updateuser(converter.toUser(userDTO));
        return updateuser;
    }
}
