package Data;

import BusinessLogic.DeliveryService;
import BusinessLogic.Model.MenuItem;
import BusinessLogic.Model.Order;
import BusinessLogic.Model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Deserializator {

    public static Set<MenuItem> readProductsFromFile() {
        Set<MenuItem> products = new HashSet<>();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/data/products.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            products = (Set<MenuItem>) in.readObject();
            in.close();
            file.close();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    public static Set<User> readUsersFromFile() {
        Set<User> users = new HashSet<>();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/data/users.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            users = (Set<User>) in.readObject();
            in.close();
            file.close();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static Map<Order, ArrayList<MenuItem>> readOrdersFromFile() {
        Map<Order, ArrayList<MenuItem>> orders = new HashMap<>();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/data/orders.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            orders = (Map<Order, ArrayList<MenuItem>>) in.readObject();
            in.close();
            file.close();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static DeliveryService readDeliveryServiceFromFile(){
        DeliveryService deliveryService = new DeliveryService();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/data/delivery_service.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            deliveryService = (DeliveryService) in.readObject();
            in.close();
            file.close();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return deliveryService;
    }

}
