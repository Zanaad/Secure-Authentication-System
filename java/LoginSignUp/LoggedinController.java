package LoginSignUp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedinController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Label  welcomeLabel;
    @FXML
    private Label favoriteLabel;

    public void initialize(URL location, ResourceBundle resources){
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"login.fxml","Log in",null);
            }
        });
    }
    public void setUserInformation(String username){
        welcomeLabel.setText("Welcome "+ username+"!");
    }
}
