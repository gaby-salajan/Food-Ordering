package BusinessLogic.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public MenuItem() {
        this.title = "";
        this.rating = 0.0;
        this.calories = 0;
        this.protein = 0;
        this.fat = 0;
        this.sodium = 0;
        this.price = 0;
    }

    public MenuItem(String title, Double rating, int calories, int protein, int fat, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public abstract int computePrice();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return title.equals(menuItem.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return
                title + " - " +
                "rating : " + rating +
                " | calories : " + calories +
                " | protein : " + protein +
                " | fat : " + fat +
                " | sodium : " + sodium +
                " | price : " + price;
    }
}
