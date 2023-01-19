package GUI;

import BusinessLogic.DeliveryService;
import BusinessLogic.Model.BaseProduct;
import BusinessLogic.Model.MenuItem;
import GUI.TableModel.ProductTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AdminGUI extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JButton deleteProductButton;
    private JTable productsTable;
    private JButton modifyProductButton;
    private JList<MenuItem> menuAsList;
    private JButton addToMenuButton;
    private JButton removeFromMenuButton;
    private JButton createMenuButton;
    private JButton importProductsButton;
    private JTextField fromTF;
    private JTextField toTF;
    private JButton timeIntButton;
    private JTextField minOrderedNumTF;
    private JButton mostOrderedButton;
    private JTextField minNumOrdersTF;
    private JButton clientWithMostOrdersButton;
    private JTextField minPriceTF;
    private JTextField dayTF;
    private JButton productsOrderedWithinDayButton;
    private JTextField menuNameTF;
    private JScrollPane productsTableScrollPane;
    private JTextField productTitleTF;
    private JTextField productRatingTF;
    private JTextField productCaloriesTF;
    private JTextField productProteinTF;
    private JTextField productFatTF;
    private JTextField productSodiumTF;
    private JTextField productPriceTF;
    private JButton addProductButton;
    private JTextField searchTF;
    private JButton resetButton;
    private JButton searchButton;
    private JComboBox criteriaCB;

    DeliveryService deliveryService;

    ProductTableModel tableModel;
    DefaultListModel<MenuItem> listModel;

    private String searchCriteria;

    public AdminGUI(DeliveryService deliveryService) throws HeadlessException {
        super("Admin");

        this.deliveryService = deliveryService;
        this.listModel = new DefaultListModel<>();
        menuAsList.setModel(listModel);
        this.searchCriteria = "Title";

        addActionListeners();

        this.setContentPane(mainPanel);
        this.setSize(new Dimension(1500, 1000));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addActionListeners(){
        importProductsButton.addActionListener(
                e -> {
                    deliveryService.importProductsCSV();
                    makeTable(deliveryService.getProducts());
                    JOptionPane.showMessageDialog(this, "Products imported from .csv file");
                }
        );
        tabbedPane1.addChangeListener(
                e -> {
                    if(tabbedPane1.getSelectedIndex() == 1){
                        searchTF.setText("");
                        search(searchCriteria, searchTF.getText());
                    }
                }
        );

        addToMenuButton.addActionListener(
                e -> {
                    MenuItem product = tableModel.getRow(productsTable.getSelectedRow());
                    listModel.addElement(product);
                    menuAsList.updateUI();
                });
        removeFromMenuButton.addActionListener(
                e -> {
                    MenuItem product = menuAsList.getSelectedValue();
                    listModel.removeElement(product);
                    menuAsList.updateUI();
                });

        createMenuButton.addActionListener(
                e -> {
                    if(checkFields()) {
                        ArrayList<MenuItem> list = Collections.list(listModel.elements());
                        deliveryService.createMenu(list, menuNameTF.getText());
                        JOptionPane.showMessageDialog(this, "Menu : \""+menuNameTF.getText()+"\""+" created.");
                        menuNameTF.setText("");
                    }
                });

        modifyProductButton.addActionListener(
                e -> {
                    MenuItem product = tableModel.getRow(productsTable.getSelectedRow());
                    new ModifyProductWindow(deliveryService, product);
                });

        deleteProductButton.addActionListener(
                e -> {
                    MenuItem product = tableModel.getRow(productsTable.getSelectedRow());
                    deliveryService.deleteProduct(product);
                    JOptionPane.showMessageDialog(this, "Product deleted.");
                    makeTable(deliveryService.getProducts());
                }
        );
        
        addProductButton.addActionListener(
                e -> {
                    if(checkAddProductFields()){
                        MenuItem newProduct = new BaseProduct(productTitleTF.getText(),
                                Double.valueOf(productRatingTF.getText()),
                                Integer.parseInt(productCaloriesTF.getText()),
                                Integer.parseInt(productProteinTF.getText()),
                                Integer.parseInt(productFatTF.getText()),
                                Integer.parseInt(productSodiumTF.getText()),
                                Integer.parseInt(productPriceTF.getText()));
                        if(!deliveryService.addProduct(newProduct)){
                            JOptionPane.showMessageDialog(this, "Product already exists");
                        }
                    }
                });

        timeIntButton.addActionListener(
                e -> {
                    int from = Integer.parseInt(fromTF.getText());
                    int to = Integer.parseInt(toTF.getText());
                    deliveryService.generateIntervalReport(from, to);
                    JOptionPane.showMessageDialog(this, "Report generated");
                });

        mostOrderedButton.addActionListener(
                e -> {
                    int minNr = Integer.parseInt(minOrderedNumTF.getText());
                    deliveryService.generateMostOrderedReport(minNr);
                    JOptionPane.showMessageDialog(this, "Report generated");
                });

        clientWithMostOrdersButton.addActionListener(
                e -> {
                    int minNr = Integer.parseInt(minNumOrdersTF.getText());
                    int minPrice = Integer.parseInt(minPriceTF.getText());
                    deliveryService.generateClientWithMostOrdersReport(minNr, minPrice);
                    JOptionPane.showMessageDialog(this, "Report generated");
                });

        productsOrderedWithinDayButton.addActionListener(
                e -> {
                   String day = dayTF.getText();
                   deliveryService.generateProductsOrderedWithinDayReport(day);
                    JOptionPane.showMessageDialog(this, "Report generated");
                });

        searchButton.addActionListener(
                e -> {
                    search(searchCriteria,searchTF.getText());
                });

        criteriaCB.addActionListener(
                e -> {
                    searchCriteria = (String) criteriaCB.getSelectedItem();
                    //System.out.println(searchCriteria);
                });
        resetButton.addActionListener(
                e -> {
                    searchTF.setText("");
                    search(searchCriteria, searchTF.getText());
                });
    }

    private boolean checkAddProductFields() {
        if(productTitleTF.getText().equals(""))
            return false;
        if(productRatingTF.getText().equals(""))
            return false;
        if(productCaloriesTF.getText().equals(""))
            return false;
        if(productProteinTF.getText().equals(""))
            return false;
        if(productFatTF.getText().equals(""))
            return false;
        if(productSodiumTF.getText().equals(""))
            return false;
        if(productPriceTF.getText().equals(""))
            return false;
        return true;
    }
    
    private boolean checkFields(){
        if(menuNameTF.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Enter a name for the menu");
            return false;
        }
        if(listModel.isEmpty()){
            JOptionPane.showMessageDialog(this, "Cannot create an empty menu");
            return false;
        }
        return true;
    }


    private void makeTable(Set<MenuItem> products){
        List<String> headers = deliveryService.productHeader();

        tableModel = new ProductTableModel(new ArrayList<>(products), headers);
        productsTable.setModel(tableModel);
        productsTable.updateUI();
    }


    private void search(String criteria, String s){
        Set<MenuItem> searchProducts = deliveryService.searchProducts(criteria, s);
        makeTable(searchProducts);
    }
}

    
