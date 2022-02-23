package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        departmentDao.findAll();

        List<Department> departments = departmentDao.findAll();

        for (Department x : departments){
            System.out.println(x);
        }
    }
}
