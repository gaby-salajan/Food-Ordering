package BusinessLogic.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CompositeProduct extends MenuItem{
    private List<MenuItem> menu;

    public CompositeProduct() {
        super();
        this.menu = new ArrayList<MenuItem>();
    }
    public CompositeProduct(String title, Double rating, int calories, int protein, int fat, int sodium, int price) {
        super(title, rating, calories, protein, fat, sodium, price);
        this.menu = new ArrayList<MenuItem>();
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void addItem(MenuItem item){
        this.menu.add(item);
        this.setCalories(getCalories() + item.getCalories());
        this.setProtein(getProtein() + item.getProtein());
        this.setFat(getFat() + item.getFat());
        this.setSodium(getSodium() + item.getSodium());
        this.setPrice(getPrice() + item.getPrice());
    }

    public void calcRating(){
        AtomicReference<Double> avg = new AtomicReference<>(0.0);
        AtomicInteger count = new AtomicInteger();
        menu.forEach(menuItem -> avg.getAndSet(avg.get()+menuItem.getRating()));
        avg.getAndSet(avg.get() / menu.size());
        this.setRating(avg.get());
    }

    @Override
    public int computePrice() {
        AtomicInteger res = new AtomicInteger();
        menu.forEach(menuItem -> res.addAndGet(menuItem.getPrice()));
        return res.get();
    }
}
