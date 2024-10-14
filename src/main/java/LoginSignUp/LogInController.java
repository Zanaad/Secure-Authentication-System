package LoginSignUp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField pfPassword;

    public void initialize(URL location, ResourceBundle resources){
        loginButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                DBUtils.logInUser(event,tfUsername.getText(),pfPassword.getText());
            }
        });
        //Ndryshimi i 'scene' prej login ne sign up kur shtypet butoni sign up, nese nje user nuk eshte i regjistruar
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"signup.fxml","Sign up",null);
            }
        });
    }
}
