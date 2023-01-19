package GUI;

import BusinessLogic.DeliveryService;
import BusinessLogic.Model.User;
import BusinessLogic.Model.MenuItem;
import GUI.TableModel.ProductTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ClientGUI extends JFrame {

    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JTable productTable;
    private JTextField searchTF;
    private JButton searchButton;
    private JScrollPane productTableScrollPane;
    private JButton addProductToOrderButton;
    private JButton placeOrderButton;
    private JButton removeProductButton;
    private JLabel orderTotalLabel;
    private JLabel orderTotal;
    private JList<MenuItem> orderAsList;
    private JButton resetButton;
    private JComboBox<String> criteriaCB;
    private JButton clearOrderButton;

    private String searchCriteria;

    private User user;
    DeliveryService deliveryService;

    ProductTableModel tableModel;
    DefaultListModel<MenuItem> listModel;

    public ClientGUI(User user, DeliveryService deliveryService) throws HeadlessException {
        super("Client: " + user.getUsername());
        this.user = user;
        this.deliveryService = deliveryService;
        this.searchCriteria = "Title";

        this.listModel = new DefaultListModel<>();
        orderAsList.setModel(listModel);

        addActionListeners();
        makeTable(deliveryService.getProducts());
        this.setContentPane(mainPanel);
        this.setSize(new Dimension(1300, 800));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void addActionListeners(){
        criteriaCB.addActionListener(
                e -> {
                    searchCriteria = (String) criteriaCB.getSelectedItem();
                    //System.out.println(searchCriteria);
                });
        searchButton.addActionListener(
                e -> {
                    search(searchCriteria,searchTF.getText());
                });
        resetButton.addActionListener(
                e -> {
                    searchTF.setText("");
                    search(searchCriteria, searchTF.getText());
                });

        addProductToOrderButton.addActionListener(
                e -> {
                    MenuItem product = tableModel.getRow(productTable.getSelectedRow());
                    listModel.addElement(product);
                    orderTotal.setText(Integer.parseInt(orderTotal.getText())+product.getPrice()+"");
                    orderAsList.updateUI();
                });

        removeProductButton.addActionListener(
                e -> {
                    MenuItem product = orderAsList.getSelectedValue();
                    listModel.removeElement(product);
                    orderTotal.setText(Integer.parseInt(orderTotal.getText())-product.getPrice()+"");
                    orderAsList.updateUI();
                });

        clearOrderButton.addActionListener(
                e -> {
                    orderTotal.setText("0");
                    listModel.removeAllElements();
                });

        placeOrderButton.addActionListener(
                e -> {
                    ArrayList<MenuItem> order = Collections.list(listModel.elements());
                    deliveryService.createOrder(order, user);
                    JOptionPane.showMessageDialog(this, "Order placed");

                });
    }

    private void makeTable(Set<MenuItem> products){
        List<String> headers = deliveryService.productHeader();

        tableModel = new ProductTableModel(new ArrayList<>(products), headers);
        productTable.setModel(tableModel);
        productTable.updateUI();
    }

    private void search(String criteria, String s){
        Set<MenuItem> searchProducts = deliveryService.searchProducts(criteria, s);
        makeTable(searchProducts);
    }



}
