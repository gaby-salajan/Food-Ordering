package GUI;

import BusinessLogic.DeliveryService;
import BusinessLogic.Model.BaseProduct;
import BusinessLogic.Model.MenuItem;

import javax.swing.*;
import java.awt.*;

public class ModifyProductWindow extends JFrame{
    private JTextField ratingTF;
    private JButton modifyProductButton;
    private JPanel mainPanel;
    private JTextField titleTF;
    private JTextField caloriesTF;
    private JTextField proteinTF;
    private JTextField fatTF;
    private JTextField sodiumTF;
    private JTextField priceTF;

    DeliveryService deliveryService;
    MenuItem product;

    public ModifyProductWindow(DeliveryService deliveryService, MenuItem product) throws HeadlessException {
        super("Modify Product");

        this.deliveryService = deliveryService;
        this.product = product;

        fillFields();

        modifyProductButton.addActionListener(
                e -> {
                    if(checkFields()){
                        MenuItem newProduct = new BaseProduct(titleTF.getText(),
                                Double.valueOf(ratingTF.getText()),
                                Integer.parseInt(caloriesTF.getText()),
                                Integer.parseInt(proteinTF.getText()),
                                Integer.parseInt(fatTF.getText()),
                                Integer.parseInt(sodiumTF.getText()),
                                Integer.parseInt(priceTF.getText()));
                        deliveryService.modifyProduct(product, newProduct);
                        JOptionPane.showMessageDialog(this, "Product modified.");
                        dispose();
                    }
                });

        this.setContentPane(mainPanel);
        this.setSize(new Dimension(700, 400));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void fillFields(){
        titleTF.setText(product.getTitle());
        ratingTF.setText(String.valueOf(product.getRating()));
        caloriesTF.setText(String.valueOf(product.getCalories()));
        proteinTF.setText(String.valueOf(product.getProtein()));
        fatTF.setText(String.valueOf(product.getFat()));
        sodiumTF.setText(String.valueOf(product.getSodium()));
        priceTF.setText(String.valueOf(product.getPrice()));
    }

    private boolean checkFields(){
        if(titleTF.getText().equals(""))
            return false;
        if(ratingTF.getText().equals(""))
            return false;
        if(caloriesTF.getText().equals(""))
            return false;
        if(proteinTF.getText().equals(""))
            return false;
        if(fatTF.getText().equals(""))
            return false;
        if(sodiumTF.getText().equals(""))
            return false;
        if(priceTF.getText().equals(""))
            return false;
        return true;
    }

}
