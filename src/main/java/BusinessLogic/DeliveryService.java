package BusinessLogic;

import BusinessLogic.Model.*;
import Data.Deserializator;
import Data.FileWriter;
import Data.Serializator;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class DeliveryService extends Observable implements Serializable, DeliveryServiceProcessing  {

    @Serial
    private static final long serialVersionUID = 1L;

    Set<MenuItem> products;
    Set<User> users;
    Map<Order, ArrayList<MenuItem>> orders;

    public DeliveryService() {
        importProducts();
        importUsers();
        importOrders();
    }

    public boolean isWellFormed(){
        if(products != null && users != null && orders != null){
            return true;
        }
        return false;
    }

    public void notifyObserver(EmployeeOrder eo) {
        setChanged();
        notifyObservers(eo);
    }

    private void importOrders() {
        this.orders = Deserializator.readOrdersFromFile();
    }

    public void importUsers(){
        this.users = Deserializator.readUsersFromFile();
    }

    public void importProductsCSV(){
        try {
            Path path = Paths.get("src/main/resources/products.csv");
            Stream<String> stream = Files.lines(path);
            products = stream.skip(1).map(DeliveryService::parse).collect(Collectors.toSet());
            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static <R> BaseProduct parse(String s) {
        String[] fields = s.split(",");
        return new BaseProduct(fields[0].trim(),
                Double.parseDouble(fields[1]),
                Integer.parseInt(fields[2]),
                Integer.parseInt(fields[3]),
                Integer.parseInt(fields[4]),
                Integer.parseInt(fields[5]),
                Integer.parseInt(fields[6]));
    }

    public Set<MenuItem> getProducts() {
        return products;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Map<Order, ArrayList<MenuItem>> getOrders() {
        return orders;
    }

    public List<String> productHeader(){
        return Arrays.stream(MenuItem.class.getDeclaredFields()).skip(1).map(DeliveryService::fieldToString).collect(Collectors.toList());
    }

    private static String fieldToString(Field f){
        return f.getName();
    }

    @Override
    public void importProducts() {
        this.products = Deserializator.readProductsFromFile();
    }

    @Override
    public void modifyProduct(MenuItem product, MenuItem newProduct) {
        products.remove(product);
        products.add(newProduct);
    }

    @Override
    public boolean addProduct(MenuItem newProduct) {
        return products.add(newProduct);
    }

    @Override
    public void deleteProduct(MenuItem product) {
        products.remove(product);
    }

    @Override
    public void createMenu(ArrayList<MenuItem> menuAsList, String title) {
        CompositeProduct cp = new CompositeProduct();
        cp.setTitle(title);
        for(MenuItem m : menuAsList){
            cp.addItem(m);
        }
        cp.calcRating();
        products.add(cp);
    }

    @Override
    public void createOrder(ArrayList<MenuItem> orderAsList, User user) {
        int id = orders.keySet().size() + 1;

        Order newOrder = new Order(id, user, orderAsList);
        orders.put(newOrder, orderAsList);

        EmployeeOrder eo = new EmployeeOrder(newOrder, orderAsList);
        notifyObserver(eo);

        FileWriter.writeBill(orderAsList, newOrder);
    }

    @Override
    public Set<MenuItem> searchProducts(String criteria, String s) {
        if(!Objects.equals(s, "")){
            return switch (criteria) {
                case "Title" -> getProducts().stream().filter(product -> product.getTitle().contains(s)).collect(Collectors.toSet());
                case "Rating" -> getProducts().stream().filter(product -> product.getRating() == (Double.parseDouble(s))).collect(Collectors.toSet());
                case "Calories" -> getProducts().stream().filter(product -> product.getCalories() == (Integer.parseInt(s))).collect(Collectors.toSet());
                case "Protein" -> getProducts().stream().filter(product -> product.getProtein() == (Integer.parseInt(s))).collect(Collectors.toSet());
                case "Fat" -> getProducts().stream().filter(product -> product.getFat() == (Integer.parseInt(s))).collect(Collectors.toSet());
                case "Sodium" -> getProducts().stream().filter(product -> product.getSodium() == (Integer.parseInt(s))).collect(Collectors.toSet());
                case "Price" -> getProducts().stream().filter(product -> product.getPrice() == (Integer.parseInt(s))).collect(Collectors.toSet());
                default -> getProducts();
            };
        }
        else
            return getProducts();
    }

    @Override
    public void generateIntervalReport(int from, int to) {
        Map<Order, ArrayList<MenuItem>> reportOrders;
        reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getHours() >= from && entry.getKey().getDate().getHours() < to).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        FileWriter.writeIntervalReport(reportOrders, from, to);
    }

    @Override
    public void generateMostOrderedReport(int minNr) {
        ArrayList<MenuItem> productList = new ArrayList<>();
        Map<MenuItem, Integer> countMap = new HashMap<MenuItem, Integer>();

        orders.forEach((k,v) -> productList.addAll(v));

        for (MenuItem m : productList) {
            Integer i = countMap.get(m);
            countMap.put(m, (i == null) ? 1 : i + 1);
        }

        Map<MenuItem, Integer> mostOrdered = countMap.entrySet().stream().filter(entry -> entry.getValue() > minNr).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        FileWriter.writeMostOrderedReport(mostOrdered, minNr);
    }
    @Override
    public void generateClientWithMostOrdersReport(int minNr, int minPrice){
        ArrayList<User> userList = new ArrayList<>();
        Map<User, Integer> countMap = new HashMap<>();
        Map<Order, ArrayList<MenuItem>> validOrders = new HashMap<>(orders.entrySet().stream().filter(entry -> entry.getKey().getTotal() > minPrice).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        validOrders.forEach((k,v) -> userList.add(k.getClient()));

        for (User u : userList) {
            Integer i = countMap.get(u);
            countMap.put(u, (i == null) ? 1 : i + 1);
        }
        countMap.entrySet().removeIf(entry -> entry.getValue() <= minNr);

        FileWriter.writeClientWithMostOrdersReport(countMap, minNr, minPrice);

    }

    @Override
    public void generateProductsOrderedWithinDayReport(String dayName){
        Map<Order, ArrayList<MenuItem>> reportOrders = new HashMap<>();
        switch (dayName) {
            case "Monday"    -> reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getDay() == 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "Tuesday"   -> reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getDay() == 2).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "Wednesday" -> reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getDay() == 3).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "Thursday"  -> reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getDay() == 4).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "Friday"    -> reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getDay() == 5).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "Saturday"  -> reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getDay() == 6).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "Sunday"    -> reportOrders = orders.entrySet().stream().filter(entry -> entry.getKey().getDate().getDay() == 0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        ArrayList<MenuItem> productList = new ArrayList<>();
        reportOrders.forEach((k,v) -> productList.addAll(v));

        Map<MenuItem, Integer> countMap = new HashMap<>();
        for (MenuItem m : productList) {
            Integer i = countMap.get(m);
            countMap.put(m, (i == null) ? 1 : i + 1);
        }
        FileWriter.writeProductsOrderedWithinDayReport(countMap);
    }

    @Override
    public void saveData() {
        Serializator.saveDeliveryService(this);
        Serializator.writeUsersToFile(users);
        Serializator.writeProductsToFile(products);
        Serializator.writeOrdersToFile(orders);
    }


}
