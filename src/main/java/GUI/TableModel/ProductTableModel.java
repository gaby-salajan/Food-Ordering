package GUI.TableModel;

import BusinessLogic.Model.MenuItem;
import java.util.List;

public class ProductTableModel extends TableModel<MenuItem> {

    /**
     * Constructor
     * @param modelData table data to be set
     * @param columnNames table headers to be set
     */
    public ProductTableModel(List<MenuItem> modelData, List<String> columnNames) {
        super(modelData, columnNames);
    }

    /**
     * Return the value of a cell based on the given indexes
     * @param rowIndex row index
     * @param columnIndex column index
     * @return data from the selected field
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MenuItem product = getRow(rowIndex);

        return switch (columnIndex) {
            case 0 -> product.getTitle();
            case 1 -> product.getRating();
            case 2 -> product.getCalories();
            case 3 -> product.getProtein();
            case 4 -> product.getFat();
            case 5 -> product.getSodium();
            case 6 -> product.getPrice();
            default -> null;
        };
    }
}