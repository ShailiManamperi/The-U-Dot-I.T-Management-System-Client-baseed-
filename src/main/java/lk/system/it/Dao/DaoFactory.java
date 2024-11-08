package lk.system.it.Dao;


import lk.system.it.Dao.custom.impl.*;

import java.sql.Connection;

public class DaoFactory {

    private static DaoFactory daoFactory ;
    private DaoFactory() {
    }

    public static DaoFactory getInstance(){
        return daoFactory==null?(daoFactory=new DaoFactory()):daoFactory;
    }

    public <T extends SuperDAO> T getDAO(Connection connection, DaoTypes daoType) {
        switch (daoType){
            case STUDENT:
                return (T)new StudentDAOImpl(connection);
            case Student_Course:
                return (T)new Student_CourseDAOImpl(connection);
            case COURSE:
                return (T)new CourseDaoImpl(connection);
            case ATTEND:
                return (T)new AttendanceDaoImpl(connection);
            case USER:
                return(T) new UserDaoImpl(connection);
//            case EMPLOYEE:
//                return (T)new employeeDAOImpl(connection);
//            case ATTEND:
//                return (T)new AttendanceDAOImpl(connection);
//            case CUSTOMER:
//                return (T)new CustomerDAOImpl(connection);
//            case ORDER:
//                return (T)new OrderDAOImpl(connection);
//            case ITEM:
//                return (T)new ItemDAOImpl(connection);
//            case SUPPLIER:
//                return (T) new SupplierDAOImpl(connection);
//            case DELIVERY:
//                return (T) new DeliveryDAOImpl(connection);


//            case SUPPLIERORDER:
//                return (T) new SupplierOrderDAOImpl(connection);
//            case INVOICE:
//                return (T) new InvoiceDAOImpl(connection);

            default:
                return null;

        }

    }
}
