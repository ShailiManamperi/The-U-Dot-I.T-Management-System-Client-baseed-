package lk.system.it.Service.custom.impl;


import lk.system.it.Dao.DaoFactory;
import lk.system.it.Dao.DaoTypes;
import lk.system.it.Dao.custom.StudentDAO;
import lk.system.it.Db.DBConnection;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Service.custom.StudentService;
import lk.system.it.Service.exception.DuplicateException;
import lk.system.it.Service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private final Converter converter;
    private final Connection connection;

    private final StudentDAO studentDAO;

    public StudentServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        studentDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.STUDENT);
    }

//    @Override
//    public ItemDTO saveItem(ItemDTO itemDTO) throws DuplicateException {
//        if (itemDAO.existByPk(itemDTO.getCode())) {
//            throw new DuplicateException("This Item id is already added!");
//        } else {
//            itemDAO.save(converter.toItem(itemDTO));
//            return itemDTO;
//        }
//    }
//
//    @Override
//    public ItemDTO updatItem(ItemDTO itemDTO) throws NotFoundException {
//        if (!itemDAO.existByPk(itemDTO.getCode())) {
//            throw new NotFoundException("Item not found!");
//        } else {
//            itemDAO.update(converter.toItem(itemDTO));
//            return itemDTO;
//        }
//    }
//
//    @Override
//    public List<ItemDTO> searchItemList(String text) throws NotFoundException {
//        List<Item> itemList = itemDAO.searchByText(text);
//        List<ItemDTO> itemDTOList = new ArrayList<>();
//        for (int i = 0; i<itemList.size(); i++){
//            Item item = itemList.get(i);
//            itemDTOList.add(i,converter.fromItem(item));
//        }
//        return itemDTOList;
//    }
//
//    @Override
//    public boolean deleteItem(String id) throws NotFoundException {
//        if (!itemDAO.existByPk(id)){
//            throw new NotFoundException("This item id is not found");
//        }
//        return itemDAO.deleteByPk(id);
//    }
//

//
//    @Override
//    public boolean searchDuplicate(String id) throws NotFoundException {
//        if (!itemDAO.existByPk(id)){
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public List<ItemDTO> getAllItems() {
//        List<Item> allItems = itemDAO.getAllItems();
//        List<ItemDTO> itemDTOList = new ArrayList<>();
//        for (int i = 0; i<allItems.size(); i++){
//            Item item = allItems.get(i);
//            itemDTOList.add(i,converter.fromItem(item));
//        }
//        return itemDTOList;
//    }
//
//    @Override
//    public Optional<Item> searchItem(String id) {
//        if (!itemDAO.existByPk(id)) {
//            throw new NotFoundException("This item is not founded!");
//        }
//        return  itemDAO.findByPk(id);
//    }
//
//    @Override
//    public ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException {
//        ArrayList<String> itemids = itemDAO.loadItemIds();
//        return itemids;
//    }




    @Override
    public StudentDto saveStudent(StudentDto Dto) throws DuplicateException {
        if (studentDAO.existByPk(Dto.getStudent_id())) {
            throw new DuplicateException("This Item id is already added!");
        } else {
            studentDAO.save(converter.tostudent(Dto));
            return Dto;
        }
    }

    @Override
    public String generateNewItemId() throws SQLException {
        String newStudentId = studentDAO.findNewId();
        return newStudentId;
    }
}
