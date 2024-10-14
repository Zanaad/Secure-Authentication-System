package LoginSignUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

public class DBUtils {

    // Method to establish a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/DataSecurity", "root", "jupiter");
    }

    // Method to handle alerts
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            if (username != null) {
                LoggedinController loggedinController = loader.getController();
                loggedinController.setUserInformation(username);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event, String username, String password, String salt) {
        String userCheckQuery = "SELECT * FROM users WHERE Username=?";
        String insertQuery = "INSERT INTO users (Username, HashedPassword, Salt) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement psCheckUserExist = connection.prepareStatement(userCheckQuery);
             PreparedStatement psInsert = connection.prepareStatement(insertQuery)) {

            psCheckUserExist.setString(1, username);
            try (ResultSet resultSet = psCheckUserExist.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    showAlert("This username is taken");
                } else {
                    psInsert.setString(1, username);
                    psInsert.setString(2, password);
                    psInsert.setString(3, salt);
                    psInsert.executeUpdate();
                    changeScene(event, "loggedin.fxml", "Welcome", username);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        String loginQuery = "SELECT HashedPassword, Salt FROM users WHERE Username=?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    showAlert("Credentials are not correct");
                } else {
                    while (resultSet.next()) {
                        String retrievedPassword = resultSet.getString("HashedPassword");
                        String salt = resultSet.getString("Salt");
                        String hashedEnteredPassword = SIgnUpController.hashPassword(password, salt);

                        if (retrievedPassword.equals(hashedEnteredPassword)) {
                            changeScene(event, "loggedin.fxml", "Welcome", username);
                        } else {
                            showAlert("Passwords do not match");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
