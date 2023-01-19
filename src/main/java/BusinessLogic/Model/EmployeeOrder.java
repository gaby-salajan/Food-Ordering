package BusinessLogic.Model;

import java.util.ArrayList;

public class EmployeeOrder {
    private Order o;
    private ArrayList<MenuItem> productList;

    public EmployeeOrder(Order o, ArrayList<MenuItem> productList) {
        this.o = o;
        this.productList = productList;
    }

    public Order getOrder() {
        return o;
    }

    public void setOrder(Order o) {
        this.o = o;
    }

    public ArrayList<MenuItem> getList() {
        return productList;
    }

    public void setList(ArrayList<MenuItem> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        String toPrint = "";
        toPrint = "<html>Order : "+ o.getId() +" - client : "+o.getClient()+" -> <br/>";
        for(MenuItem m : productList){
            toPrint += (m.getTitle() + "<br/>");
        }
        toPrint += "</html>";
        return toPrint;
    }
}
