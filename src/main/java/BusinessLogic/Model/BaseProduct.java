package BusinessLogic.Model;

public class BaseProduct extends MenuItem{

    public BaseProduct() {
        super();
    }

    public BaseProduct(String title, Double rating, int calories, int protein, int fat, int sodium, int price) {
        super(title, rating, calories, protein, fat, sodium, price);
    }

    @Override
    public int computePrice() {
        return this.getPrice();
    }
}
