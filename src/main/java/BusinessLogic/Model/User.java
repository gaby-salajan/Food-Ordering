package BusinessLogic.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private UserType userType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isAdmin(){
        return this.getUserType().equals(UserType.ADMIN);
    }

    public boolean isEmployee(){
        return this.getUserType().equals(UserType.EMPLOYEE);
    }

    public boolean isClient(){
        return this.getUserType().equals(UserType.CLIENT);
    }


    public User(String username, String password, UserType userType) {
        setUsername(username);
        setPassword(password);
        setUserType(userType);
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        setUserType(UserType.CLIENT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return username;
    }
}
