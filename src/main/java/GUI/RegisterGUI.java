package GUI;

import BusinessLogic.DeliveryService;
import BusinessLogic.Model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JFrame {
    private JTextField register_username;
    private JPasswordField register_password;
    private JPasswordField register_confirmPassword;
    private JButton registerButton;
    private JPanel mainPanel;
    DeliveryService deliveryService;

    public RegisterGUI(DeliveryService deliveryService) throws HeadlessException {
        super("Employee Panel");
        this.deliveryService = deliveryService;;

        addRegisterButtonListener();
        this.setContentPane(mainPanel);
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addRegisterButtonListener(){
        registerButton.addActionListener(e -> registerUser(deliveryService));
    }

    public void registerUser(DeliveryService deliveryService){
        String username = register_username.getText();
        String password = String.valueOf(register_password.getPassword());
        String password_confirm = String.valueOf(register_confirmPassword.getPassword());
        User registerUser = checkForExist(deliveryService, username);

        if(registerUser == null){
            if(password.equals(password_confirm)){
                deliveryService.getUsers().add(new User(username, password));
                JOptionPane.showMessageDialog(this, "User created");
                this.dispose();
            }
            else
                JOptionPane.showMessageDialog(this, "Passwords do not match");
        }
        else
            JOptionPane.showMessageDialog(this, "User already exists");
    }

    public User checkForExist(DeliveryService deliveryService, String username){
        return deliveryService.getUsers().stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
    }
}
