package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class App {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        List<Seller> sellerList = sellerDao.findAll();

        for (Seller x : sellerList) {
            System.out.println(x);
        }
    }
}
