package lk.system.it.Service.custom;

import lk.system.it.Dto.UserDTO;
import lk.system.it.Service.SuperService;

import java.sql.SQLException;

public interface UserService extends SuperService{
    public UserDTO search(String code) throws SQLException, ClassNotFoundException;

    public boolean save (UserDTO userDTO) throws SQLException, ClassNotFoundException;

    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException;
}
