package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class App {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(1);
        System.out.println(seller);

        seller.setName("Bob Grey");
        seller.setDepartment(new Department(2, null));
        sellerDao.update(seller);

        System.out.println(seller);
    }
}
