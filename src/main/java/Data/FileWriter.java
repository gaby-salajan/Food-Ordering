package Data;

import BusinessLogic.Model.MenuItem;
import BusinessLogic.Model.Order;
import BusinessLogic.Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FileWriter {
    private static final String resPath = "src/main/resources/";

    public static void writeBill(ArrayList<MenuItem> orderAsList, Order order) {
        try {
            java.io.FileWriter FW = new java.io.FileWriter(resPath + "bills/Order-" + order.getId() + ".txt");
            FW.write("Order-"+ order.getId()+"\n");
            int total = 0;
            for(MenuItem m : orderAsList){
                total += m.getPrice();
                FW.write(m.toString()+"\n");
            }
            FW.write("Total : "+total+"\n");
            FW.write("Placed by : "+ order.getClient());
            FW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeIntervalReport(Map<Order, ArrayList<MenuItem>> reportOrders, int from, int to) {
        try {
            java.io.FileWriter FW = new java.io.FileWriter(resPath+"/reports/time_interval_report.txt");
            FW.write("Orders performed between "+ from+"-"+to+" :\n");
            reportOrders.forEach((k,v) -> {
                try {
                    FW.write(k+"\n");
                    for(MenuItem m : v){
                        FW.write("    " + m +"\n");
                    }
                    FW.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            FW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMostOrderedReport(Map<MenuItem, Integer> reportOrders, int minNr) {
        try {
            java.io.FileWriter FW = new java.io.FileWriter(resPath+"/reports/most_ordered_products_report.txt");
            FW.write("Product ordered more than "+minNr+" times:\n");
            reportOrders.forEach((k,v) -> {
                try {
                    FW.write(k+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            FW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static void writeProductsOrderedWithinDayReport(Map<MenuItem, Integer> countMap) {
        try {
            java.io.FileWriter FW = new java.io.FileWriter(resPath+"/reports/products_ordered_within_day.txt");

            countMap.forEach((k,v) -> {
                try {
                    FW.write(k+" -> ordered "+ v +" times\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            FW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeClientWithMostOrdersReport(Map<User, Integer> countMap, int minNr, int minPrice) {
        try {
            java.io.FileWriter FW = new java.io.FileWriter(resPath+"/reports/client_with_most_orders.txt");

            FW.write("User that have ordered more than "+minNr+" times with minimum order price of "+minPrice+"\n");
            countMap.forEach((k,v) -> {
                try {
                    FW.write(k+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            FW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
