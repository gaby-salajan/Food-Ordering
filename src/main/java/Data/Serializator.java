package Data;

import BusinessLogic.DeliveryService;
import BusinessLogic.Model.MenuItem;
import BusinessLogic.Model.Order;
import BusinessLogic.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class Serializator {

    public static void writeProductsToFile(Set<MenuItem> products){
        try {
            FileOutputStream file = new FileOutputStream("src/main/resources/data/products.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(products);
            out.close();
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeUsersToFile(Set<User> users){
        try {
            FileOutputStream file = new FileOutputStream("src/main/resources/data/users.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(users);
            out.close();
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeOrdersToFile(Map<Order, ArrayList<MenuItem>> orders){
        try {
            FileOutputStream file = new FileOutputStream("src/main/resources/data/orders.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(orders);
            out.close();
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDeliveryService(DeliveryService deliveryService) {
        try {
            FileOutputStream file = new FileOutputStream("src/main/resources/data/delivery_service.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(deliveryService);
            out.close();
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
