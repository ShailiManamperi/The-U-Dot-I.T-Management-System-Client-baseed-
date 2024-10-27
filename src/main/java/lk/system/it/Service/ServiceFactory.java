package lk.system.it.Service;


import lk.system.it.Dao.custom.impl.*;
import lk.system.it.Service.custom.impl.CourseServiceImpl;
import lk.system.it.Service.custom.impl.StudentServiceImpl;
import lk.system.it.Service.custom.impl.Student_CourseServiceImpl;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance(){
        return serviceFactory==null?(serviceFactory=new ServiceFactory()):serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes serviceTypes){
        switch (serviceTypes){
            case STUDENT:
                return (T)new StudentServiceImpl();
            case COURSE:
                return (T)new CourseServiceImpl();
            case Student_Course:
                return (T)new Student_CourseServiceImpl();
            default:
                return null;
        }
    }

}
