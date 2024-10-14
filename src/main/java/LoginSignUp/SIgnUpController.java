package LoginSignUp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Formatter;
import java.util.ResourceBundle;

public class SIgnUpController implements Initializable {
    @FXML
    private Button signupButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField pfPassword;
    private static final int SALT_LENGTH = 16;

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        Formatter formatter=new Formatter();
        for(byte b:salt){
            formatter.format("%02x",b);
        }
        return formatter.toString();
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((password + salt).getBytes());
            byte[] hashedPassword = md.digest();

            Formatter formatter = new Formatter();
            for (byte b : hashedPassword) {
                formatter.format("%02x", b);
            }
            return   formatter.toString();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tfUsername.getText().trim().isEmpty() && !pfPassword.getText().trim().isEmpty()) {
                    try {
                        String salt = generateSalt();
                        String hashedPassword = hashPassword(pfPassword.getText(), salt);
                        DBUtils.signUpUser(event, tfUsername.getText(), hashedPassword,salt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Please fill in all info");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });
        //Ndryshimi i 'scene' prej sign up ne login kur shtypet butoni login, nese nje user eshte i regjistruar
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent,"login.fxml","Log in",null);
            }
        });
    }
}
