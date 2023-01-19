package GUI;

import BusinessLogic.Model.EmployeeOrder;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class EmployeeGUI extends JFrame implements Observer {
    private JPanel mainPanel;
    private JButton finishOrderButton;

    private JList<String> orderList;
    DefaultListModel<String> listModel;

    private EmployeeOrder employeeOrder;

    public EmployeeGUI() throws HeadlessException {
        super("Employee");

        this.listModel = new DefaultListModel<>();
        orderList.setModel(listModel);
        orderList.setCellRenderer(customRenderer());

        this.setContentPane(mainPanel);
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        finishOrderButton.addActionListener(
                e -> {
                    if(orderList.getSelectedValue() == null){
                        JOptionPane.showMessageDialog(this, "Select an order");
                    }
                    else{
                        listModel.removeElement(orderList.getSelectedValue());
                        orderList.updateUI();
                    }
                });
    }

    @Override
    public void update(Observable o, Object obj) {
        this.employeeOrder = (EmployeeOrder)obj;
        addToList();
    }

    private void addToList(){
        listModel.addElement(employeeOrder.toString());
        orderList.updateUI();
    }

    private ListCellRenderer<? super String> customRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                return listCellRendererComponent;
            }
        };
    }
}
