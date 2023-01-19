package BusinessLogic.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private User client;
    private Date date;
    private int total;

    public Order() {
        this.id = 0;
        this.client = null;
        this.date = new Date(0);
    }

    public Order(int id, User client) {
        this.id = id;
        this.client = client;
        this.date = new Date();
    }

    public Order(int id, User client, Date date) {
        this.id = id;
        this.client = client;
        this.date = date;
    }

    public Order(int id, User client, ArrayList<MenuItem> orderAsList) {
        this.id = id;
        this.client = client;
        this.date = new Date();
        for(MenuItem m : orderAsList){
            this.total += m.getPrice();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && client == order.client && date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, date);
    }

    @Override
    public String toString() {
        return "Order-" + id + " -> client : " + client + " , date : " + date;
    }
}
