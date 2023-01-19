package BusinessLogic;

import BusinessLogic.Model.MenuItem;
import BusinessLogic.Model.User;

import java.util.ArrayList;
import java.util.Set;

public interface DeliveryServiceProcessing {

    void importProducts();

    void modifyProduct(MenuItem product, MenuItem newProduct);

    boolean addProduct(MenuItem newProduct);
    void deleteProduct(MenuItem product);

    void createMenu(ArrayList<MenuItem> menuAsList, String title);

    void createOrder(ArrayList<MenuItem> orderAsList, User user);

    Set<MenuItem> searchProducts(String criteria, String s);


    void generateIntervalReport(int from, int to);

    void generateMostOrderedReport(int minNr);

    void generateClientWithMostOrdersReport(int minNr, int minPrice);

    void generateProductsOrderedWithinDayReport(String dayName);

    void saveData();
}
