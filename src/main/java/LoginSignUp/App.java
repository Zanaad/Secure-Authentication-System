package LoginSignUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public void start(Stage stage) throws Exception{
        FXMLLoader createUserForm=new FXMLLoader(
                App.class.getResource("login.fxml")
        );
        Scene scene=new Scene(createUserForm.load());
        stage.setScene(scene);
        stage.show();
    }
}
