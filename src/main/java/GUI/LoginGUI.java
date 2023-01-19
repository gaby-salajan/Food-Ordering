package GUI;

import BusinessLogic.DeliveryService;
import BusinessLogic.Model.User;
import Data.Deserializator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginGUI extends JFrame{
    private JTextField login_username;
    private JButton loginButton;
    private JButton login_registerButton;
    private JPasswordField login_password;
    private JPanel topPanel;

    DeliveryService deliveryService;

    public LoginGUI() {
        super("Login");

        this.deliveryService = Deserializator.readDeliveryServiceFromFile();
        if(!deliveryService.isWellFormed()){
            deliveryService = new DeliveryService();
        }

        addLoginButtonListener();
        addLoginRegisterButtonListener();


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deliveryService.saveData();
                System.exit(0);
            }
        });
        this.add(topPanel);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addLoginButtonListener(){
        loginButton.addActionListener(e -> {
            User loginUser = getLoginUser();
            if (loginUser != null) {
                if (loginUser.isAdmin()) {
                    new AdminGUI(deliveryService);
                }
                if (loginUser.isEmployee()) {
                    deliveryService.addObserver(new EmployeeGUI());
                }
                if (loginUser.isClient()) {
                    new ClientGUI(loginUser, deliveryService);
                }
            }
        });
    }

    public void addLoginRegisterButtonListener(){
        login_registerButton.addActionListener(e -> new RegisterGUI(deliveryService));
    }
    public User getLoginUser(){
        String username = login_username.getText();
        String password = String.valueOf(login_password.getPassword());
        User loginUser = checkForExist(username, password);
        if(loginUser == null){
            JOptionPane.showMessageDialog(this, "Invalid username / password");
            return null;
        }
        else
            return loginUser;
    }

    public User checkForExist(String username, String password){

        return deliveryService.getUsers().stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findAny().orElse(null);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
